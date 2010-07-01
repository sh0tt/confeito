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

import java.io.UnsupportedEncodingException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.handler.GlobalExceptionHandler;
import org.t2framework.confeito.spi.AnnotationResolverCreator;

/**
 * <#if locale="en">
 * <p>
 * WebConfiguration interface is a configuration for T2 framework.It is an
 * interface between web.xml configuration and T2 internal settings.Basically,
 * WebConfiguration effects whole of the framework behaviors.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface WebConfiguration {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Initialization method.This method must invoke before any other operation
	 * of this interface.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @throws ServletException
	 *             when initializing prcess fails
	 */
	void initialize() throws ServletException;

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link AnnotationResolverCreator}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return annotation resolver creator
	 * @see org.t2framework.confeito.spi.AnnotationResolverCreator
	 */
	AnnotationResolverCreator getResolverCreator();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link FilterConfig}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return current filter configuration
	 * @see javax.servlet.FilterConfig
	 */
	FilterConfig getFilterConfig();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get user setting encoding for this web application.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return encoding
	 */
	String getEncoding();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get user setting root packages.It is not permitted not to have any root
	 * packages. It must not be null.See
	 * {@link org.t2framework.confeito.internal.ConfigurationUtil#getRootPackages}
	 * .
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return root packages
	 * @see org.t2framework.confeito.internal.ConfigurationUtil
	 */
	String[] getRootPackages();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get user setting configuration path.This configuration path is for
	 * {@link ContainerAdapter} setting.It can be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return user configuration path
	 */
	String getUserConfigPath();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get resources that T2 framework must not serve.It is like {@literal
	 * .css/.js/.gif} things like that.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return exclude resources
	 */
	String[] getExcludeResources();

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
	 * @return adapter
	 * @see org.t2framework.confeito.adapter.ContainerAdapter
	 */
	ContainerAdapter<?> getContainerAdapter();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get eager load option.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if eagerload page classes, otherwise false
	 */
	boolean getEagerLoad();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link WebApplication}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return web application
	 */
	WebApplication getWebApplication();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link GlobalExceptionHandler}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return global exception handler
	 */
	GlobalExceptionHandler getGlobalExceptionHandler();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ServletContext}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return servlet context
	 * @see javax.servlet.ServletContext
	 */
	ServletContext getServletContext();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set up {@link WebConfiguration} and its internal instances using
	 * {@link HttpServletRequest} and {@link HttpServletResponse}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	void setupRequestAndResponse(HttpServletRequest req, HttpServletResponse res)
			throws UnsupportedEncodingException;

	/**
	 * <#if locale="en">
	 * <p>
	 * True if the specified framework component can take from
	 * {@link ContainerAdapter}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param componentClass
	 * @return true if the component exists, otherwise false
	 */
	<T> boolean hasFrameworkComponent(Class<? super T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get framework component from specified class.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param componentClass
	 * @return a component instance
	 */
	<T> T getFrameworkComponent(Class<? super T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * This method invoke after request process end.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストが終了した後に呼び出されます.
	 * </p>
	 * </#if>
	 */
	void endRequest();

	/**
	 * <#if locale="en">
	 * <p>
	 * Destroy any resources that the implementation may have.This method must
	 * invoke after any other operation, usually shutdown time.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	void destroy();

}
