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
package org.t2framework.confeito.contexts.impl;

import java.io.UnsupportedEncodingException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.exception.NoSuchComponentException;
import org.t2framework.confeito.handler.GlobalExceptionHandler;
import org.t2framework.confeito.internal.ConfigurationUtil;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.WebApplicationCreator;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;
import org.t2framework.confeito.spi.impl.WebApplicationCreatorImpl;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link WebConfiguration}.
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
public class WebConfigurationImpl implements WebConfiguration {

	protected static Logger logger = Logger
			.getLogger(WebConfigurationImpl.class);

	protected FilterConfig config;

	protected ContainerAdapter<?> containerAdapter;

	protected AnnotationResolverCreator resolverCreator;

	protected GlobalExceptionHandler globalExceptionHandler;

	protected WebApplication webApplication;

	public WebConfigurationImpl(final FilterConfig config) {
		this.config = Assertion.notNull(config);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize {@link ContainerAdapter}, {@link GlobalExceptionHandler},
	 * {@link AnnotationResolverCreator}, and {@link WebApplication}.
	 * 
	 * This initialize process is bit heavy because of registration process of
	 * page classes.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public void initialize() throws ServletException {
		final String userConfigPath = getUserConfigPath();
		this.containerAdapter = getContainerAdapter(config, userConfigPath);
		this.globalExceptionHandler = this.containerAdapter
				.createGlobalExceptionHandler();
		this.resolverCreator = createResolverCreator(this.containerAdapter);
		this.webApplication = createWebApplication(this.containerAdapter);
		this.webApplication.initialize();
	}

	protected AnnotationResolverCreator createResolverCreator(
			ContainerAdapter<?> containerAdapter) {
		AnnotationResolverCreator resolverCreator = null;
		if (containerAdapter.hasComponent(AnnotationResolverCreator.class)) {
			resolverCreator = containerAdapter
					.getComponent(AnnotationResolverCreator.class);
		} else {
			resolverCreator = new DefaultAnnotationResolverCreatorImpl();
		}
		if (resolverCreator == null) {
			throw new NoSuchComponentException(AnnotationResolverCreator.class);
		}
		return resolverCreator;
	}

	protected WebApplication createWebApplication(
			ContainerAdapter<?> containerAdapter) {
		WebApplicationCreator creator = null;
		if (containerAdapter.hasComponent(WebApplicationCreator.class)) {
			creator = containerAdapter
					.getComponent(WebApplicationCreator.class);
		} else {
			creator = new WebApplicationCreatorImpl();
		}
		if (creator == null) {
			throw new NoSuchComponentException(WebApplicationCreator.class);
		}
		return creator.createWebApplication(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getResolverCreator()
	 */
	public AnnotationResolverCreator getResolverCreator() {
		return this.resolverCreator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getFilterConfig()
	 */
	public FilterConfig getFilterConfig() {
		return this.config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getEncoding()
	 */
	public String getEncoding() {
		return ConfigurationUtil.getEncoding(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getRootPackages()
	 */
	public String[] getRootPackages() {
		return ConfigurationUtil.getRootPackages(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getUserConfigPath()
	 */
	public String getUserConfigPath() {
		return ConfigurationUtil.getUserConfig(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getExcludeResources()
	 */
	public String[] getExcludeResources() {
		return ConfigurationUtil.getExcludeResources(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getContainerAdapter()
	 */
	public ContainerAdapter<?> getContainerAdapter() {
		return this.containerAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getEagerLoad()
	 */
	@SuppressWarnings("deprecation")
	public boolean getEagerLoad() {
		boolean eagerLoad = ConfigurationUtil.getEagerLoad(this.config);
		if (eagerLoad == false) {
			eagerLoad = containerAdapter.getEagerLoad();
		}
		return eagerLoad;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link ContainerAdapter} from web.xml.This ContainerAdapter must be
	 * initialized here for startup settings.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ContainerAdapter}をweb.xmlから生成して、返します.このContainerAdapterは、
	 * この後のT2のスタートアップ設定のためにこの時点で初期化されていなければなりません.
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @param userConfigPath
	 * @return
	 * @throws ServletException
	 */
	protected ContainerAdapter<?> getContainerAdapter(
			final FilterConfig config, String userConfigPath)
			throws ServletException {
		ContainerAdapter<?> adapter = null;
		try {
			final String adapterClass = ConfigurationUtil
					.getAdapterClassName(config);
			final Class<ContainerAdapter<?>> c = ClassUtil
					.forName(adapterClass);
			adapter = ClassUtil.newInstance(c);
			adapter.setFilterConfig(config);
			adapter.setServletContext(config.getServletContext());
			adapter.init(userConfigPath);
			logger.log("DTDT0054", new Object[] { adapterClass, adapter });
		} catch (Throwable t) {
			throw new ServletException(t);
		}
		return adapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.t2framework.t2.filter.WebConfiguration#setWebApplication(org.t2framework
	 * .t2.contexts.WebApplication)
	 */
	public void setWebApplication(WebApplication webapp) {
		this.webApplication = webapp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getWebApplication()
	 */
	public WebApplication getWebApplication() {
		return this.webApplication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.t2framework.t2.filter.WebConfiguration#getGlobalExceptionHandler()
	 */
	public GlobalExceptionHandler getGlobalExceptionHandler() {
		return globalExceptionHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#getServletContext()
	 */
	public ServletContext getServletContext() {
		return this.config.getServletContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.t2framework.t2.filter.WebConfiguration#setupRequestAndResponse(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void setupRequestAndResponse(HttpServletRequest req,
			HttpServletResponse res) throws UnsupportedEncodingException {
		getContainerAdapter().setRequest(req);
		getContainerAdapter().setResponse(res);
		setupEncoding(req);
	}

	protected void setupEncoding(HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(getEncoding());
		}
	}

	@Override
	public void endRequest() {
		getContainerAdapter().endRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.t2framework.t2.filter.WebConfiguration#destroy()
	 */
	public void destroy() {
		getContainerAdapter().destroy();
		getWebApplication().destroy();
	}

	@Override
	public <T> T getFrameworkComponent(Class<? super T> componentClazz) {
		return (T) getContainerAdapter().getComponent(componentClazz);
	}

	@Override
	public <T> boolean hasFrameworkComponent(Class<? super T> componentClass) {
		return getContainerAdapter().hasComponent(componentClass);
	}
}
