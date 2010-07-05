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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.t2framework.confeito.util.ArrayUtil;
import org.t2framework.confeito.util.IteratorEnumeration;

/**
 * <#if locale="en">
 * <p>
 * An implementation class of {@link MockHttpServletRequest}.
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
public class MockHttpServletRequestImpl implements MockHttpServletRequest {

	protected ServletContext servletContext;

	protected String servletPath;

	protected String authType;

	protected List<Cookie> cookieList = new ArrayList<Cookie>();

	protected Map<String, List<String>> headers = new HashMap<String, List<String>>();

	protected String method = "POST";

	protected String pathInfo;

	protected String pathTranslated;

	protected String queryString;

	protected MockHttpSession session;

	protected String scheme = "http";

	protected int serverPort = 80;

	protected String protocol = "HTTP/1.1";

	protected String serverName = "localhost";

	protected Map<String, Object> attributes = new HashMap<String, Object>();

	protected String characterEncoding = "ISO-8859-1";

	protected int contentLength;

	protected String contentType;

	protected Map<String, String[]> parameters = new HashMap<String, String[]>();

	protected String remoteAddr;

	protected String remoteHost;

	protected int remotePort;

	protected String localAddr;

	protected String localName;

	protected int localPort;

	protected List<Locale> locales = new ArrayList<Locale>();

	protected boolean secure = false;

	public MockHttpServletRequestImpl(final ServletContext servletContext,
			final String servletPath) {
		this(servletContext, servletPath, null);
	}

	public MockHttpServletRequestImpl(final ServletContext servletContext,
			final String servletPath, final MockHttpSession session) {
		this.servletContext = servletContext;
		this.servletPath = (servletPath.startsWith("/")) ? servletPath : "/"
				+ servletPath;
		this.session = session;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Cookie[] getCookies() {
		return cookieList.toArray(new Cookie[cookieList.size()]);
	}

	public void addCookie(Cookie cookie) {
		cookieList.add(cookie);
	}

	public long getDateHeader(String name) {
		String value = getHeader(name);
		return MockHeaderUtil.getDateValue(value);
	}

	public String getHeader(String name) {
		List<String> values = getHeaderList(name);
		if (values != null) {
			return (String) values.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getHeaders(String name) {
		List<String> values = getHeaderList(name);
		if (values != null) {
			return new IteratorEnumeration<String>(values.iterator());
		}
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return false;
			}

			@Override
			public String nextElement() {
				return null;
			}
			
		};
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getHeaderNames() {
		return new IteratorEnumeration<String>(headers.keySet().iterator());
	}

	public int getIntHeader(String name) {
		String value = getHeader(name);
		return MockHeaderUtil.getIntValue(value);
	}

	public void addHeader(String name, String value) {
		List<String> values = getHeaderList(name);
		if (values == null) {
			values = new ArrayList<String>();
		}
		values.add(value);
		headers.put(name.toLowerCase(), values);
	}

	public void addDateHeader(String name, long value) {
		addHeader(name, MockHeaderUtil.getDateValue(value));
	}

	public void addIntHeader(String name, int value) {
		addHeader(name, String.valueOf(value));
	}

	private List<String> getHeaderList(String name) {
		name = name.toLowerCase();
		return headers.get(name);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	public String getPathTranslated() {
		return pathTranslated;
	}

	public void setPathTranslated(String pathTranslated) {
		this.pathTranslated = pathTranslated;
	}

	public String getContextPath() {
		return servletContext.getServletContextName();
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getRemoteUser() {
		return System.getProperty("user.name");
	}

	public boolean isUserInRole(String arg0) {
		throw new UnsupportedOperationException();
	}

	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException();
	}

	public String getRequestedSessionId() {
		String sessionId = getRequestedSessionIdFromCookie();
		if (sessionId != null) {
			return sessionId;
		}
		return getRequestedSessionIdFromURL();
	}

	protected String getRequestedSessionIdFromCookie() {
		Cookie[] cookies = getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().endsWith("sessionid")) {
				return cookie.getValue();
			}
		}
		return null;
	}

	protected String getRequestedSessionIdFromURL() {
		String uri = getRequestURI();
		int index = uri.lastIndexOf("sessionid");
		if (index < 0) {
			return null;
		}
		return uri.substring(index + "sessionid".length());
	}

	public String getRequestURI() {
		String contextPath = getContextPath();
		if (contextPath.equals("/")) {
			return servletPath;
		}
		return contextPath + servletPath;
	}

	public StringBuffer getRequestURL() {
		StringBuffer url = new StringBuffer();
		url.append(scheme);
		url.append("://");
		url.append(serverName);
		if ((scheme.equals("http") && (serverPort != 80))
				|| (scheme.equals("https") && (serverPort != 443))) {

			url.append(':');
			url.append(serverPort);
		}
		url.append(getRequestURI());
		return url;
	}

	public String getServletPath() {
		return servletPath;
	}

	public HttpSession getSession(boolean create) {
		if (session != null) {
			return session;
		}
		if (create) {
			session = new MockHttpSessionImpl(servletContext, this);
		}
		if (session != null) {
			session.access();
		}
		return session;
	}

	public HttpSession getSession() {
		return getSession(true);
	}

	public boolean isRequestedSessionIdValid() {
		if (session != null) {
			return session.isValid();
		}
		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return getRequestedSessionIdFromCookie() != null;
	}

	public boolean isRequestedSessionIdFromURL() {
		return getRequestedSessionIdFromURL() != null;
	}

	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return isRequestedSessionIdFromURL();
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return new IteratorEnumeration<String>(attributes.keySet().iterator());
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding)
			throws UnsupportedEncodingException {

		this.characterEncoding = characterEncoding;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getParameter(String name) {
		String[] values = parameters.get(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames() {
		return new IteratorEnumeration<String>(parameters.keySet().iterator());
	}

	public String[] getParameterValues(String name) {
		return parameters.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		return parameters;
	}

	public void addParameter(String name, String value) {
		String[] values = getParameterValues(name);
		if (values == null) {
			setParameter(name, value);
		} else {
			String[] newArray = new String[values.length + 1];
			System.arraycopy(values, 0, newArray, 0, values.length);
			newArray[newArray.length - 1] = value;
			parameters.put(name, newArray);
		}
	}

	public void addParameter(String name, String[] values) {
		if (values == null) {
			setParameter(name, (String) null);
			return;
		}
		String[] vals = getParameterValues(name);
		if (vals == null) {
			setParameter(name, values);
		} else {
			String[] newArray = new String[vals.length + values.length];
			System.arraycopy(vals, 0, newArray, 0, vals.length);
			System.arraycopy(values, 0, newArray, vals.length, values.length);
			parameters.put(name, newArray);
		}
	}

	public void setParameter(String name, String value) {
		if (parameters.containsKey(name)) {
			String[] strings = parameters.get(name);
			ArrayUtil.add(strings, value);
		} else {
			parameters.put(name, new String[] { value });
		}
	}

	public void setParameter(String name, String[] values) {
		parameters.put(name, values);
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getLocalAddr() {
		return localAddr;
	}

	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public Locale getLocale() {
		if (locales.isEmpty()) {
			return Locale.getDefault();
		}
		return locales.get(0);
	}

	public void setLocale(Locale locale) {
		locales.clear();
		locales.add(locale);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getLocales() {
		return new IteratorEnumeration<Locale>(locales.iterator());
	}

	public boolean isSecure() {
		return secure;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return new MockRequestDispatcherImpl(path);
	}

	@Deprecated
	public String getRealPath(String path) {
		return path;
	}

	public void clearSession() {
		session = null;
	}

	@Override
	public void setSecure(boolean secure) {
		this.secure = secure;
	}

}
