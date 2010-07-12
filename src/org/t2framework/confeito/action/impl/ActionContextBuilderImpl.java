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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionContextBuilder;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.exception.NoActionMethodFoundRuntimeException;
import org.t2framework.confeito.exception.NoDefaultActionMethodFoundRuntimeException;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.method.ActionMethodResolver;
import org.t2framework.confeito.model.ActionMethod;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * ActionContextBuilderImpl is an implementation class of
 * {@link ActionContextBuilder}.
 * </p>
 * <#else>
 * <p>
 * ActionContextBuilderImplは、{@link ActionContextBuilder}の実装クラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class ActionContextBuilderImpl implements ActionContextBuilder {

	protected Map<Class<? extends Annotation>, ActionMethodResolver> methodResolvers = new HashMap<Class<? extends Annotation>, ActionMethodResolver>();

	protected ActionMethodResolver defaultResolver;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Construct this object. The both parameters, {@link ActionMethodResolver}
	 * map and default resolver must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * このオブジェクトを構築します. {@link ActionMethodResolver}のMapと defaultResolver
	 * はnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param methodResolvers
	 * @param defaultResolver
	 */
	public ActionContextBuilderImpl(
			Map<Class<? extends Annotation>, ActionMethodResolver> methodResolvers,
			ActionMethodResolver defaultResolver) {
		this.methodResolvers = Assertion.notNull(methodResolvers);
		this.defaultResolver = Assertion.notNull(defaultResolver);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Build an {@link ActionContext} using {@link ActionMethodResolver}.The
	 * building process is:
	 * </p>
	 * <ul>
	 * <li>set the {@link PageComponent} that comes from
	 * {@link ActionInvokingContext} and iterate each {@link ActionMethod}.</li>
	 * <li>Find and correct taget candidate {@link MethodDesc} using
	 * {@link ActionMethodResolver#isMatch(ActionContext, Annotation, MethodDesc)
	 * .}</li>
	 * <li>Find the target {@link MethodDesc} and set up page variables which is
	 * resolved by
	 * {@link ActionMethodResolver#resolve(ActionContext, Annotation, MethodDesc)
	 * .}</li>
	 * <li>?resolve again?</li>
	 * </ul>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionContextBuilder#build(org.t2framework.confeito.action.ActionInvokingContext)
	 * @param invokingActionContext
	 * @return build action context
	 * @throws NoActionMethodFoundRuntimeException
	 */
	@Override
	public ActionContext build(final ActionInvokingContext invokingActionContext)
			throws NoActionMethodFoundRuntimeException {
		Assertion.notNull(invokingActionContext);
		final PageComponent current = invokingActionContext
				.getCurrentPageDesc();
		final ActionContext actionContext = invokingActionContext
				.getActionContext();
		actionContext.setTargetPageDesc(current);
		List<Method> matchList = null;
		int maxMatchCount = 0;
		search: for (Method methodDesc : current.getActionMethod()) {
			actionContext.clearMatchCount();
			final boolean continueToNext = buildActionMethod(actionContext,
					methodDesc);
			if (continueToNext) {
				continue search;
			}
			final int matchCount = actionContext.getMatchCount();
			if (maxMatchCount < matchCount) {
				maxMatchCount = matchCount;
				matchList = new ArrayList<Method>();
				matchList.add(methodDesc);
			} else if (matchCount == maxMatchCount) {
				if (matchList == null) {
					continue search;
				}
				matchList.add(methodDesc);
			}
		}
		buildActionContext(actionContext, current, matchList);
		invokingActionContext.setActionStatus(ActionStatus.FOUND_ACTION_METHOD);
		return actionContext;
	}

	protected void buildActionContext(ActionContext actionContext,
			PageComponent current, List<Method> matchList) {
		Method methodDesc = findMethodDescFromMatchList(actionContext, current,
				matchList);
		resolvePageVariables(actionContext);
		resolveActionMethod(actionContext, methodDesc);
		actionContext.setTargetMethodDesc(methodDesc);
	}

	protected Method findMethodDescFromMatchList(ActionContext actionContext,
			PageComponent current, List<Method> matchList)
			throws NoActionMethodFoundRuntimeException {
		Method ret = null;
		if (matchList != null) {
			if (0 < matchList.size()) {
				ret = matchList.get(0);
			} else {
				throw new NoActionMethodFoundRuntimeException(current
						.getActionMethod().getMethodNames());
			}
		} else {
			ret = current.getDefaultMethod();
			setupForDefaultMethodDesc(actionContext, current, ret);
		}
		return ret;
	}

	protected boolean buildActionMethod(final ActionContext actionContext,
			final Method methodDesc) {
		boolean continueToNext = false;
		for (Annotation annotation : methodDesc.getAnnotations()) {
			final ActionMethodResolver resolver = findAnnotationResolver(annotation);
			if (resolver == null) {
				continue;
			}
			boolean match = buildActionMethodByResolver(resolver,
					actionContext, annotation, methodDesc);
			if (match == false) {
				continueToNext = true;
				break;
			}
		}
		return continueToNext;
	}

	protected boolean buildActionMethodByResolver(
			ActionMethodResolver resolver, ActionContext actionContext,
			Annotation annotation, Method methodDesc) {
		boolean resolved = false;
		if (resolver.isMatch(actionContext, annotation, methodDesc)) {
			actionContext.incrementMatchCount();
			resolved = true;
		}
		return resolved;
	}

	protected void resolvePageVariables(ActionContext actionContext) {
		String pagePath = PathUtil.getPagePath(actionContext.getRequest());
		PageComponent pd = actionContext.getTargetPageDesc();
		Map<String, String> varMap = pd.getUrlTemplate().parseUrl(pagePath);
		if (varMap != null) {
			for (Entry<String, String> entry : varMap.entrySet()) {
				actionContext.addVariables(entry.getKey(), entry.getValue());
			}
		}
	}

	protected void resolveActionMethod(ActionContext actionContext,
			Method methodDesc) {
		for (Annotation annotation : methodDesc.getAnnotations()) {
			final ActionMethodResolver resolver = findAnnotationResolver(annotation);
			if (resolver == null) {
				continue;
			}
			resolver.preResolve(actionContext, annotation, methodDesc);
			try {
				resolver.resolve(actionContext, annotation, methodDesc);
			} finally {
				resolver.postResolve(actionContext, annotation, methodDesc);
			}
		}
	}

	protected void setupForDefaultMethodDesc(final ActionContext actionContext,
			final PageComponent current, final Method methodDesc) {
		if (current.hasDefaultMethod() == false) {
			throw new NoDefaultActionMethodFoundRuntimeException();
		}
		defaultResolver.resolve(actionContext, current.getDefaultAnnotation(),
				methodDesc);
	}

	protected ActionMethodResolver findAnnotationResolver(Annotation annotation) {
		Assertion.notNull(annotation);
		return methodResolvers.get(annotation.annotationType());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ActionMethodResolver} as default resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public void setDefaultActionAnnotationResolver(
			final ActionMethodResolver defaultResolver) {
		this.defaultResolver = Assertion.notNull(defaultResolver);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Add {@link ActionMethodResolver} for target annotation class.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public void addActionAnnotationResolver(
			Class<? extends Annotation> annotationClass,
			ActionMethodResolver resolver) {
		methodResolvers.put(annotationClass, resolver);
	}

}
