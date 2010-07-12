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

import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <#if locale="en">
 * <p>
 * Mock class of {@link HttpServletResponse}.
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
public interface MockHttpServletResponse extends HttpServletResponse {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Cookie}s.
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
	Cookie[] getCookies();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get status.
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
	int getStatus();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get message.
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
	String getMessage();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get headers {@link Iterator} from given name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @return
	 */
	Iterator<String> getHeaders(String name);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get header string by name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @return
	 */
	String getHeader(String name);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get all header as {@link Iterator<String>}.
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
	Iterator<String> getHeaderNames();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get int header from name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @return
	 */
	int getIntHeader(String name);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get content length.
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
	int getContentLength();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get response as string.
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
	String getResponseString();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get response as byte[].
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
	byte[] getResponseBytes();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get redirect path.
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
	String getRedirectPath();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link OutputStream}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param os
	 */
	void setOutputStream(ServletOutputStream os);
}
