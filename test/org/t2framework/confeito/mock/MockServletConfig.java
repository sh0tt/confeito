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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * <#if locale="en">
 * <p>
 * Mock class of {@link ServletConfig}.
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
public interface MockServletConfig extends ServletConfig {

	/**
	 * <#if locale="en">
	 * <p>
	 * Set servlet name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletName
	 */
	void setServletName(final String servletName);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ServletContext}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 */
	void setServletContext(final ServletContext servletContext);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set initial parameter.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @param value
	 */
	void setInitParameter(final String name, final String value);
}
