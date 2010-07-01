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
package org.t2framework.confeito.navigation;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.t2framework.confeito.HttpVersion;
import org.t2framework.confeito.contexts.FrameworkComponent;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.contexts.impl.RequestImpl;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.util.ArrayUtil;
import org.t2framework.confeito.util.EnumerationIterable;
import org.t2framework.confeito.util.IteratorEnumeration;
import org.t2framework.confeito.util.UrlBuilder;

/**
 * <#if locale="en">
 * <p>
 * The pseudo redirect response.
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
public class Proceed extends WebNavigation<Proceed> {

	protected static Logger logger = Logger.getLogger(Proceed.class.getName());

	public static Proceed proceed(String path) {
		return new Proceed(path);
	}

	protected Map<String, Object> paramMap = new HashMap<String, Object>();

	protected Map<String, String> attributeMap = new HashMap<String, String>();

	public Proceed(String path) {
		super(path);
		this.paramMap = new HashMap<String, Object>();
	}

	public void setParameterMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public Proceed addAttribute(String key, String value) {
		this.attributeMap.put(key, value);
		return this;
	}

	public Proceed addParam(String key, String value) {
		if (this.paramMap.containsKey(key)) {
			Object old = this.paramMap.remove(key);
			if (old instanceof String) {
				this.paramMap.put(key, new String[] { (String) old, value });
			} else if (old instanceof String[]) {
				this.paramMap.put(key, ArrayUtil.add((String[]) old, value));
			}
		} else {
			this.paramMap.put(key, value);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(WebContext context) throws Exception {
		if (path.startsWith("/") == false) {
			// TODO
			logger.log(Level.INFO, "ITDT0020", new Object[] { "Forward" });
			path = PathUtil.appendStartSlashIfNeed(path);
		}
		final HttpServletRequest request = context.getRequest()
				.getNativeResource();
		for (Object n : new EnumerationIterable(request.getAttributeNames())) {
			String name = (String) n;
			request.removeAttribute(name);
		}
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		final Map<String, Object> map = this.paramMap;
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(
				request) {

			@Override
			public String getMethod() {
				return "GET";
			}

			@Override
			public String getRequestURI() {
				final String path = getPath();
				final String contextPath = getContextPath();
				return ("/".equals(contextPath) == false) ? contextPath + path
						: path;
			}

			@Override
			public String getParameter(String name) {
				String[] values = getParameterValues(name);
				return (values != null) ? values[0] : null;
			}

			@Override
			public Map getParameterMap() {
				return map;
			}

			@Override
			public Enumeration getParameterNames() {
				return new IteratorEnumeration(map.keySet().iterator());
			}

			@Override
			public String[] getParameterValues(String name) {
				Object object = map.get(name);
				if (object instanceof String) {
					return new String[] { (String) object };
				} else if (object instanceof String[]) {
					return (String[]) object;
				}
				return null;
			}
		};
		if (context instanceof FrameworkComponent) {
			FrameworkComponent.class.cast(context).setRequest(
					new RequestImpl(wrapper, context.getResponse()
							.getNativeResource()));
		}
		String realpath = buildForwardPath(context, path);
		forward(context, realpath);
	}

	protected String buildForwardPath(WebContext context, String path) {
		final UrlBuilder builder = new UrlBuilder(path);
		final Map<String, Object> params = context.getRequest()
				.getAttributesAsMap();
		if (params.isEmpty() == false) {
			builder.addAll(params);
		}
		return builder.build();
	}

	protected void forward(WebContext context, String path) {
		context.forward(path);
	}

	@Override
	public Proceed setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		return this;
	}

}
