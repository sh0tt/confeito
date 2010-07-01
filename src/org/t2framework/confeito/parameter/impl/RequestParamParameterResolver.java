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
package org.t2framework.confeito.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.annotation.RequestParam;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.exception.InvalidRequestParameterTypeRuntimeException;
import org.t2framework.confeito.exception.RequestParameterNotFoundRuntimeException;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.AutoboxingUtil;
import org.t2framework.confeito.util.ConverterUtil;
import org.t2framework.confeito.util.HtmlEscapeUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * TODO : modify document as it should be.
 * 
 * 
 * RequestParamParameterResolver is a resolver to inject request parameter. If
 * specified request parameter type is String, it goes with String, otherwise
 * the injected value will be String[] like how
 * HttpServletRequest.getParameter/getParameterValues behaves.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class RequestParamParameterResolver extends AbstractParameterResolver {

	private static Logger logger = Logger
			.getLogger(RequestParamParameterResolver.class);

	public RequestParamParameterResolver() {
		setTargetAnnotationClass(RequestParam.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return HttpServletRequest.getParameter if RequestParam(parameter
	 * annotation).value matches.The return value would be String or String[]
	 * type and it is all escaped by {@link HtmlEscapeUtil#escape(String)}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return String or String[]
	 * @throws RequestParameterNotFoundRuntimeException
	 * @throws {@link InvalidRequestParameterTypeRuntimeException}
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		Assertion.notNull(actionContext);
		Assertion.notNull(paramClass);
		final Request request = actionContext.getRequest();
		final RequestParam requestParam = findTargetAnnotation(
				paramAnnotations, targetAnnotationClass);
		final String key = requestParam.value();
		final boolean empty = requestParam.empty();
		String[] paramValues = request.getParameters(key);
		final boolean primitive = paramClass.isPrimitive();
		if (isEmpty(paramValues)) {
			if (empty == false) {
				throw new RequestParameterNotFoundRuntimeException(key);
			} else {
				return getNullValue(paramClass, primitive);
			}
		}
		try {
			if (paramClass.isArray()) {
				final Class<?> type = paramClass.getComponentType();
				final int length = paramValues.length;
				Object ret = Array.newInstance(type, length);
				for (int i = 0; i < length; i++) {
					Object converted = convert(request, paramValues[i], type);
					if (primitive) {
						converted = AutoboxingUtil.getDefaultValueIfNull(type,
								converted);
					}
					Array.set(ret, i, converted);
				}
				return ret;
			} else {
				final String paramValue = paramValues[0];
				return convert(request, paramValue, paramClass);
			}
		} catch (Throwable t) {
			if (empty == false) {
				throw new InvalidRequestParameterTypeRuntimeException(
						paramClass);
			} else {
				logger.log("WTDT0018", new Object[] { paramClass.getName() });
				return null;
			}
		}
	}

	protected Object getNullValue(Class<?> paramClass, boolean primitive) {
		return (primitive) ? AutoboxingUtil
				.getDefaultPrimitiveValue(paramClass) : null;
	}

	protected Object convert(Request request, String value, Class<?> type) {
		if (Date.class.isAssignableFrom(type)) {
			final Locale locale = request.getLocale();
			return ConverterUtil.DATE_CONVERTER.toDate(value, null, locale);
		} else {
			return ConverterUtil.convert(value, type);
		}
	}

	protected boolean isEmpty(String[] paramValues) {
		if (paramValues == null) {
			return true;
		}
		if (paramValues.length == 1) {
			return StringUtil.isEmpty(paramValues[0]);
		}
		return StringUtil.isAllEmpty(paramValues);
	}

}
