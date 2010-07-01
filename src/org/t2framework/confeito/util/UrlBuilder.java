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

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <#if locale="en">
 * <p>
 * UrlBuilder is utility class to build url.
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
public class UrlBuilder {

	protected String base;

	protected final Map<String, Object> urlParameters = new HashMap<String, Object>();

	public UrlBuilder() {
	}

	public UrlBuilder(final String base) {
		this.base = Assertion.notNull(base);
	}

	public void setBase(final String base) {
		this.base = Assertion.notNull(base);
	}

	public String build() {
		final StringBuilder sb = new StringBuilder(100);
		final URI uri = URI.create(base);
		if (uri.getScheme() != null) {
			sb.append(uri.getScheme());
			sb.append(":");
		}
		// [scheme:]scheme-specific-part[#fragment]
		// [scheme:][//authority][path][?query][#fragment]
		// server-based-authority=[user-info@]host[:port]
		if (uri.getAuthority() != null) {
			sb.append("//");
			sb.append(uri.getAuthority());
		}

		if (uri.getPath() != null) {
			sb.append(uri.getPath());
		} else if (uri.getSchemeSpecificPart() != null) {
			sb.append(uri.getSchemeSpecificPart());
		}
		boolean questionAppeared = false;
		if (uri.getQuery() != null) {
			questionAppeared = true;
			sb.append('?');
			sb.append(uri.getQuery());
		}
		for (final Iterator<String> it = urlParameters.keySet().iterator(); it
				.hasNext();) {
			final String key = it.next();
			Object value = urlParameters.get(key);
			if (value == null) {
				continue;
			}
			if (!value.getClass().isArray()) {
				if (questionAppeared) {
					sb.append('&');
				} else {
					sb.append('?');
					questionAppeared = true;
				}
				appenQueryString(key, value, sb, questionAppeared);
			} else {
				final Object[] values = (Object[]) value;
				for (final Object v : values) {
					if (questionAppeared) {
						sb.append('&');
					} else {
						sb.append('?');
						questionAppeared = true;
					}
					appenQueryString(key, v, sb, questionAppeared);
				}
			}
		}
		if (uri.getFragment() != null) {
			sb.append('#');
			sb.append(uri.getFragment());
		}
		return new String(sb);
	}

	protected void appenQueryString(String key, Object v, StringBuilder sb,
			boolean questionAppeared) {
		sb.append(key);
		sb.append('=');
		sb.append(ConverterUtil.convertAsString(v));
	}

	public void add(final String key, final Object value) {
		Assertion.notNulls(key, value);
		if (urlParameters.containsKey(key)) {
			Object o = urlParameters.get(key);
			if (o == null) {
				urlParameters.put(key, value);
			} else if (o.getClass().isArray()) {
				ArrayUtil.add((Object[]) o, value);
			} else {
				Object[] array = new Object[] { o, value };
				urlParameters.put(key, array);
			}
		} else {
			urlParameters.put(key, value);
		}
	}

	public String getBase() {
		return base;
	}

	public Map<String, Object> getUrlParameters() {
		return urlParameters;
	}

	public void addAll(Map<String, Object> params) {
		urlParameters.putAll(params);
	}

}
