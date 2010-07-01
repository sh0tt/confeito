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
import org.t2framework.confeito.annotation.SessionAttr;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.exception.InvalidSessionAttributeTypeRuntimeException;
import org.t2framework.confeito.exception.SessionAttributeNotFoundRuntimeException;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * SessionAttributeResolver is a resolver class to inject session attribute.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author taichi
 */
public class SessionAttributeResolver extends AbstractParameterResolver {

	public SessionAttributeResolver() {
		setTargetAnnotationClass(SessionAttr.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return HttpSession.getAttribute if SessionAttr(parameter
	 * annotation).value matches.The return value would be parameter type.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return Object
	 * @throws SessionAttributeNotFoundRuntimeException
	 *             if value is null and {@link SessionAttr#nullable()} is false.
	 * @throws InvalidSessionAttributeTypeRuntimeException
	 *             if something wrong with value.
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		final Session session = actionContext.getSession();
		SessionAttr annotation = findTargetAnnotation(paramAnnotations,
				this.targetAnnotationClass);
		final String key = annotation.value();
		final boolean nullable = annotation.nullable();
		Object value = session.getAttribute(key);
		if (nullable == false && value == null) {
			throw new SessionAttributeNotFoundRuntimeException(key);
		}
		if (value != null) {
			final Class<?> clazz = value.getClass();
			if (paramClass.isAssignableFrom(clazz) == false) {
				throw new InvalidSessionAttributeTypeRuntimeException(
						paramClass, clazz);
			}
		}
		return value;
	}

}
