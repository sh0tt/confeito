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
package org.t2framework.confeito.parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;

/**
 * <#if locale="en">
 * <p>
 * Resolve ActionMethod's parameter.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author AKatayama
 * @author shot
 */
public interface ParameterResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * If this ParameterResolver.resolve() should be invoked or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param md
	 * @param paramIndex
	 * @param paramAnnotations
	 * @param paramClass
	 * @return true if this parameter should be handled by this resolver,
	 *         otherwise false
	 */
	boolean isTargetParameter(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Resolve parameter.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param md
	 * @param paramIndex
	 * @param paramAnnotations
	 * @param paramClass
	 * @return resolved parameter
	 */
	Object resolve(ActionContext actionContext, Method md, int paramIndex,
			Annotation[] paramAnnotations, Class<?> paramClass);
}
