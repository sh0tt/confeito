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

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <#if locale="en">
 * <p>
 * The mock class of {@link HttpServletRequest}.
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
public interface MockHttpServletRequest extends HttpServletRequest {

	/**
	 * <#if locale="en">
	 * <p>
	 * Add single parameter.
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
	void addParameter(String name, String value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Add parameters.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @param values
	 */
	void addParameter(String name, String[] values);

	/**
	 * <#if locale="en">
	 * <p>
	 * Add {@link Cookie}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param cookie
	 */
	void addCookie(Cookie cookie);

	/**
	 * <#if locale="en">
	 * <p>
	 * Add http header value.
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
	void addHeader(String name, String value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set authentication type.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param authType
	 */
	void setAuthType(String authType);

	/**
	 * <#if locale="en">
	 * <p>
	 * Add date header.
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
	void addDateHeader(String name, long value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Add int header.
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
	void addIntHeader(String name, int value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set path info.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param pathInfo
	 */
	void setPathInfo(String pathInfo);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set translated path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param pathTranslated
	 */
	void setPathTranslated(String pathTranslated);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set query string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param queryString
	 */
	void setQueryString(String queryString);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set content length.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param contentLength
	 */
	void setContentLength(int contentLength);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set content type.
	 * 
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
	 * <#if locale="en">
	 * <p>
	 * Set single parameter.
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
	void setParameter(String name, String value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set parameters.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @param values
	 */
	void setParameter(String name, String[] values);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set protocol.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param protocol
	 */
	void setProtocol(String protocol);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set scheme.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param scheme
	 */
	void setScheme(String scheme);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set server name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param serverName
	 */
	void setServerName(String serverName);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set server port.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param serverPort
	 */
	void setServerPort(int serverPort);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set remote address.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param remoteAddr
	 */
	void setRemoteAddr(String remoteAddr);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set remote host.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param remoteHost
	 */
	void setRemoteHost(String remoteHost);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link Locale}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param locale
	 */
	void setLocale(Locale locale);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set http method.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param method
	 */
	void setMethod(String method);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set local address.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param localAddr
	 */
	void setLocalAddr(String localAddr);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set local name.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param localName
	 */
	void setLocalName(String localName);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set local port.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param localPort
	 */
	void setLocalPort(int localPort);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set remote port.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param remotePort
	 */
	void setRemotePort(int remotePort);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set to be secure.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param secure
	 */
	void setSecure(boolean secure);

	/**
	 * <#if locale="en">
	 * <p>
	 * Clear session.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	void clearSession();
}
