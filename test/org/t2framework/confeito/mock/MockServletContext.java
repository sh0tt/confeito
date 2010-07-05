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

import java.util.Map;

import javax.servlet.ServletContext;

/**
 * <#if locale="en">
 * <p>
 * Mock class of {@link ServletContext}.
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
public interface MockServletContext extends ServletContext {

	/**
	 * <#if locale="en">
	 * <p>
	 * Add mime type.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 * @param type
	 */
	void addMimeType(String file, String type);

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
	void setInitParameter(String name, String value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@link MockHttpServletRequest} by given path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return
	 */
	MockHttpServletRequest createRequest(String path);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set servlet context name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param servletContextName
	 */
	void setServletContextName(String servletContextName);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get init parameter as {@link Map<String, String>}
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	Map<String, String> getInitParameterMap();

}
