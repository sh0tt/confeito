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
package org.t2framework.confeito.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.exception.ForwardDispatchRuntimeException;
import org.t2framework.confeito.exception.RedirectDispatchRuntimeException;

/**
 * <#if locale="en">
 * <p>
 * Servlet utility class.
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
public class ServletUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Forward by given {@link RequestDispatcher}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param dispatcher
	 * @param request
	 * @param response
	 */
	public static void forward(final RequestDispatcher dispatcher,
			final HttpServletRequest request, final HttpServletResponse response) {
		Assertion.notNulls(dispatcher, request, response);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			throw new ForwardDispatchRuntimeException(e);
		} catch (IOException e) {
			throw new ForwardDispatchRuntimeException(e);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Parse request query strings as {@link Map}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param param
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String[]> parseParameters(String param,
			String encoding) throws UnsupportedEncodingException {
		Map<String, String[]> map = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(param, "&");
		while (st.hasMoreTokens()) {
			String tkn = st.nextToken();
			int equal = tkn.indexOf("=");
			String name;
			String value;
			if (equal >= 0) {
				name = tkn.substring(0, equal);
				value = tkn.substring(equal + 1);
			} else {
				name = tkn;
				value = "";
			}
			name = URLDecoder.decode(name, encoding);
			value = URLDecoder.decode(value, encoding);
			String[] current = map.get(name);
			if (current == null) {
				map.put(name, new String[] { value });
			} else {
				map.put(name, (String[]) ArrayUtil.add(current, value));
			}
		}
		return map;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Send a redirect back to the HTTP client.This method always call
	 * {@link HttpServletResponse#sendRedirect(String)}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param response
	 * @param path
	 */
	public static void redirect(final HttpServletResponse response,
			final String path) {
		redirect(response, path, true);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Send a redirect back to the HTTP client in a correct way which means if
	 * there supposed to be HTTP 1.0 compatible, use
	 * {@link HttpServletResponse#sendRedirect(String)}.If not compatible to be
	 * HTTP 1.0, use {@link HttpServletResponse#SC_SEE_OTHER}, 303, to redirect.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param response
	 * @param path
	 * @param http10Compatible
	 */
	public static void redirect(final HttpServletResponse response,
			final String path, final boolean http10Compatible) {
		Assertion.notNulls(response, path);
		final String encodeUrl = response.encodeRedirectURL(path);
		if (http10Compatible) {
			try {
				response.sendRedirect(encodeUrl);
			} catch (IOException e) {
				throw new RedirectDispatchRuntimeException(e, path);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_SEE_OTHER);
			response.setHeader("Location", encodeUrl);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * True if given request is multi-part request.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMultipart(final HttpServletRequest request) {
		Assertion.notNull(request);
		String method = request.getMethod().toUpperCase();
		if (!"POST".equals(method)) {
			return false;
		}
		final String contentType = request.getContentType();
		if (StringUtil.isEmpty(contentType)) {
			return false;
		}
		return contentType.indexOf(Constants.MULTIPART) >= 0;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param filterConfig
	 * @param parameterName
	 * @param defaultValue
	 * @return
	 */
	public static int getSizeParameter(final FilterConfig filterConfig,
			final String parameterName, final int defaultValue) {
		Assertion.notNulls(filterConfig, parameterName);
		String param = filterConfig.getInitParameter(parameterName);
		if (StringUtil.isEmpty(param)) {
			return defaultValue;
		}
		param = param.toLowerCase();
		int factor = 1;
		String number = param;
		if (StringUtil.endsWithIgnoreCase(param, "g")) {
			factor = Constants.GB;
			number = param.substring(0, param.length() - 1);
		} else if (StringUtil.endsWithIgnoreCase(param, "m")) {
			factor = Constants.MB;
			number = param.substring(0, param.length() - 1);
		} else if (StringUtil.endsWithIgnoreCase(param, "k")) {
			factor = Constants.KB;
			number = param.substring(0, param.length() - 1);
		}
		return Integer.parseInt(number) * factor;
	}

}
