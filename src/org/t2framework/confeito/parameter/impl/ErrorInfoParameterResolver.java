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

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ErrorInfo;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

/**
 * 
 * <#if locale="en">
 * <p>
 * Get {@link ErrorInfo} as a paramter.
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
public class ErrorInfoParameterResolver extends AbstractParameterResolver {

	public ErrorInfoParameterResolver() {
		setTargetClass(ErrorInfo.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ErrorInfo}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		return actionContext.getErrorInfo();
	}

}
