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
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.annotation.Form;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.spi.FormResolver;
import org.t2framework.confeito.spi.impl.FormResolverImpl;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * <#if locale="en">
 * <p>
 * FormParameterResolver is a concrete class of ParameterResolver, and it
 * handles &#064;Form. Currently, the implementation of this class is:
 * 
 * <pre>
 *  -get parameter class and instantiate by default constructor.It is expected POJO class.
 *  -set value if property name and httpServletRequest.getParameter() are same.
 * </pre>
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
public class FormParameterResolver extends AbstractParameterResolver {

	protected static final FormResolver DEFAULT_RESOLVER = new FormResolverImpl();

	public FormParameterResolver() {
		setTargetAnnotationClass(Form.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create parameter class instance and set request parameter by
	 * {@link FormResolver}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.spi.FormResolver
	 */
	@SuppressWarnings("unchecked")
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		Form form = findTargetAnnotation(paramAnnotations,
				targetAnnotationClass);
		if (form == null) {
			throw new IllegalStateException("@Form must not be null.");
		}
		final WebContext context = WebContext.get();
		FormResolver resolver = null;
		final Class<? extends FormResolver> resolverClass = form
				.resolverClass();
		final ContainerAdapter<?> containerAdapter = context
				.getContainerAdapter();
		if (resolverClass != FormResolverImpl.class) {
			resolver = containerAdapter
					.getComponent((Class<FormResolver>) resolverClass);
			if (resolver == null) {
				resolver = ClassUtil.newInstance(resolverClass);
			}
		} else {
			resolver = DEFAULT_RESOLVER;
		}
		final ErrorInfo errorInfo = actionContext.getErrorInfo();
		final String className = paramClass.getName();
		Object o = null;
		try {
			if (containerAdapter.hasComponent(paramClass)) {
				o = containerAdapter.getComponent((Class<Object>) paramClass);
			} else {
				o = ClassUtil.newInstance(paramClass);
			}
		} catch (Throwable t) {
			errorInfo.addErrorInfo(className, t);
			return null;
		}
		Component<Object> component = new Component<Object>(o);
		resolver.resolve(form, context, component, errorInfo);
		return o;
	}
}
