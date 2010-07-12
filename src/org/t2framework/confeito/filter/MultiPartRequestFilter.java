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
package org.t2framework.confeito.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.ConfigurationKey;
import org.t2framework.confeito.apache.commons.fileupload.servlet.ServletFileUpload;
import org.t2framework.confeito.internal.ConfigurationUtil;
import org.t2framework.confeito.spi.MultipartRequestHandler;
import org.t2framework.confeito.util.ServletUtil;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * The filter for handling multipart request. MultiPartRequestFilter should run
 * before T2Filter to set up MultiPart instance.This class heavilly depends on
 * Apache commons fileupload.Please see T2 pom.xml which is included.
 * 
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
public class MultiPartRequestFilter implements Filter {

	/**
	 * <#if locale="en">
	 * <p>
	 * Default max upload size(100MB).
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static final int DEFAULT_MAX_SIZE = 100 * Constants.MB;

	/**
	 * <#if locale="en">
	 * <p>
	 * Default max file upload size(100MB).
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static final int DEFAULT_MAX_FILE_SIZE = 100 * Constants.MB;

	/**
	 * <#if locale="en">
	 * <p>
	 * Default threshold size(100KB).
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static final int DEFAULT_THREASHOLD_SIZE = 100 * Constants.KB;

	public static int maxSize;

	public static int maxFileSize;

	public static int thresholdSize;

	public static String repositoryPath = null;

	public static String encoding;

	protected MultipartRequestHandler handler;

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize {@link MultiPartRequestFilter} to use. Get parameters such as
	 * max size, max file size, threshold size, and create
	 * {@link MultipartRequestHandler}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		maxSize = ServletUtil.getSizeParameter(filterConfig,
				ConfigurationKey.UPLOAD_MAX_SIZE, DEFAULT_MAX_SIZE);
		maxFileSize = ServletUtil.getSizeParameter(filterConfig,
				ConfigurationKey.UPLOAD_MAX_FILE_SIZE, DEFAULT_MAX_FILE_SIZE);
		thresholdSize = ServletUtil
				.getSizeParameter(filterConfig,
						ConfigurationKey.UPLOAD_THRESHOLD_SIZE,
						DEFAULT_THREASHOLD_SIZE);
		repositoryPath = filterConfig
				.getInitParameter(ConfigurationKey.UPLOAD_REPOSITORY_PATH);
		encoding = ConfigurationUtil.getEncoding(filterConfig);
		this.handler = ConfigurationUtil
				.getMultipartRequestHandler(filterConfig);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@link MultiPartRequestWrapper} if the request is multipart kind.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				setupEncoding(request);
				request = createHttpServletRequestWrapper(request, handler);
			}
			chain.doFilter(request, response);
		} finally {
			this.handler.close(request);
		}
	}

	protected HttpServletRequest createHttpServletRequestWrapper(
			HttpServletRequest request, MultipartRequestHandler handler) {
		return new MultiPartRequestWrapper(request, handler);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Destroy this filter. {@link MultipartRequestHandler} will be released.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void destroy() {
		this.handler = null;
	}

	protected void setupEncoding(HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding);
		}
	}

	protected int getSizeParameter(final FilterConfig filterConfig,
			final String parameterName, final int defaultValue) {
		String param = filterConfig.getInitParameter(parameterName);
		if (StringUtil.isEmpty(param)) {
			return defaultValue;
		}
		param = param.toLowerCase();
		int factor = 1;
		String number = param;
		if (StringUtil.endsWithIgnoreCase(param, "g")) {
			factor = 1024 * 1024 * 1024;
			number = param.substring(0, param.length() - 1);
		} else if (StringUtil.endsWithIgnoreCase(param, "m")) {
			factor = 1024 * 1024;
			number = param.substring(0, param.length() - 1);
		} else if (StringUtil.endsWithIgnoreCase(param, "k")) {
			factor = 1024;
			number = param.substring(0, param.length() - 1);
		}
		return Integer.parseInt(number) * factor;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * 
	 * {@link HttpServletRequestWrapper} for multipart data.
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
	public static class MultiPartRequestWrapper extends
			HttpServletRequestWrapper {

		protected Map<String, String[]> paramMap;

		/**
		 * <#if locale="en">
		 * <p>
		 * Construct this wrapper.{@code
		 * MultipartRequestHandler#handle(HttpServletRequest)} will be executed
		 * to create request parameter map at this time.
		 * 
		 * </p>
		 * <#else>
		 * <p>
		 * 
		 * </p>
		 * </#if>
		 * 
		 * @param request
		 * @param handler
		 */
		public MultiPartRequestWrapper(final HttpServletRequest request,
				MultipartRequestHandler handler) {
			super(request);
			this.paramMap = handler.handle(request);
		}

		/**
		 * <#if locale="en">
		 * <p>
		 * Get parameter from key.
		 * </p>
		 * <#else>
		 * <p>
		 * 
		 * </p>
		 * </#if>
		 */
		public String getParameter(String name) {
			String value = (String) getFirst(paramMap.get(name));
			if (value == null) {
				value = super.getParameter(name);
			}
			return value;
		}

		/**
		 * <#if locale="en">
		 * <p>
		 * Get multipart handled parameter map.
		 * </p>
		 * <#else>
		 * <p>
		 * 
		 * </p>
		 * </#if>
		 */
		@SuppressWarnings("unchecked")
		public Map getParameterMap() {
			return paramMap;
		}

		/**
		 * <#if locale="en">
		 * <p>
		 * Get parameter names.
		 * </p>
		 * <#else>
		 * <p>
		 * 
		 * </p>
		 * </#if>
		 */
		@SuppressWarnings("unchecked")
		public Enumeration getParameterNames() {
			return Collections.enumeration(paramMap.keySet());
		}

		/**
		 * <#if locale="en">
		 * <p>
		 * Get parameters from key.
		 * </p>
		 * <#else>
		 * <p>
		 * 
		 * </p>
		 * </#if>
		 */
		public String[] getParameterValues(String name) {
			return (String[]) paramMap.get(name);
		}

		protected Object getFirst(Object obj) {
			Object[] objs = (Object[]) obj;
			if (objs == null || objs.length == 0) {
				return null;
			} else {
				return objs[0];
			}
		}

	}

}
