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
package org.t2framework.confeito.action.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.exception.NoParameterResolverRuntimeException;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.parameter.ParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * {@link ActionArgumentsPreparationFilterImpl} is an action filer for preparing
 * action arguments.How to find and collect arguments is by parameter annotation
 * and {@link ParameterResolver}.This filter should be invoked before action
 * invoking filter such as {@link ActionInvokerFilterImpl}.
 * </p>
 * <#else>
 * <p>
 * {@link ActionArgumentsPreparationFilterImpl} は、実行するアクションのメソッド引数を準備するための
 * ActionFilterです.引数を解決するために、メソッド引数のパラメータアノテーションと、{@link ParameterResolver}を
 * 使用します.このフィルターは、{@link ActionInvokerFilterImpl}
 * など、アクションを呼び出すフィルターの前に呼び出す必要があります.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionArgumentsPreparationFilterImpl implements ActionFilter {

	/**
	 * <#if locale="en">
	 * <p>
	 * Find and saves action arguments to {@link ActionInvokingContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * アクションのメソッド引数を解決し、{@link ActionInvokingContext}にセットします.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionFilter#invoke(org.t2framework.confeito.action.ActionInvokingContext,
	 *      org.t2framework.t2.contexts.PageComponent,
	 *      org.t2framework.confeito.action.ActionFilterChain)
	 * @param invokingContext
	 * @param pageDesc
	 * @param chain
	 * @throws NoParameterResolverRuntimeException
	 *             in case of there is no parameters available for the action.
	 */
	@Override
	public void invoke(final ActionInvokingContext invokingContext,
			final PageComponent pageDesc, final ActionFilterChain chain) {
		final List<ParameterResolver> parameterResolvers = invokingContext
				.createParameterResolvers();
		final Object[] args = prepareActionArguments(invokingContext,
				parameterResolvers);
		invokingContext.setActionArguments(args);
		chain.invokeChain(invokingContext, pageDesc);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Prepare action arguments.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行に必要な引数を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param invokingContext
	 * @param parameterResolvers
	 * @return action arguments, not null.
	 * @throws NoParameterResolverRuntimeException
	 *             in case of there is no parameters available for the action.
	 */
	protected Object[] prepareActionArguments(
			final ActionInvokingContext invokingContext,
			final List<ParameterResolver> parameterResolvers) {
		final ActionContext actionContext = invokingContext.getActionContext();
		final Method actionMethod = actionContext.getTargetMethodDesc();
		final Class<?>[] paramClasses = actionMethod.getParameterTypes();
		return prepareActionArgumentsByAnnotation(actionContext, actionMethod,
				paramClasses, parameterResolvers);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Prepare and gather action arguments using
	 * {@link org.t2framework.confeito.parameter.ParameterResolver}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link org.t2framework.confeito.parameter.ParameterResolver}
	 * を使って、アクションの引数を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.parameter.ParameterResolver
	 * @param actionContext
	 * @param actionMethod
	 * @param paramClasses
	 * @param parameterResolvers
	 * @return action arguments, not null.
	 * @throws NoParameterResolverRuntimeException
	 *             in case of there is no parameters available for the action.
	 */
	protected Object[] prepareActionArgumentsByAnnotation(
			final ActionContext actionContext, final Method actionMethod,
			final Class<?>[] paramClasses,
			final List<ParameterResolver> parameterResolvers) {
		final Annotation[][] parameterAnnotations = actionMethod
				.getParameterAnnotations();
		final Object[] ret = new Object[paramClasses.length];
		resolve: for (int paramIndex = 0; paramIndex < paramClasses.length; paramIndex++) {
			final Annotation[] argAnnotations = parameterAnnotations[paramIndex];
			final Class<?> argType = paramClasses[paramIndex];
			for (ParameterResolver resolver : parameterResolvers) {
				if (resolver.isTargetParameter(actionContext, actionMethod,
						paramIndex, argAnnotations, argType)) {
					ret[paramIndex] = resolver.resolve(actionContext,
							actionMethod, paramIndex, argAnnotations, argType);
					continue resolve;
				}
			}
			throw new NoParameterResolverRuntimeException(argType);
		}
		return ret;
	}
}
