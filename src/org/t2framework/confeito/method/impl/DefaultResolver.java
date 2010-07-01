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
import org.t2framework.confeito.method.AbstractActionMethodResolver;

/**
 * <#if locale="en">
 * <p>
 * DefaultAnnotationResolver handles &#064;Default.
 * 
 * </p>
 * <#else>
 * <p>
 * &#064;Defaultのリゾルバです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class DefaultResolver extends AbstractActionMethodResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * Always true because it is the last one to match.
	 * </p>
	 * <#else>
	 * <p>
	 * 最後に処理されるリゾルバのため、常にtrueを返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc) {
		return true;
	}
}
