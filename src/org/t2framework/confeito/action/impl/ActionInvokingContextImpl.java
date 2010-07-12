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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionContextBuilder;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.method.ActionMethodResolver;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.parameter.ParameterResolver;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * 
 * <#if locale="en">
 * <p>
 * An implementation of {@link ActionInvokingContext}.
 * </p>
 * <#else>
 * <p>
 * {@link ActionInvokingContext}の実装クラス.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionInvokingContextImpl implements ActionInvokingContext {

	protected WebContext context;

	protected ActionStatus actionStatus = ActionStatus.NONE;

	protected Navigation navigation;

	protected AnnotationResolverCreator resolverCreator;

	protected PluginProcessor pluginProcessor;

	protected AtomicInteger count = new AtomicInteger(0);

	protected ContainerAdapter<?> containerAdapter;

	protected Object[] actionArguments;

	protected Object page;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Construct this instance with {@link WebContext}, {@link WebConfiguration}
	 * , and {@link PluginProcessor}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebContext},{@link WebConfiguration}, {@link PluginProcessor}
	 * を使って、このクラスのインスタンスを構築します.
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 * @param webConfig
	 * @param pluginProcessor
	 */
	public ActionInvokingContextImpl(WebContext context,
			WebConfiguration webConfig, PluginProcessor pluginProcessor) {
		this(context, webConfig.getResolverCreator(), webConfig
				.getContainerAdapter(), pluginProcessor);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this instance with {@link WebContext}, {@link WebConfiguration}, {@link ContainerAdapter}, {@link PluginProcessor}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebContext},{@link WebConfiguration},{@link ContainerAdapter},
	 * {@link PluginProcessor}を使って、このクラスのインスタンスを構築します.
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 * @param creator
	 * @param containerAdapter
	 * @param pluginProcessor
	 */
	public ActionInvokingContextImpl(WebContext context,
			AnnotationResolverCreator creator,
			ContainerAdapter<?> containerAdapter,
			PluginProcessor pluginProcessor) {
		this.context = context;
		this.resolverCreator = creator;
		this.containerAdapter = containerAdapter;
		this.pluginProcessor = pluginProcessor;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ActionContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionContext}を返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public ActionContext getActionContext() {
		return this.context.getActionContext();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link WebContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebContext}を返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public WebContext getWebContext() {
		return context;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ActionStatus}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public ActionStatus getActionStatus() {
		return actionStatus;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ActionStatus}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void setActionStatus(ActionStatus actionStatus) {
		this.actionStatus = actionStatus;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set result {@link Navigation}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void setResultNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get result {@link Navigation}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Navigation getResultNavigation() {
		return navigation;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get result {@link AnnotationResolverCreator}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public AnnotationResolverCreator getResolverCreator() {
		return resolverCreator;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get result {@link ContainerAdapter}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public ContainerAdapter<?> getContainerAdapter() {
		return containerAdapter;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get result {@link PluginProcessor}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public PluginProcessor getPluginProcessor() {
		return pluginProcessor;
	}

	protected ActionContextBuilder createActionContextBuilder() {
		return new ActionContextBuilderImpl(createActionMethodResolvers(),
				createDefaultActionMethodResolver());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create page instance. It can be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.adapter.ContainerAdapter
	 */
	@Override
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public Object createPage(PageComponent pageDesc) {
		Assertion.notNull(pageDesc, "PageDesc");
		ContainerAdapter<?> adapter = getContainerAdapter();
		if (adapter != null) {
			Class componentClass = pageDesc.getPageClass();
			return adapter.getComponent(componentClass);
		} else {
			return null;
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create list of {@link ParameterResolver}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.spi.AnnotationResolverCreator#createParameterResolvers(ContainerAdapter,
	 *      WebContext)
	 */
	@Override
	public List<ParameterResolver> createParameterResolvers() {
		return getResolverCreator().createParameterResolvers(
				getContainerAdapter(), getWebContext());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Build {@link ActionContext} by {@link ActionContextBuilder}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionContextBuilder#build(ActionInvokingContext)
	 */
	@Override
	public synchronized ActionContext buildActionContext() {
		return createActionContextBuilder().build(this);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set to prepare processing next {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void nextPageDesc() {
		count.incrementAndGet();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get current {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public PageComponent getCurrentPageDesc() {
		return getActionContext().getPageDescCandidates().get(count.get());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get action arguments.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Object[] getActionArguments() {
		return this.actionArguments;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set action arguments.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void setActionArguments(Object[] actionArguments) {
		this.actionArguments = actionArguments;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get page instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Object getPage() {
		return this.page;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set page instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void setPage(Object page) {
		this.page = page;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create map of {@link ActionMethodResolver}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see AnnotationResolverCreator#createActionMethodResolvers(ContainerAdapter)
	 */
	@Override
	public Map<Class<? extends Annotation>, ActionMethodResolver> createActionMethodResolvers() {
		return getResolverCreator().createActionMethodResolvers(
				getContainerAdapter());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create default {@link ActionMethodResolver}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see AnnotationResolverCreator#createDefaultActionMethodResolver(ContainerAdapter)
	 */
	public ActionMethodResolver createDefaultActionMethodResolver() {
		return getResolverCreator().createDefaultActionMethodResolver(
				getContainerAdapter());
	}
}
