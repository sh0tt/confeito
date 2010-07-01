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

import org.t2framework.confeito.Constants;

/**
 * <#if locale="en">
 * <p>
 * Utility for HTTP content-type.
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
public class ContentTypeUtil {

	public static String getContentType(String contentType) {
		if (contentType == null) {
			return Constants.HTML_CONTENT_TYPE;
		}
		String[] strs = StringUtil.split(contentType, ",");
		String[] contentTypes = removeSemiColon(strs);
		for (int i = 0; i < contentTypes.length; i++) {
			if (isHtmlContentType(contentTypes[i].trim())) {
				return Constants.HTML_CONTENT_TYPE;
			}
		}
		for (int i = 0; i < contentTypes.length; i++) {
			if (isXmlContentType(contentTypes[i].trim())) {
				return Constants.XHTML_CONTENT_TYPE;
			}
		}
		return Constants.HTML_CONTENT_TYPE;
	}

	protected static String[] removeSemiColon(String[] contentTypes) {
		for (int i = 0; i < contentTypes.length; i++) {
			String type = contentTypes[i];
			int index = type.indexOf(";");
			if (index != -1) {
				type = type.substring(0, index);
				contentTypes[i] = type;
			}
		}
		return contentTypes;
	}

	public static boolean isHtmlContentType(String type) {
		return type.indexOf(Constants.HTML_CONTENT_TYPE) != -1
				|| type.equals(Constants.ANY_CONTENT_TYPE);
	}

	public static boolean isXmlContentType(String type) {
		return type.indexOf(Constants.XHTML_CONTENT_TYPE) != -1
				|| type.indexOf(Constants.APPLICATION_XML_CONTENT_TYPE) != -1
				|| type.indexOf(Constants.TEXT_XML_CONTENT_TYPE) != -1;
	}

	public static String getEncodingFromContentType(String contentType) {
		if (contentType == null) {
			return null;
		}
		if (contentType != null) {
			int semi = contentType.indexOf(';');
			if (semi >= 0) {
				String sub = contentType.substring(semi + 1).trim();
				if (sub.toLowerCase().startsWith(Constants.PREFIX_CHARSET)) {
					return sub.substring(Constants.PREFIX_CHARSET.length())
							.trim();
				}
			}
		}
		return null;

	}
}
