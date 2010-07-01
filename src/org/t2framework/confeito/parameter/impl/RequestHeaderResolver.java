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
import org.t2framework.confeito.annotation.RequestHeader;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.parameter.ParameterResolver;

/**
 * 
 * <#if locale="en">
 * <p>
 * An {@link ParameterResolver} for request headers.
 * </p>
 * <#else>
 * <p>
 * リクエストヘッダを引数で取得するためのリゾルバです.MapかString型でヘッダを取得することが出来ます.
 * Mapの場合はリクエストヘッダ全体を、Stringの場合はある特定のキーに対しての値を取得できます.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class RequestHeaderResolver extends AbstractParameterResolver {

	public RequestHeaderResolver() {
		setTargetAnnotationClass(RequestHeader.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get request header value.The return value is {@link String} or
	 * {@link String}[] or null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Object resolve(final ActionContext actionContext, final Method md,
			final int paramIndex, final Annotation[] paramAnnotations,
			final Class<?> paramClass) {
		final Map<String, Object> headers = actionContext.getRequest()
				.getHeaders();
		final RequestHeader header = findTargetAnnotation(paramAnnotations,
				getTargetAnnotationClass());
		final String key = header.key();
		final boolean empty = "".equals(key);
		if (paramClass == Map.class && empty) {
			return headers;
		} else if (paramClass == String.class) {
			assertNotEmptyKey(empty);
			Object o = headers.get(key);
			if (o instanceof String) {
				return o;
			} else if (o instanceof String[]) {
				return new String[] { (String) o };
			}
		} else if (paramClass == String[].class) {
			assertNotEmptyKey(empty);
			Object o = headers.get(key);
			if (o instanceof String) {
				return new String[] { (String) o };
			} else if (o instanceof String[]) {
				return o;
			}
		}
		return null;
	}

	protected void assertNotEmptyKey(final boolean empty) {
		if (empty) {
			throw new IllegalStateException("key must be specified.");
		}
	}
}
