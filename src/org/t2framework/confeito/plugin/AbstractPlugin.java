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
import org.t2framework.confeito.action.ActionInvoker;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * {@link AbstractPlugin} is the base class for creating custom plugin. This
 * base class does nothing.
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
public abstract class AbstractPlugin implements Plugin {

	protected ContainerAdapter<?> containerAdapter;

	@Override
	public void initialize(ServletContext servletContext,
			WebApplication webApplication) {
	}

	@Override
	public void beginRequestProcessing(HttpServletRequest request,
			HttpServletResponse response) {
	}

	@Override
	public HttpServletRequest createRequest(HttpServletRequest request,
			HttpServletResponse response) {
		return request;
	}

	@Override
	public HttpServletResponse createResponse(HttpServletRequest request,
			HttpServletResponse response) {
		return response;
	}

	@Override
	public Navigation componentCreated(WebContext context, Object page) {
		return getPluginDefaultNavigation();
	}

	@Override
	public Navigation beforeActionInvoke(ActionContext actionContext,
			Method targetMethod, Object page, Object[] args) {
		return getPluginDefaultNavigation();
	}

	/**
	 * @since 0.6.2
	 */
	@Override
	public Navigation afterActionInvoke(ActionContext actionContext,
			Method targetMethod, Object page, Object[] args, Navigation result) {
		return getPluginDefaultNavigation();
	}

	@Override
	public void beforeNavigation(WebContext context) {
	}

	@Override
	public void afterNavigation(WebContext context) {
	}

	@Override
	public void endRequestProcessing(HttpServletRequest request,
			HttpServletResponse response) {
	}

	@Override
	public void destroy(ServletContext servletContext,
			WebApplication webApplication) {
	}

	@Override
	public void setContainerAdapter(ContainerAdapter<?> containerAdapter) {
		this.containerAdapter = Assertion.notNull(containerAdapter);
	}

	protected ContainerAdapter<?> getContainerAdapter() {
		return containerAdapter;
	}

	protected Navigation getPluginDefaultNavigation() {
		return PluginDefaultNavigation.INSTANCE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((containerAdapter == null) ? 0 : containerAdapter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPlugin other = (AbstractPlugin) obj;
		if (containerAdapter == null) {
			if (other.containerAdapter != null)
				return false;
		} else if (!containerAdapter.equals(other.containerAdapter))
			return false;
		return true;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link PluginDefaultNavigation} is a {@link Navigation} implementation
	 * class for {@link Plugin}.This class is intended to use just for telling
	 * that the plugin return default navigation.It means {@link ActionInvoker}
	 * will process to the next action.
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
	public static class PluginDefaultNavigation implements Navigation {

		public static final PluginDefaultNavigation INSTANCE = new PluginDefaultNavigation();

		@Override
		public void execute(WebContext context) throws Exception {
			// no op;
		}

	}
}
