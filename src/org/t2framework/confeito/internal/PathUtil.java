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
package org.t2framework.confeito.internal;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * PathUtil is an utility class for creating path. This class is basically
 * expected to use within T2 framework.
 * </p>
 * <#else>
 * <p>
 * PathUtilはT2で使うパスを作るためのユーティリティクラスです．このクラスは主にT2フレームワークで内部的に使われることを意図しています.
 * </p>
 * </#if>
 * 
 * @author shot
 * @author c9katayama
 */
public class PathUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get page path from {@link Request}. Page path is a path removed context
	 * path from original path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Request}からページパスを取得します.ページパスはオリジナルなパスから、コンテキストパスを除いたパスです.
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            <#if locale="en">
	 *            <p>
	 *            {@link Request} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link Request}インスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the page path
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ページパス
	 *         </p>
	 *         </#if>
	 */
	public static String getPagePath(Request request) {
		final HttpServletRequest req = request.getNativeResource();
		return getPagePath(req);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get page path from {@link HttpServletRequest}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletRequest}からページパスを取得します.
	 * ページパスはオリジナルなパスから、コンテキストパスを除いたパスです.
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            <#if locale="en">
	 *            <p>
	 *            {@link HttpServletRequest} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link HttpServletRequest}インスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the page path
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ページパス
	 *         </p>
	 *         </#if>
	 */
	public static String getPagePath(HttpServletRequest request) {
		final String contextPath = request.getContextPath();
		final String requestURI = request.getRequestURI();
		return getPagePath(contextPath, requestURI);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get page path from context path and decoded request URI.The path should
	 * be found by these pattern below:
	 * 
	 * <pre>
	 * URI          contextpath   getPagePath
	 *  /               /            /
	 *  /index          /            /index
	 *  /index/         /            /index
	 *  /index/hoge     /            /index/hoge
	 *  /index          /index       /
	 *  /index/hoge     /index       /hoge
	 *  /index/hoge/    /index       /hoge
	 * </pre>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * コンテキストパスとデコードされたリクエストURIからページパスを取得します．リクエストされたURIとコンテキストパス、
	 * ページパスの関係は下記のパターンのようになります．
	 * 
	 * <pre>
	 * URI          contextpath   getPagePath
	 *  /               /            /
	 *  /index          /            /index
	 *  /index/         /            /index
	 *  /index/hoge     /            /index/hoge
	 *  /index          /index       /
	 *  /index/hoge     /index       /hoge
	 *  /index/hoge/    /index       /hoge
	 * </pre>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param contextPath
	 *            <#if locale="en">
	 *            <p>
	 *            the context path
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンテキストパス
	 *            </p>
	 *            </#if>
	 * @param decodedUri
	 *            <#if locale="en">
	 *            <p>
	 *            decoded request URI string
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            デコードされたリクエストURI文字列
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the page path
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ページパス
	 *         </p>
	 *         </#if>
	 */
	public static String getPagePath(String contextPath, String decodedUri) {
		String requestUri = removalJSessionId(decodedUri);
		int index = requestUri.indexOf(contextPath);
		String path = null;
		if (index >= 0) {
			path = requestUri.substring(index + contextPath.length(),
					requestUri.length());
		} else {
			path = requestUri;
		}
		if (path.startsWith("/") == false) {
			path = "/" + path;
		}
		if (path.equals("/") == false && path.endsWith("/")) {
			path = removeEndSlashIfNeed(path);
		}
		return path;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get action method path from page path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 * @param pageTemplatePath
	 * @return action path
	 */
	public static String getActionPath(final Request request,
			String pageTemplatePath) {
		pageTemplatePath = PathUtil.appendStartSlashIfNeed(Assertion
				.notNull(pageTemplatePath));

		String actualPath = getPagePath(request);
		pageTemplatePath = appendStartSlashIfNeed(pageTemplatePath);

		int pos1 = pageTemplatePath.indexOf('{');
		int pos2 = pageTemplatePath.indexOf('}');
		if (pos1 < 0 || pos2 < 0 || pos2 < pos1) {
			if (actualPath.startsWith(pageTemplatePath)) {
				actualPath = actualPath.substring(pageTemplatePath.length());
				actualPath = appendStartSlashIfNeed(actualPath);
				return actualPath;
			} else {
				return actualPath;
			}
		} else {
			String[] templates = StringUtil.split(pageTemplatePath, "/");
			String[] actuals = StringUtil.split(actualPath, "/");
			boolean ret = true;
			if (templates.length <= actuals.length) {
				for (int i = 0; i < templates.length; i++) {
					String t = templates[i];
					int index1 = t.indexOf('{');
					if (index1 < 0) {
						if (t.equals(actuals[i])) {
							continue;
						} else {
							ret = false;
							break;
						}
					}
					int index2 = t.indexOf('}');
					if (index2 < 0 || index2 < index1) {
						ret = false;
						break;
					}
				}
				if (ret) {
					StringBuilder builder = new StringBuilder("/");
					for (int i = templates.length; i < actuals.length; i++) {
						builder.append(actuals[i]);
						builder.append("/");
					}
					int len = builder.length();
					if (1 < len) {
						builder.setLength(len - 1);
					}
					return new String(builder);
				} else {
					return actualPath;
				}
			} else {
				return actualPath;
			}
		}

	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Append slash to the first if the path is not started with slash.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return slash-added path
	 */
	public static String appendStartSlashIfNeed(final String path) {
		if (path != null && !startsWithSlash(path)) {
			return "/" + path;
		}
		return path;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Append slash to the end if the path is not ended with slash.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return end slash-added path
	 */
	public static String appendEndSlashIfNeed(final String path) {
		if (path != null && !endsWithSlash(path)) {
			return path + "/";
		}
		return path;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Remove slash from the begin.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return start-slash removed path
	 */
	public static String removeStartSlashIfNeed(final String path) {
		if (path != null && startsWithSlash(path)) {
			return path.substring(1, path.length());
		}
		return path;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Remove slash from the end.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return end-slash removed path
	 */
	public static String removeEndSlashIfNeed(final String path) {
		if (path != null && path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Check if the path starts with slash.
	 * </p>
	 * <#else>
	 * <p>
	 * パスが/で始まっている場合、trueを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 *            <#if locale="en">
	 *            <p>
	 *            the path
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            パス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         true if path starts with {@literal /}, otherwise false
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         パスが/で終わっている場合、true.それ以外の場合false.
	 *         </p>
	 *         </#if>
	 */
	public static boolean startsWithSlash(final String path) {
		if (StringUtil.isEmpty(path)) {
			return false;
		}
		return path.indexOf("/") == 0;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Check if the path ends with slash.
	 * </p>
	 * <#else>
	 * <p>
	 * パスの末端が/で終わっている場合、trueを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 *            <#if locale="en">
	 *            <p>
	 *            the path
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            パス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         true if path ends with {@literal /}, otherwise false
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         パスの末端が/で終わっている場合、true.それ以外の場合false.
	 *         </p>
	 *         </#if>
	 */
	public static boolean endsWithSlash(final String path) {
		if (StringUtil.isEmpty(path)) {
			return false;
		}
		return path.lastIndexOf("/") == path.length() - 1;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Remove jsessionid from the path.
	 * </p>
	 * <#else>
	 * <p>
	 * jsessionidをパスから削除します.
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 *            <#if locale="en">
	 *            <p>
	 *            the path
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            パス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         jsessionid removed path
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         jsessionidを削除したパス
	 *         </p>
	 *         </#if>
	 */
	public static String removalJSessionId(final String path) {
		int idx = path.indexOf(';');
		if (0 <= idx) {
			return path.substring(0, idx);
		}
		return path;
	}
}
