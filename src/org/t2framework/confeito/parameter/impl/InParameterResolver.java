package org.t2framework.confeito.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.annotation.In;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

/**
 * 
 * <#if locale="en">
 * <p>
 * ParameterResolver for {@link In}.
 * </p>
 * <#else>
 * <p>
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class InParameterResolver extends AbstractParameterResolver {

	public InParameterResolver() {
		setTargetAnnotationClass(In.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class paramClass) {
		ContainerAdapter<?> containerAdapter = WebContext.get()
				.getContainerAdapter();
		return containerAdapter.getComponent(paramClass);
	}

}
