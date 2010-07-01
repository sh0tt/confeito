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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * <#if locale="en">
 * <p>
 * {@link Application} is a container for initialized parameter, or long-span
 * attributes. Application holds {@link ServletContext} internally.
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
public interface Application extends AttributesAssembler,
		NativeResource<ServletContext> {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get init parameter from {@link ServletContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return parmeter value
	 */
	String getInitParameter(String key);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link URL} from {@link ServletContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return url as resource for this path
	 * @throws MalformedURLException
	 */
	URL getResource(String path) throws MalformedURLException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link InputStream} from {@link ServletContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return input stream as resource for this path
	 */
	InputStream getResourceAsStream(String path);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get resource paths from {@link ServletContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return set of resource paths for this path
	 */
	Set<String> getResourcePaths(String path);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get filter initialized parameter as {@link Map}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return map of filter initialization parameters
	 */
	Map<String, String> getFilterInitParamMap();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get filter initialized value.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return value of filter initialization by this key
	 */
	String getFilterInitParamValue(String key);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link FilterConfig}.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	FilterConfig getFilterConfig();
}
