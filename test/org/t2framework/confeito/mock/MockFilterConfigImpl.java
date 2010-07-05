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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.IteratorEnumeration;

/**
 * <#if locale="en">
 * <p>
 * The implementation class of {@link MockFilterConfig}.
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
public class MockFilterConfigImpl implements MockFilterConfig {

	protected Map<String, String> initParamMap = new HashMap<String, String>();

	protected String filterName;

	protected MockServletContext context;

	public MockFilterConfigImpl() {
		this.context = new MockServletContextImpl(null);
	}

	public MockFilterConfigImpl(final MockServletContext context) {
		this.context = context;
	}

	public MockFilterConfigImpl(String path) {
		this.context = new MockServletContextImpl(path);
	}

	@Override
	public String getFilterName() {
		return filterName;
	}

	@Override
	public String getInitParameter(String key) {
		return initParamMap.get(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getInitParameterNames() {
		return new IteratorEnumeration<String>(initParamMap.keySet().iterator());
	}

	@Override
	public ServletContext getServletContext() {
		return context;
	}

	@Override
	public void addInitParameter(String key, String value) {
		Assertion.notNulls(key, value);
		initParamMap.put(key, value);
	}

	@Override
	public void setFilterName(String filterName) {
		this.filterName = Assertion.notNull(filterName);
	}

}
