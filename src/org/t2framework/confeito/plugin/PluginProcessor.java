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
package org.t2framework.confeito.plugin;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.plugin.AbstractPlugin.PluginDefaultNavigation;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * PluginProceesor is a processor interface of executing {@link Plugin} at each
 * phase.This interface is the core of plugin architecture for T2 framework and
 * it is not expected user to extend and use in most case.
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
public class PluginProcessor {

	/**
	 * The {@link ContainerAdapter} for finding plugin component.
	 */
	protected final ContainerAdapter<?> containerAdapter;

	/**
	 * The lock object.
	 */
	protected final Object lock = new Object();

	/**
	 * Default navigation for plugin.
	 */
	protected static Navigation DEFAULT_NAVIGATION = PluginDefaultNavigation.INSTANCE;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this instance with {@link ContainerAdapter}.The given container
	 * adapter must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 */
	public PluginProcessor(ContainerAdapter<?> containerAdapter) {
		this.containerAdapter = Assertion.notNull(containerAdapter);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Invoke initialization. Executed at
	 * {@link Filter#init(javax.servlet.FilterConfig)}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 * @param webApplication
	 */
	public void invokeInit(final ServletContext servletContext,
			final WebApplication webApplication) {
		invoke0(servletContext, new Invoker<ServletContext>() {

			@Override
			public ServletContext invoke(Plugin plugin,
					ServletContext servletContext) {
				plugin.initialize(servletContext, webApplication);
				return servletContext;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Notify to begin request processing.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @param response
	 */
	public void beginRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		invoke0(request, new Invoker<HttpServletRequest>() {

			@Override
			public HttpServletRequest invoke(Plugin plugin,
					HttpServletRequest request) {
				plugin.beginRequestProcessing(request, response);
				return request;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook of end request processing.You can flush or release any resources for
	 * this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @param response
	 */
	public void endRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		invoke0(request, new Invoker<HttpServletRequest>() {

			@Override
			public HttpServletRequest invoke(Plugin plugin,
					HttpServletRequest request) {
				plugin.endRequestProcessing(request, response);
				return request;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Creates {@link HttpServletRequest} or just returns original.Usually it
	 * does nothing but in case of some reasons to use your own request
	 * instance, do wrap original request using
	 * {@link HttpServletRequestWrapper}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @param response
	 * @return created {@link HttpServletRequest}
	 */
	public HttpServletRequest createRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		HttpServletRequest req = invoke0(request,
				new Invoker<HttpServletRequest>() {

					@Override
					public HttpServletRequest invoke(Plugin plugin,
							HttpServletRequest req) {
						return plugin.createRequest(req, response);
					}

				});
		return req;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Creates {@link HttpServletRequest} or just returns original.Usually it
	 * does nothing but in case of some reasons to use your own request
	 * instance, do wrap original request using
	 * {@link HttpServletRequestWrapper}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @param response
	 * @return created {@link HttpServletResponse}
	 */
	public HttpServletResponse createResponse(final HttpServletRequest request,
			final HttpServletResponse response) {
		HttpServletResponse res = invoke0(response,
				new Invoker<HttpServletResponse>() {

					@Override
					public HttpServletResponse invoke(Plugin plugin,
							HttpServletResponse response) {
						return plugin.createResponse(request, response);
					}

				});
		return res;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook just after page instance creation.You can modify original page
	 * instance or do AOP things or whatever do with newly created page
	 * instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 * @param page
	 * @return navigation instance
	 */
	public Navigation invokeComponentCreated(final WebContext context,
			final Object page) {
		return invokeNavigation(page, new NavigationInvoker<Object>() {

			@Override
			public Navigation invoke(Plugin plugin, Object param) {
				return plugin.componentCreated(context, param);
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook just before invoking an action method.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param methodDesc
	 * @param page
	 * @param args
	 * @return navigation instance
	 */
	public Navigation beforeActionInvoke(final ActionContext actionContext,
			final Method targetMethod, final Object page, final Object[] args) {
		return invokeNavigation(page, new NavigationInvoker<Object>() {

			@Override
			public Navigation invoke(Plugin plugin, Object param) {
				return plugin.beforeActionInvoke(actionContext, targetMethod,
						param, args);
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook just after invoking an action method.This method must execute
	 * whether action invoking causes an error or not.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param methodDesc
	 * @param page
	 * @param args
	 * @param result
	 * @return navigation instance
	 */
	public Navigation afterActionInvoke(final ActionContext actionContext,
			final Method targetMethod, final Object page, final Object[] args,
			final Navigation result) {
		return invokeNavigation(page, new NavigationInvoker<Object>() {

			@Override
			public Navigation invoke(Plugin plugin, Object param) {
				return plugin.afterActionInvoke(actionContext, targetMethod,
						param, args, result);
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook just before {@link Navigation#execute(WebContext)}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 */
	public void beforeNavigation(final WebContext context) {
		invoke0(context, new Invoker<WebContext>() {

			@Override
			public WebContext invoke(Plugin plugin, WebContext ctx) {
				plugin.beforeNavigation(ctx);
				return ctx;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook just after {@link Navigation#execute(WebContext)}.This method must
	 * execute whether invoking {@link Navigation#execute(WebContext)} causes an
	 * error or not.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 */
	public void afterNavigation(WebContext context) {
		invoke0(context, new Invoker<WebContext>() {

			@Override
			public WebContext invoke(Plugin plugin, WebContext ctx) {
				plugin.afterNavigation(ctx);
				return ctx;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Destroy any resources.Executed at {@link Filter#destroy()}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 * @param webApplication
	 */
	public void destroy(final ServletContext servletContext,
			final WebApplication webApplication) {
		invoke0(servletContext, new Invoker<ServletContext>() {

			@Override
			public ServletContext invoke(Plugin plugin,
					ServletContext servletContext) {
				plugin.destroy(servletContext, webApplication);
				return servletContext;
			}

		});
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Invoke {@link Plugin} by {@link Invoker}.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param org
	 * @param invoker
	 * @return
	 */
	protected <T> T invoke0(T org, Invoker<T> invoker) {
		List<Plugin> plugins = getPlugins();
		if (plugins == null || plugins.isEmpty()) {
			return org;
		}
		T instance = org;
		synchronized (instance) {
			for (Plugin p : plugins) {
				p.setContainerAdapter(containerAdapter);
				instance = invoker.invoke(p, instance);
			}
		}
		return instance;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Invoke {@link NavigationInvoker} for {@link Navigation} .
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param page
	 * @param invoker
	 * @return
	 */
	protected <T> Navigation invokeNavigation(T page,
			NavigationInvoker<T> invoker) {
		List<Plugin> plugins = getPlugins();
		if (plugins == null || plugins.isEmpty()) {
			return DEFAULT_NAVIGATION;
		}
		T param = page;
		Navigation navigation = DEFAULT_NAVIGATION;
		synchronized (lock) {
			for (Plugin p : plugins) {
				p.setContainerAdapter(containerAdapter);
				navigation = invoker.invoke(p, param);
				if (navigation != null
						&& navigation.equals(DEFAULT_NAVIGATION) == false) {
					break;
				}
			}
		}
		return navigation;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Private invoker interface.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @author shot
	 * 
	 * @param <T>
	 */
	private static interface Invoker<T> {
		T invoke(Plugin plugin, T param);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Private navigation result invoker interface.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @author shot
	 * 
	 * @param <T>
	 */
	private static interface NavigationInvoker<T> {
		Navigation invoke(Plugin plugin, T param);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get list of {@link Plugin} which is handled by this processor.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return list of plugin
	 */
	public List<Plugin> getPlugins() {
		if (this.containerAdapter.hasComponent(Plugin.class)) {
			return containerAdapter.getComponents(Plugin.class);
		} else {
			return Collections.emptyList();
		}
	}

}
