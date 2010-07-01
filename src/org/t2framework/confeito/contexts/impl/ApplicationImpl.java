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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * An {@link Application} implementation for ServletContext.
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
public class ApplicationImpl implements Application {

	/**
	 * Underlying {@link ServletContext}
	 */
	protected ServletContext servletContext;

	/**
	 * Filter init values as {@link Map}
	 */
	protected Map<String, String> filterInitParamMap;

	/**
	 * Underlying {@link FilterConfig}
	 */
	protected FilterConfig filterConfig;

	public ApplicationImpl(final ServletContext servletContext,
			final FilterConfig config) {
		Assertion.notNulls(servletContext, config);
		this.servletContext = servletContext;
		this.filterConfig = config;
	}

	@SuppressWarnings("unchecked")
	protected void initFilterInitParamMap(FilterConfig config) {
		Map<String, String> map = new HashMap<String, String>();
		for (Enumeration<String> e = config.getInitParameterNames(); e
				.hasMoreElements();) {
			String name = e.nextElement();
			String value = config.getInitParameter(name);
			map.put(name, value);
		}
		this.filterInitParamMap = map;
	}

	@Override
	public String getInitParameter(String key) {
		return servletContext.getInitParameter(key);
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return servletContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return servletContext.getResourceAsStream(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getResourcePaths(String path) {
		return servletContext.getResourcePaths(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAttributeNames() {
		List<String> ret = new ArrayList<String>();
		for (Enumeration<String> e = getNativeResource().getAttributeNames(); e
				.hasMoreElements();) {
			ret.add(e.nextElement());
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAttributesAsMap() {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (Enumeration<String> e = getNativeResource().getAttributeNames(); e
				.hasMoreElements();) {
			String key = (String) e.nextElement();
			Object value = servletContext.getAttribute(key);
			ret.put(key, value);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getAttribute(String key) {
		return (V) getNativeResource().getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <V> V removeAttribute(String key) {
		Object ret = getAttribute(key);
		getNativeResource().removeAttribute(key);
		return (V) ret;
	}

	@Override
	public synchronized <V> void setAttribute(String key, V value) {
		getNativeResource().setAttribute(key, value);
	}

	@Override
	public ServletContext getNativeResource() {
		return servletContext;
	}

	@Override
	public Map<String, String> getFilterInitParamMap() {
		if (this.filterInitParamMap == null) {
			initFilterInitParamMap(this.filterConfig);
		}
		return filterInitParamMap;
	}

	@Override
	public String getFilterInitParamValue(String name) {
		if (this.filterInitParamMap == null) {
			initFilterInitParamMap(this.filterConfig);
		}
		return filterInitParamMap.get(Assertion.notNull(name));
	}

	@Override
	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

}
