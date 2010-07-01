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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * Request is represented class as user request and hides and wraps
 * HttpServletRequest.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see javax.servlet.http.HttpServletRequest
 */
public interface Request extends AttributesAssembler, ParameterAssembler,
		NativeResource<HttpServletRequest> {

	/**
	 * <#if locale="en">
	 * <p>
	 * The key of forward key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String JAVAX_FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

	/**
	 * <#if locale="en">
	 * <p>
	 * Ajax request marker key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String AJAX_REQUEST_MARKER_KEY = "X-Requested-With";

	/**
	 * <#if locale="en">
	 * <p>
	 * XMLHttpRequest
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String AJAX_REQUEST_MARKER_VALUE = "xmlhttprequest";

	/**
	 * <#if locale="en">
	 * <p>
	 * Multipart attribute key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String MULTIPART_ATTRIBUTE_KEY = Request.class.getName() + ".Multipart";

	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP method override key for those who can not use HTTP PUT/DELET because
	 * of firewall policy.
	 * </p>
	 * <#else>
	 * <p>
	 * HTTPメソッドのPUTやDELETEを使う事が出来ない状況下で擬似的にHTTPメソッドを置き換えるためのキーです.
	 * </p>
	 * </#if>
	 */
	String HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get path.This method wraps {@link HttpServletRequest#getPathInfo()} .
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return path information
	 */
	String getPathInfo();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get context path.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return context path for this request
	 */
	String getContextPath();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link HttpMethod} for this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return http method enum
	 */
	HttpMethod getMethod();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get override HTTP method for this request.
	 * </p>
	 * <ul>
	 * <li>the request header "X-HTTP-Method-Override"</li>
	 * <li>the value of request parameter key, "_method"</li>
	 * </ul>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return override HTTP method
	 * @since 0.6.2
	 */
	HttpMethod getOverrideHttpMethod();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Locale} for this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return locale
	 */
	Locale getLocale();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set {@link Session}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param session
	 */
	void setSession(Session session);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Session}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return current session
	 */
	Session getSession();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Forward to the given path.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 */
	void forward(String path);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if this request is forwarded.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if this request is forwarded, otherwise false
	 */
	boolean isForwarded();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if this request comes from Flex/AIR with AMF(Action Message Format).
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if this request is AMF type request, otherwise false
	 */
	boolean isAmfRequest();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if this request is ajax type request which means the request is
	 * coming with a header "X-Requested-With" and the header value contain
	 * "xmlhttprequest".Usually, polite javascript frameworks like prototype.js,
	 * jQuery, and ExtJS, usually send the ajax request with these header and
	 * value, so we count on these good manner.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if this request is ajax request, otherwise false
	 */
	boolean isAjaxRequest();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get content length from this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return content length
	 */
	int getContentLength();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link InputStream} from this request.If an input or output errors,
	 * {@link IORuntimeException} occurred.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return input stream
	 * @throws IORuntimeException
	 */
	InputStream getInputStream() throws IORuntimeException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if this request contains multi part data.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if this request is multi-part
	 */
	boolean isMultipartType();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Multipart} as multi part data from this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return multi-part data
	 */
	Multipart getMultipart();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get request headers as map.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return request headers as map
	 */
	Map<String, Object> getHeaders();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get request URI from this request.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return requested uri
	 */
	String getRequestURI();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get character encoding.
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

}
