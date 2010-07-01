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
package org.t2framework.confeito.contexts;

import java.util.Locale;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Base context class for T2, and it is stored at each thread using
 * ThreadLocal.WebContext is created when each request is accepted to T2 entry
 * point(usually it is {@link org.t2framework.confeito.filter.T2Filter}).
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
public abstract class WebContext implements
		Context<Request, Response, Application, Session> {

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link ThreadLocal} for pooling {@link WebContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static ThreadLocal<WebContext> pool = createThreadLocal();

	/**
	 * <#if locale="en">
	 * <p>
	 * Default {@link Locale}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected Locale defaultLocale;

	/**
	 * <#if locale="en">
	 * <p>
	 * Action context.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected ActionContext actionContext;

	/**
	 * <#if locale="en">
	 * <p>
	 * Container adapter.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected ContainerAdapter<?> containerAdapter;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct {@link WebContext}.Default {@link Locale} and clean method is
	 * set.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public WebContext() {
		this(Locale.getDefault());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct {@link WebContext} with default {@link Locale}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param defaultLocale
	 */
	public WebContext(final Locale defaultLocale) {
		this.defaultLocale = Assertion.notNull(defaultLocale);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Store {@link WebContext} to {@link ThreadLocal}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 */
	public static void set(WebContext context) {
		pool.set(Assertion.notNull(context));
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link WebContext} from {@link ThreadLocal}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return web context at this thread
	 */
	public static WebContext get() {
		return pool.get();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Clear {@link WebContext} from thread local.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static void clear() {
		pool.remove();
	}

	private static <T> ThreadLocal<T> createThreadLocal() {
		return new ThreadLocal<T>();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get request {@link Locale}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return requested locale
	 */
	public Locale getRequestLocale() {
		return getRequest().getLocale();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get default server {@link Locale}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return server locale
	 */
	public Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Forward by {@link Request}.The given path must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @see org.t2framework.confeito.contexts.Request#forward(String)
	 */
	public void forward(final String path) {
		Assertion.notNull(path);
		getRequest().forward(path);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Redirect by {@link Response}.The given path must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @see org.t2framework.confeito.contexts.Response#redirect(String)
	 */
	public void redirect(final String path) {
		Assertion.notNull(path);
		getResponse().redirect(path);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ActionContext}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return actionContext
	 */
	public ActionContext getActionContext() {
		return actionContext;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ActionContext}.The given ActionContext must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 */
	public void setActionContext(ActionContext actionContext) {
		this.actionContext = Assertion.notNull(actionContext);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ContainerAdapter}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return container adapter
	 */
	public ContainerAdapter<?> getContainerAdapter() {
		return containerAdapter;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ContainerAdapter}.The given ContainerAdapter must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 */
	public void setContainerAdapter(ContainerAdapter<?> containerAdapter) {
		Assertion.notNull(containerAdapter);
		this.containerAdapter = containerAdapter;
	}

}
