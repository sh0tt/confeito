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
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.method.AbstractActionMethodResolver;
import org.t2framework.confeito.method.ActionMethodResolver;

/**
 * <#if locale="en">
 * <p>
 * Ajax type {@link ActionMethodResolver}.
 * 
 * </p>
 * <#else>
 * <p>
 * Ajaxリクエストを処理するリゾルバ.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class AjaxResolver extends AbstractActionMethodResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * True if the request comes from javascript.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストがAjaxタイプの場合、trueを返します.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.contexts.Request#isAjaxRequest()
	 */
	@Override
	public boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc) {
		final Request request = actionContext.getRequest();
		return request.isAjaxRequest();
	}
}
