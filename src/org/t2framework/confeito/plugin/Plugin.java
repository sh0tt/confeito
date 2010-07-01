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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * Plugin is an extension point to extend and update T2 behavior for some
 * reason.Plugin has several phases below to extend:
 * 
 * <pre>
 *  - initialize              : Initialize phase as Filter.init().
 *  - beginRequestProcessing  : Begin request processing as the first point of Filter.doFilter().
 *  - createRequest           : Create request.
 *  - createResponse          : Create response.
 *  - componentCreated        : Create the Page instance.
 *  - beforeActionInvoke      : Just before invoking the Page's action method.
 *  - afterActionInvoke       : Just after invoking the Page's action method.
 *  - beforeNavigation        : Just before executing Navigation like forward or redirect.
 *  - afterNavigation         : Just after executing Navigation.
 *  - endRequestProcessing    : End request processing as the end point of Filter.doFilter().
 *  - destroy                 : Destroy phase as Filter.destroy().
 * </pre>
 * 
 * The user should extend {@code org.t2framework.plugin.AbstractPlugin} instead
 * of {@code org.t2framework.plugin.Plugin} because of compatibility matters.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.plugin.AbstractPlugin
 */
public interface Plugin {

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize phase. <br />
	 * Initialize method is expected to configure whole web application itself.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 *            : ServletContext
	 * @param webApplication
	 *            : WebApplication as the holder of all Page meta information.
	 */
	void initialize(final ServletContext servletContext,
			final WebApplication webApplication);

	/**
	 * <#if locale="en">
	 * <p>
	 * Begin request processing.At that phase, there is no WebContext created,
	 * simply HttpServletRequest and HttpServletResponse are provided.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            : HttpServletRequest
	 * @param response
	 *            : HttpServletResponse
	 */
	void beginRequestProcessing(final HttpServletRequest request,
			final HttpServletResponse response);

	/**
	 * <#if locale="en">
	 * <p>
	 * Create HttpServletRequest to use.If needs to wrap HttpServletRequest,
	 * this is the best phase to do.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            : HttpServletRequest
	 * @param response
	 *            : HttpServletResponse
	 * @return wrapped HttpServletRequest.
	 */
	HttpServletRequest createRequest(final HttpServletRequest request,
			final HttpServletResponse response);

	/**
	 * <#if locale="en">
	 * <p>
	 * Create HttpServletResponse to use.If needs to wrap HttpServletResponse,
	 * this is the best phase to do.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            : HttpServletRequest
	 * @param response
	 *            : HttpServletResponse
	 * @return wrapped HttpServletResponse.
	 */
	HttpServletResponse createResponse(final HttpServletRequest request,
			final HttpServletResponse response);

	/**
	 * <#if locale="en">
	 * <p>
	 * The phase of creating page instance. If extended this method returns
	 * Navigation, except NoOperation, then T2 execute Navigation.execute()
	 * immediately.If so, the action method is not invoked at all.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 *            : WebContext
	 * @param page
	 *            : created the page instance
	 * @return Navigation instance.
	 */
	Navigation componentCreated(final WebContext context, final Object page);

	/**
	 * <#if locale="en">
	 * <p>
	 * Just before action invoking. For instance to use this phase, you can
	 * check the page instance if it passes external requirements that the web
	 * application might have like authentication check. If you do not want T2
	 * invoke the target method, return Navigation instance except NoOperation.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 *            : ActionContext, the context object that lives in the action
	 *            invoking phase.
	 * @param targetMethod
	 *            : MethodDesc, the target method meta instance.
	 * @param page
	 *            : the page instance
	 * @param args
	 *            : arguments for the target method.
	 * @return Navigation instance.
	 */
	Navigation beforeActionInvoke(final ActionContext actionContext,
			final Method targetMethod, final Object page, final Object[] args);

	/**
	 * <#if locale="en">
	 * <p>
	 * Just after action invoking. You can do like overriding Navigation
	 * instance that is invoked by the target method, or cleaning up some
	 * properties that the page instance might have.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 *            : ActionContext, the context object that lives in the action
	 *            invoking phase.
	 * @param targetMethod
	 *            : MethodDesc, the target method meta instance.
	 * @param page
	 *            : the page instance
	 * @param args
	 *            : arguments for the target method.
	 * @param result
	 * @return Navigation instance.
	 */
	Navigation afterActionInvoke(final ActionContext actionContext,
			final Method targetMethod, final Object page, final Object[] args,
			Navigation result);

	/**
	 * <#if locale="en">
	 * <p>
	 * Before Navigation instance, which is given by the result of action
	 * invoked, executing.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 *            : WebContext
	 */
	void beforeNavigation(final WebContext context);

	/**
	 * <#if locale="en">
	 * <p>
	 * After Navigation instance executing.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 *            : WebContext
	 */
	void afterNavigation(final WebContext context);

	/**
	 * <#if locale="en">
	 * <p>
	 * The phase of end request processing. WebContext is also alive, so you can
	 * use WebContext.get() to get whole information.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            : HttpServletRequest
	 * @param response
	 *            : HttpServletResponse
	 */
	void endRequestProcessing(final HttpServletRequest request,
			final HttpServletResponse response);

	/**
	 * <#if locale="en">
	 * <p>
	 * The Destroy phase like Filter.destroy().
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 *            : ServletContext
	 * @param webApplication
	 *            : WebApplication as the holder of all Page meta information.
	 */
	void destroy(final ServletContext servletContext,
			final WebApplication webApplication);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set {@link ContainerAdapter}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 */
	void setContainerAdapter(ContainerAdapter<?> containerAdapter);

}
