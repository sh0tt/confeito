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
package org.t2framework.confeito.tag;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.util.DateUtil;
import org.t2framework.confeito.util.HtmlEscapeUtil;
import org.t2framework.confeito.util.URLUtil;

/**
 * <#if locale="en">
 * <p>
 * Functions is an utility class to be expected to use from JSP. To use
 * function, JSP2.0 spec or higher is required.
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
public class Functions {

	/**
	 * <#if locale="en">
	 * <p>
	 * Language to ResourceBundle mapping.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static Map<String, ResourceBundle> messageResourceMap = new HashMap<String, ResourceBundle>();

	/**
	 * <#if locale="en">
	 * <p>
	 * Output object.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param o
	 * @return escaped string or empty string
	 */
	public static String out(Object o) {
		return (o != null) ? escape(o) : "";
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Format to date.As default, pattern sets as yyyy/MM/DD.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param date
	 * @return yyyy/MM/dd format date string
	 */
	public static String dateFormat(Date date) {
		if (date == null) {
			return null;
		}
		String pattern = DateUtil.findPattern(WebContext.get()
				.getRequestLocale());
		return dateFormatWith(date, pattern);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Format to date with specifed pattern.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param date
	 * @param pattern
	 * @return formatted date string
	 */
	public static String dateFormatWith(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		final SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Append context-path to given parameter.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param orgurl
	 * @return context path adding url
	 */
	public static String url(String orgurl) {
		if (URLUtil.isAbsoluteUrl(orgurl)) {
			return orgurl;
		}
		WebContext context = WebContext.get();
		if (context == null) {
			return orgurl;
		}
		HttpServletRequest request = context.getRequest().getNativeResource();
		if (request == null) {
			return orgurl;
		}
		String url = "";
		final String contextPath = request.getContextPath();
		if (orgurl.indexOf(contextPath) == 0) {
			url = orgurl;
		} else {
			if (!contextPath.endsWith("/") && !orgurl.startsWith("/")) {
				url = contextPath + "/" + orgurl;
			} else {
				url = contextPath + orgurl;
			}
		}
		if (request.isRequestedSessionIdFromCookie()) {
			return url;
		} else {
			return context.getResponse().getNativeResource().encodeURL(url);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Escape object.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param input
	 * @return escaped string
	 */
	public static String escape(Object input) {
		if (input == null) {
			return "";
		}
		String s = "";
		if (input.getClass().isArray()) {
			s = convertFromArray(input);
		} else {
			s = input.toString();
		}
		return escape(s);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Escape string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @return escaped string
	 */
	public static String escape(String s) {
		return HtmlEscapeUtil.escape(s);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key, Object param1) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key, param1);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key, Object param1, Object param2) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key, param1, param2);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key, Object param1, Object param2,
			Object param3) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key, param1, param2,
				param3);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key, Object param1, Object param2,
			Object param3, Object param4) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key, param1, param2,
				param3, param4);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * NLS string.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return NLS message string
	 */
	public static String nls(String key, Object param1, Object param2,
			Object param3, Object param4, Object param5) {
		WebContext context = WebContext.get();
		return findMessage(context.getRequestLocale(), key, param1, param2,
				param3, param4, param5);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Add message resource.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundle
	 */
	public static void addMessageResource(ResourceBundle bundle) {
		messageResourceMap.put(bundle.getLocale().getLanguage(), bundle);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Find messages.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param locale
	 * @param key
	 * @param objects
	 * @return message
	 */
	protected static String findMessage(Locale locale, String key,
			Object... objects) {
		ResourceBundle rb = messageResourceMap.get(locale.getLanguage());
		if (rb == null) {
			rb = messageResourceMap.get(Locale.getDefault().getLanguage());
			if (rb == null) {
				rb = messageResourceMap.get("");
				if (rb == null) {
					return "";
				}
			}
		}
		if (!rb.containsKey(key)) {
			return "";
		}
		String s = rb.getString(key);
		if (objects != null && 0 < objects.length) {
			return MessageFormat.format(s, objects);
		}
		return s;
	}

	private static String convertFromArray(Object input) {
		Class<?> clazz = input.getClass().getComponentType();
		String s = "";
		if (clazz == String.class) {
			s = Arrays.toString((Object[]) input);
		} else if (clazz == boolean.class) {
			s = Arrays.toString((boolean[]) input);
		} else if (clazz == int.class) {
			s = Arrays.toString((int[]) input);
		} else if (clazz == long.class) {
			s = Arrays.toString((long[]) input);
		} else if (clazz == byte.class) {
			s = Arrays.toString((byte[]) input);
		} else if (clazz == short.class) {
			s = Arrays.toString((short[]) input);
		} else if (clazz == float.class) {
			s = Arrays.toString((float[]) input);
		} else if (clazz == double.class) {
			s = Arrays.toString((double[]) input);
		} else if (clazz == char.class) {
			s = Arrays.toString((char[]) input);
		} else {
			s = Arrays.toString((Object[]) input);
		}
		return s;
	}
}
