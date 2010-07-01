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

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * {@link Response} is an interface as user response and hides and wraps
 * {@link HttpServletResponse}.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see javax.servlet.http.HttpServletResponse
 */
public interface Response extends NativeResource<HttpServletResponse> {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link OutputStream} from this response. If an output error occurred,
	 * throw {@link IORuntimeException}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return servlet output stream
	 * @throws IORuntimeException
	 */
	OutputStream getOutputStream() throws IORuntimeException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set content type.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param contentType
	 */
	void setContentType(String contentType);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set header key and value.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @param value
	 */
	void setHeader(String key, String value);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get content type.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return content type
	 */
	String getContentType();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get character encoding for this response.Character encoding will find by
	 * content type.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return character encoding
	 */
	String getCharacterEncoding();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Send a redirect response for this path.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 */
	void redirect(String path);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set no cache for this response.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 */
	void setNoCache();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Write string to this response.If an output errors, throw
	 * {@link IOException} occurred.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @throws IOException
	 */
	void write(String s) throws IOException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Write string to this response, and then flush response immediatelly.If an
	 * output errors, throw {@link IOException} occurred.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @throws IOException
	 */
	void writeAndFlush(String s) throws IOException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Flush this response.If an output errors, throw {@link IOException}
	 * occurred.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @throws IOException
	 */
	void flush() throws IOException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Send error status.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param status
	 */
	void sendError(int status);
}
