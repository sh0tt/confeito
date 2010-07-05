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

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.t2framework.confeito.util.IteratorEnumeration;
import org.t2framework.confeito.util.ResourceUtil;

/**
 * <#if locale="en">
 * <p>
 * An implementation class of {@link MockServletContext}.
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
public class MockServletContextImpl implements MockServletContext, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MAJOR_VERSION = 2;

	public static final int MINOR_VERSION = 5;

	public static final String SERVER_INFO = "mock";

	private String servletContextName;

	private final Map<String, String> mimeTypes = new HashMap<String, String>();

	private final Map<String, String> initParameters = new HashMap<String, String>();

	private final Map<String, Object> attributes = new HashMap<String, Object>();

	public MockServletContextImpl(String path) {
		if (path == null || path.charAt(0) != '/') {
			path = "/";
		}
		this.servletContextName = path;
	}

	public ServletContext getContext(String path) {
		throw new UnsupportedOperationException();
	}

	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	public String getMimeType(String file) {
		return (String) mimeTypes.get(file);
	}

	public void addMimeType(String file, String type) {
		mimeTypes.put(file, type);
	}

	@SuppressWarnings("rawtypes")
	public Set getResourcePaths(String path) {
		path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
		File src = ResourceUtil.getResourceAsFile(".");
		File root = src.getParentFile();
		if (root.getName().equalsIgnoreCase("WEB-INF")) {
			root = root.getParentFile();
		}
		File file = new File(root, adjustPath(path));
		if (!file.exists()) {
			int pos = path.lastIndexOf('/');
			if (pos != -1) {
				path = path.substring(pos + 1);
			}
			do {
				file = new File(root, path);
				root = root.getParentFile();
			} while (!file.exists() && root != null);
			path = "/" + path;
		}
		if (file.isDirectory()) {
			int len = file.getAbsolutePath().length();
			Set<String> paths = new HashSet<String>();
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; ++i) {
					String replace = files[i].getAbsolutePath().substring(len)
							.replace('\\', '/');
					paths.add(path + replace);
				}
				return paths;
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public URL getResource(String path) throws MalformedURLException {
		if (path == null) {
			return null;
		}
		path = adjustPath(path);
		File src = ResourceUtil.getResourceAsFile(".");
		File root = src.getParentFile();
		if (root.getName().equalsIgnoreCase("WEB-INF")) {
			root = root.getParentFile();
		}
		while (root != null) {
			File file = new File(root, path);
			if (file.exists()) {
				return file.toURL();
			}
			root = root.getParentFile();
		}
		if (ResourceUtil.isExist(path)) {
			return ResourceUtil.getResource(path);
		}
		if (path.startsWith("WEB-INF")) {
			path = path.substring("WEB-INF".length());
			return getResource(path);
		}
		return null;
	}

	public InputStream getResourceAsStream(String path) {
		if (path == null) {
			return null;
		}
		path = adjustPath(path);
		if (ResourceUtil.isExist(path)) {
			return ResourceUtil.getResourceAsStream(path);
		}
		if (path.startsWith("WEB-INF")) {
			path = path.substring("WEB-INF".length());
			return getResourceAsStream(path);
		}
		return null;
	}

	protected String adjustPath(String path) {
		if (path != null && path.length() > 0 && path.charAt(0) == '/') {
			return path.substring(1);
		}
		return path;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return new MockRequestDispatcherImpl(path);
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public Servlet getServlet(String name) throws ServletException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public Enumeration getServlets() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public Enumeration getServletNames() {
		throw new UnsupportedOperationException();
	}

	public void log(String message) {
		System.out.println(message);
	}

	@Deprecated
	public void log(Exception ex, String message) {
		System.out.println(message);
		ex.printStackTrace();
	}

	public void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}

	public String getRealPath(String path) {
		try {
			return ResourceUtil.getResource(adjustPath(path)).getFile();
		} catch (final RuntimeException e) {
			return null;
		}
	}

	public String getServerInfo() {
		return SERVER_INFO;
	}

	public String getInitParameter(String name) {
		return initParameters.get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getInitParameterNames() {
		return new IteratorEnumeration<String>(initParameters.keySet()
				.iterator());
	}

	public void setInitParameter(String name, String value) {
		initParameters.put(name, value);
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

	public String getServletContextName() {
		return servletContextName;
	}

	public void setServletContextName(final String servletContextName) {
		this.servletContextName = servletContextName;
	}

	public MockHttpServletRequest createRequest(String path) {
		String queryString = null;
		int question = path.indexOf('?');
		if (question >= 0) {
			queryString = path.substring(question + 1);
			path = path.substring(0, question);
		}
		MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
				this, path);
		request.setQueryString(queryString);
		return request;
	}

	public Map<String, String> getInitParameterMap() {
		return initParameters;
	}

	@Override
	public String getContextPath() {
		return servletContextName;
	}

}
