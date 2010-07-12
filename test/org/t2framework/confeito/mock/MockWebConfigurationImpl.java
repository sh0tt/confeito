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
package org.t2framework.confeito.mock;

import java.io.UnsupportedEncodingException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.handler.GlobalExceptionHandler;
import org.t2framework.confeito.spi.AnnotationResolverCreator;

public class MockWebConfigurationImpl implements WebConfiguration {

	protected FilterConfig config;

	protected ContainerAdapter<?> containerAdapter;

	protected AnnotationResolverCreator resolverCreator;

	protected GlobalExceptionHandler globalExceptionHandler;

	protected WebApplication webApplication;

	protected boolean eagerload;

	protected String encoding = "UTF-8";

	private String userConfigPath;

	private ServletContext servletContext;

	private String[] rootPackages;

	private String[] excludeResources;

	private FilterConfig filterConfig;

	public void setConfig(FilterConfig config) {
		this.config = config;
	}

	public void setContainerAdapter(ContainerAdapter<?> containerAdapter) {
		this.containerAdapter = containerAdapter;
	}

	public void setResolverCreator(AnnotationResolverCreator resolverCreator) {
		this.resolverCreator = resolverCreator;
	}

	public void setGlobalExceptionHandler(
			GlobalExceptionHandler globalExceptionHandler) {
		this.globalExceptionHandler = globalExceptionHandler;
	}

	public void setWebApplication(WebApplication webApplication) {
		this.webApplication = webApplication;
	}

	@Override
	public void destroy() {
	}

	@Override
	public ContainerAdapter<?> getContainerAdapter() {
		return this.containerAdapter;
	}

	@Override
	public boolean getEagerLoad() {
		return this.eagerload;
	}

	@Override
	public String getEncoding() {
		return this.encoding;
	}

	@Override
	public String[] getExcludeResources() {
		return this.excludeResources;
	}

	@Override
	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	@Override
	public <T> T getFrameworkComponent(Class<? super T> componentClass) {
		return (T) this.containerAdapter.getComponent(componentClass);
	}

	@Override
	public GlobalExceptionHandler getGlobalExceptionHandler() {
		return this.globalExceptionHandler;
	}

	@Override
	public AnnotationResolverCreator getResolverCreator() {
		return this.resolverCreator;
	}

	@Override
	public String[] getRootPackages() {
		return this.rootPackages;
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public String getUserConfigPath() {
		return this.userConfigPath;
	}

	@Override
	public WebApplication getWebApplication() {
		return this.webApplication;
	}

	@Override
	public <T> boolean hasFrameworkComponent(Class<? super T> componentClass) {
		return false;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setupRequestAndResponse(HttpServletRequest req,
			HttpServletResponse res) throws UnsupportedEncodingException {
	}

	public FilterConfig getConfig() {
		return config;
	}

	public void setEagerload(boolean eagerload) {
		this.eagerload = eagerload;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setUserConfigPath(String userConfigPath) {
		this.userConfigPath = userConfigPath;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setRootPackages(String[] rootPackages) {
		this.rootPackages = rootPackages;
	}

	public void setExcludeResources(String[] excludeResources) {
		this.excludeResources = excludeResources;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public void endRequest() {
		containerAdapter.endRequest();
	}
}
