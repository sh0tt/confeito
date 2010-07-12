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
package org.t2framework.confeito.contexts.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.contexts.HttpMethod;
import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.exception.IORuntimeException;
import org.t2framework.confeito.internal.HttpMethodUtil;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.ServletUtil;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link Request}.
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
public class RequestImpl implements Request {

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected Session session;

	protected Object lock = new Object();

	public RequestImpl(final HttpServletRequest request,
			final HttpServletResponse response) {
		this.request = Assertion.notNull(request);
		this.response = Assertion.notNull(response);
	}

	public String getContextPath() {
		return getNativeResource().getContextPath();
	}

	public Locale getLocale() {
		return getNativeResource().getLocale();
	}

	public HttpMethod getMethod() {
		final String method = getNativeResource().getMethod();
		return HttpMethod.getMethodType(method);
	}

	public String getPathInfo() {
		return getNativeResource().getPathInfo();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAttributeNames() {
		List<String> ret = new ArrayList<String>();
		for (Enumeration<String> e = getNativeResource().getAttributeNames(); e
				.hasMoreElements();) {
			ret.add(e.nextElement());
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAttributesAsMap() {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (Enumeration<String> e = getNativeResource().getAttributeNames(); e
				.hasMoreElements();) {
			String key = (String) e.nextElement();
			Object value = getNativeResource().getAttribute(key);
			ret.put(key, value);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public <V> V getAttribute(String key) {
		return (V) getNativeResource().getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	public synchronized <V> V removeAttribute(String key) {
		Object ret = getAttribute(key);
		getNativeResource().removeAttribute(key);
		return (V) ret;
	}

	public synchronized <V> void setAttribute(String key, V value) {
		Assertion.notNull(key);
		getNativeResource().setAttribute(key, value);
	}

	@SuppressWarnings("unchecked")
	public List<String> getParameterNames() {
		List<String> list = new ArrayList<String>();
		Map<String, Object> parameterMap = getNativeResource()
				.getParameterMap();
		for (String s : parameterMap.keySet()) {
			list.add(s);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String[]> getParametersAsMap() {
		return request.getParameterMap();
	}

	public String[] getParameters(String key) {
		return getNativeResource().getParameterValues(key);
	}

	@Override
	public HttpServletRequest getNativeResource() {
		return request;
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public void setSession(Session session) {
		this.session = Assertion.notNull(session);
	}

	@Override
	public void forward(String path) {
		RequestDispatcher dispatcher = getNativeResource()
				.getRequestDispatcher(path);
		ServletUtil.forward(dispatcher, request, response);
	}

	@Override
	public boolean isForwarded() {
		return request.getAttribute(JAVAX_FORWARD_REQUEST_URI) != null;
	}

	@Override
	public String getParameter(String key) {
		return getNativeResource().getParameter(key);
	}

	@Override
	public boolean hasMultipleParameters(String key) {
		String[] values = getNativeResource().getParameterValues(key);
		return values != null && values.length > 1;
	}

	@Override
	public int getContentLength() {
		return getNativeResource().getContentLength();
	}

	@Override
	public InputStream getInputStream() throws IORuntimeException {
		try {
			return getNativeResource().getInputStream();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	@Override
	public boolean isMultipartType() {
		return ServletUtil.isMultipart(getNativeResource());
	}

	@Override
	public boolean isAjaxRequest() {
		HttpServletRequest req = getNativeResource();
		String value = req.getHeader(AJAX_REQUEST_MARKER_KEY);
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		return AJAX_REQUEST_MARKER_VALUE.equalsIgnoreCase(value.toLowerCase());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * This method will work from version 0.6, called "Star".
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isAmfRequest() {
		return isAmfType(getNativeResource());
	}

	private static boolean isAmfType(final HttpServletRequest req) {
		String contentType = req.getHeader("content-type");
		return (StringUtil.isEmpty(contentType) == false && contentType
				.equals(Constants.AMF_CONTENT_TYPE));
	}

	@Override
	public Multipart getMultipart() {
		return (Multipart) getNativeResource().getAttribute(
				Request.MULTIPART_ATTRIBUTE_KEY);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getHeaders() {
		Map<String, Object> headers = new HashMap<String, Object>();
		for (Enumeration<String> e = getNativeResource().getHeaderNames(); e
				.hasMoreElements();) {
			final String key = e.nextElement();
			List<String> list = new ArrayList<String>();
			for (Enumeration values = getNativeResource().getHeaders(key); values
					.hasMoreElements();) {
				final String value = (String) values.nextElement();
				list.add(value);
			}
			final int size = list.size();
			if (size == 0) {
				continue;
			}
			String lowerKey = key.toLowerCase();
			if (1 < size) {
				headers.put(lowerKey, list.toArray(new String[size]));
			} else {
				headers.put(lowerKey, list.get(0));
			}
		}
		return headers;
	}

	@Override
	public String getRequestURI() {
		return getNativeResource().getRequestURI();
	}

	@Override
	public String getCharacterEncoding() {
		return getNativeResource().getCharacterEncoding();
	}

	@Override
	public HttpMethod getOverrideHttpMethod() {
		final String overrideHeader = getNativeResource().getHeader(
				HTTP_METHOD_OVERRIDE);
		HttpMethod overrideMethod = HttpMethod.getMethodType(overrideHeader);
		if (overrideHeader == null) {
			overrideMethod = HttpMethodUtil
					.getHttpOverloadMethodExistByParameter(this);
		}
		return overrideMethod;
	}

}
