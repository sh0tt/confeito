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
import java.lang.reflect.Method;
import java.util.Map;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.Index;
import org.t2framework.confeito.exception.InvalidRequestParameterTypeRuntimeException;
import org.t2framework.confeito.internal.ActionUtil;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.util.ConverterUtil;

/**
 * <#if locale="en">
 * <p>
 * IndexParameterResolver is a concrete class of ParameterResolver to inject
 * index of submitted button which user requested.
 * 
 * <pre>
 * IndexParameterResolver has two constraints:
 *   - To use it, {@literal @ActionParam} must be used.
 *   -{@literal @Index} should be used as parameter annotation.
 * </pre>
 * 
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
public class IndexParameterResolver extends AbstractParameterResolver {

	public IndexParameterResolver() {
		setTargetAnnotationClass(Index.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return index value.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @throws InvalidRequestParameterTypeRuntimeException
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		final Index index = findTargetAnnotation(paramAnnotations,
				getTargetAnnotationClass());
		if (index == null) {
			throw new IllegalStateException("must have @Index.");
		}
		final String indexKey = "{" + index.value() + "}";
		ActionParam param = md.getAnnotation(ActionParam.class);
		if (param == null) {
			throw new RuntimeException("@ActionParam needed.");
		}
		// hoge[{index}][{nestindex}]
		// get position from ActionParam
		String value = param.value();
		int pos = ActionUtil.getArrayPostition(value, indexKey);
		String prefixKey = ActionUtil.getPrefixKey(value);

		// from position and requestParameter, we can get value.
		final Map<String, String[]> params = actionContext.getRequest()
				.getParametersAsMap();
		for (String key : params.keySet()) {
			if (key.indexOf('[') < 0 || key.startsWith(prefixKey) == false) {
				continue;
			}
			int count = 1;
			for (int i = 0; i < key.length(); i++) {
				if (key.charAt(i) == '[') {
					if (count == pos) {
						final int endPos = key.indexOf(']', i);
						if (0 < endPos) {
							String s = key.substring(i + 1, endPos);
							return ConverterUtil.convert(s, paramClass);
						}
					} else {
						count++;
					}
				}
			}
		}
		throw new InvalidRequestParameterTypeRuntimeException(paramClass);
	}
}
