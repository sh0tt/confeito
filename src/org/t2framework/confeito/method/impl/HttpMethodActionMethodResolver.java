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
package org.t2framework.confeito.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.contexts.HttpMethod;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.method.AbstractActionMethodResolver;

/**
 * 
 * <#if locale="en">
 * <p>
 * {@code HttpMethodActionMethodResolver} is an resolver class for http method
 * annotation.
 * </p>
 * <#else>
 * <p>
 * {@code HttpMethodActionMethodResolver}は、 HTTPメソッドアノテーションのリゾルバクラスです．
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class HttpMethodActionMethodResolver extends
		AbstractActionMethodResolver {

	private HttpMethod targetMethod;

	public HttpMethodActionMethodResolver(HttpMethod targetMethod) {
		this.targetMethod = targetMethod;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * True if http method matches.
	 * </p>
	 * <#else>
	 * <p>
	 * HTTPメソッドがマッチした場合trueを返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc) {
		final Request request = actionContext.getRequest();
		HttpMethod method = request.getOverrideHttpMethod();
		if (method == null) {
			method = request.getMethod();
		}
		if (targetMethod == method) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((targetMethod == null) ? 0 : targetMethod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpMethodActionMethodResolver other = (HttpMethodActionMethodResolver) obj;
		if (targetMethod == null) {
			if (other.targetMethod != null)
				return false;
		} else if (!targetMethod.equals(other.targetMethod))
			return false;
		return true;
	}
}
