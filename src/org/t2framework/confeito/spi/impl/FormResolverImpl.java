/*
 * Copyright 2008-2010 the T2 Project ant the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.t2framework.confeito.spi.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.action.ErrorInfo;
import org.t2framework.confeito.annotation.Form;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.internal.RequestParseUtil;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.model.Property;
import org.t2framework.confeito.spi.FormResolver;
import org.t2framework.confeito.util.ConverterUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.Reflections.MethodUtil;

/**
 * <#if locale="en">
 * <p>
 * Default implementation of {@code FormResolver}. The constraint of this
 * implementation is that multiple request parameters converts to List<String>.
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class FormResolverImpl implements FormResolver {

	protected static Logger LOG = Logger.getLogger(FormResolverImpl.class);

	@Override
	public void resolve(Form form, WebContext context, Component<?> component,
			ErrorInfo errorInfo) {
		final Request request = context.getRequest();
		Map<String, Map<Integer, Object>> paramMap = RequestParseUtil
				.createParamMap(request);
		final String className = component.getName();
		for (Property property : component.getProperties()) {
			final String name = property.propertyName;
			if (property.writeMethod == null) {
				LOG.debug(className + "#" + name
						+ " does not have setter method.");
				continue;
			}
			final boolean containsList = paramMap.containsKey(name);
			if (request.hasMultipleParameters(name) == false
					&& containsList == false) {
				final Object param = request.getParameter(name);
				try {
					Object instance = component.getInstance();
					setSingleValue(request, property, instance, param);
				} catch (Throwable t) {
					errorInfo.addErrorInfo(className + "#" + name, t);
					continue;
				}
			} else {
				Object params = null;
				if (containsList) {
					Map<Integer, Object> map = paramMap.get(name);
					params = RequestParseUtil.convertAsList(map);
				} else {
					params = request.getParameters(name);
				}
				if (params == null) {
					continue;
				}
				try {
					Object instance = component.getInstance();
					setMultipleValues(request, property, instance, params);
				} catch (Throwable t) {
					errorInfo.addErrorInfo(className + "#" + name + "[]", t);
					continue;
				}
			}
		}
	}

	protected void setMultipleValues(Request request, Property pd,
			Object target, Object src) {
		final Class<?> destType = pd.writeMethod.getParameterTypes()[0];
		final Class<? extends Object> srcType = src.getClass();
		final Locale locale = request.getLocale();
		if (destType.isArray()) {
			setArrayValue(pd, target, src, destType, srcType, locale);
		} else if (List.class.isAssignableFrom(destType)) {
			setListValue(pd, target, src, destType, srcType, locale);
		} else {
			MethodUtil.invoke(pd.writeMethod, target, pd.convertArgs(target));
		}
	}

	@SuppressWarnings("unchecked")
	protected void setListValue(Property pd, Object target, Object src,
			Class<? extends Object> destType, Class<? extends Object> srcType,
			Locale locale) {
		List<Object> list = null;
		if (List.class.isAssignableFrom(srcType)) {
			list = (List<Object>) src;
		} else if (srcType.isArray()) {
			list = Arrays.asList((Object[]) src);
		}
		Object[] args = Constants.EMPTY_ARRAY;
		if (list != null) {
			List<Object> ret = new ArrayList<Object>();
			for (Object o : list) {
				if (o != null && Date.class.isAssignableFrom(o.getClass())) {
					o = convertToDate(o.toString(), locale);
				}
				ret.add(o);
			}
			args = pd.convertArgs(ret);
		} else {
			args = pd.convertArgs(src);
		}
		MethodUtil.invoke(pd.writeMethod, target, args);
	}

	@SuppressWarnings("unchecked")
	protected void setArrayValue(Property pd, Object target, Object src,
			Class<? extends Object> destType, Class<? extends Object> srcType,
			Locale locale) {
		final Class<?> componentType = destType.getComponentType();
		Object[] params = null;
		if (List.class.isAssignableFrom(srcType)) {
			List<Object> list = (List) src;
			params = list.toArray(new Object[list.size()]);
		} else if (srcType.isArray()) {
			params = (Object[]) src;
		}
		if (params != null) {
			if (Date.class.isAssignableFrom(componentType)) {
				final int length = params.length;
				final Date[] dates = new Date[length];
				for (int i = 0; i < length; i++) {
					dates[i] = convertToDate(params[i].toString(), locale);
				}
				params = dates;
			}
			MethodUtil.invoke(pd.writeMethod, target, pd.convertArgs(params));
		} else {
			MethodUtil.invoke(pd.writeMethod, target, pd.convertArgs(src));
		}
	}

	protected Date convertToDate(String param, Locale locale) {
		return ConverterUtil.DATE_CONVERTER.toDate(param, null, locale);
	}

	protected void setSingleValue(Request request, Property pd, Object target,
			Object param) {
		final Class<?> type = pd.writeMethod.getParameterTypes()[0];
		Object value = param;
		if (Date.class.isAssignableFrom(type)) {
			final Locale locale = request.getLocale();
			value = ConverterUtil.DATE_CONVERTER.toDate(param.toString(), null,
					locale);
		}
		MethodUtil.invoke(pd.writeMethod, target, pd.convertArgs(value));
	}
}
