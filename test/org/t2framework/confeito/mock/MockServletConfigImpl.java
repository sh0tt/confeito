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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.t2framework.confeito.util.IteratorEnumeration;

/**
 * <#if locale="en">
 * <p>
 * An implementation class of {@link MockServletConfig}.
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
public class MockServletConfigImpl implements MockServletConfig, Serializable {

	private static final long serialVersionUID = 1L;

	protected String servletName;

	protected ServletContext servletContext;

	protected Map<String, String> initParameters = new HashMap<String, String>();

	public MockServletConfigImpl() {
	}

	public String getServletName() {
		return servletName;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getInitParameter(String name) {
		return initParameters.get(name);
	}

	public void setInitParameter(final String name, final String value) {
		initParameters.put(name, value);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getInitParameterNames() {
		return new IteratorEnumeration<String>(initParameters.keySet()
				.iterator());
	}
}
