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

/**
 * 
 * <#if locale="en">
 * <p>
 * Null object pattern for {@link WebConfiguration}.
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
public class NullWebConfiguration implements WebConfiguration {

	@Override
	public ContainerAdapter<?> getContainerAdapter() {
		return null;
	}

	@Override
	public boolean getEagerLoad() {
		return false;
	}

	@Override
	public String getEncoding() {
		return "UTF-8";
	}

	@Override
	public String[] getExcludeResources() {
		return null;
	}

	@Override
	public FilterConfig getFilterConfig() {
		return null;
	}

	@Override
	public <T> T getFrameworkComponent(Class<? super T> componentClazz) {
		return null;
	}

	@Override
	public GlobalExceptionHandler getGlobalExceptionHandler() {
		return null;
	}

	@Override
	public AnnotationResolverCreator getResolverCreator() {
		return null;
	}

	@Override
	public String[] getRootPackages() {
		return new String[] { "" };
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public String getUserConfigPath() {
		return null;
	}

	@Override
	public WebApplication getWebApplication() {
		return null;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void setupRequestAndResponse(HttpServletRequest req,
			HttpServletResponse res) throws UnsupportedEncodingException {

	}

	@Override
	public <T> boolean hasFrameworkComponent(Class<? super T> componentClass) {
		return false;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void endRequest() {
	}
}
