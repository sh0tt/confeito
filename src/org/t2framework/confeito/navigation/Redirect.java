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

import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.HttpVersion;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.NoPageAnnotationException;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.util.JavaBeansUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.ServletUtil;

/**
 * <#if locale="en">
 * <p>
 * Redirect response.{@link Redirect} has three options:redirect to same
 * context, redirect to other context, and redirect to outer site.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @author c9katayama
 * 
 */
public class Redirect extends WebNavigation<Redirect> {

	/**
	 * Logger for {@link Redirect}.
	 */
	private static Logger logger = Logger.getLogger(Redirect.class);

	/**
	 * Redirect target.
	 */
	protected RedirectTarget redirctTarget = RedirectTarget.SAME_CONTEXT;

	/**
	 * Create {@link Redirect} by the given path.
	 * 
	 * @param path
	 * @return
	 */
	public static Redirect to(String path) {
		return new Redirect(path);
	}

	/**
	 * Create {@link Redirect} by the given class.
	 * 
	 * @param pageClass
	 * @return
	 */
	public static Redirect to(Class<?> pageClass) {
		return new Redirect(pageClass);
	}

	/**
	 * Create {@link Redirect} with outer url.
	 * 
	 * @param url
	 * @return
	 */
	public static Redirect toOuterUrl(String url) {
		return new Redirect(url, RedirectTarget.OUTER_URL);
	}

	/**
	 * Create {@link Redirect} with outer context url.
	 * 
	 * @param url
	 * @return
	 */
	public static Redirect toOuterContext(String url) {
		return new Redirect(url, RedirectTarget.OUTER_CONTEXT);
	}

	/**
	 * Construct {@link Redirect} with path.
	 * 
	 * @param path
	 */
	public Redirect(String path) {
		super(path);
	}

	/**
	 * Construct {@link Redirect} with page class.
	 * 
	 * @param pageClass
	 */
	public Redirect(Class<?> pageClass) {
		super(pageClass);
	}

	/**
	 * Construct {@link Redirect} with url and {@link RedirectTarget}.
	 * 
	 * @param url
	 * @param redirctTarget
	 */
	public Redirect(String url, RedirectTarget redirctTarget) {
		super(url);
		this.redirctTarget = redirctTarget;
	}

	/**
	 * Execute redirection.
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		final Class<?> pageClass = getPageClass();
		String realpath = null;
		if (pageClass != null) {
			Page page = this.pageClass.getAnnotation(Page.class);
			if (page == null) {
				throw new NoPageAnnotationException(pageClass);
			}
			realpath = page.value();
			if (Page.DEFAULT_STR.equals(realpath)) {
				realpath = toRealPath(context, JavaBeansUtil
						.decapitalize(pageClass.getSimpleName()));
			} else if (realpath.startsWith("/")) {
				realpath = toRealPath(context, realpath);
			}
		} else {
			realpath = (redirctTarget == RedirectTarget.OUTER_URL) ? path
					: toRealPath(context, path);
		}
		redirect(context, realpath);
	}

	protected String toRealPath(WebContext context, String path) {
		if (path == null) {
			return null;
		}
		if (path.startsWith("http")) {
			logger.log("ITDT0021");
			return path;
		}
		if (path.startsWith("/") == false) {
			logger.log("ITDT0022", new Object[] { "Redirect" });
			path = PathUtil.appendStartSlashIfNeed(path);
		}
		if (redirctTarget == RedirectTarget.OUTER_CONTEXT) {
			return path;
		}
		final String contextPath = context.getRequest().getContextPath();
		return PathUtil.endsWithSlash(contextPath) ? PathUtil
				.removeEndSlashIfNeed(contextPath)
				+ path : contextPath + path;
	}

	/**
	 * Do redirection with HTTP version.The process of redirection differs with
	 * HTTP version, currently 1.0 compatible way(default) or 1.1 way.
	 * 
	 * @param context
	 * @param realPath
	 */
	protected void redirect(final WebContext context, final String realPath) {
		if (this.httpVersion == HttpVersion.HTTP10) {
			context.redirect(realPath);
		} else {
			final HttpServletResponse response = context.getResponse()
					.getNativeResource();
			ServletUtil.redirect(response, realPath, false);
		}
	}

	/**
	 * Set specific HTTP version as {@link HttpVersion}.
	 */
	@Override
	public Redirect setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		return this;
	}

}
