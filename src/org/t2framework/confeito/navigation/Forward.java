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

import java.util.Map;

import org.t2framework.confeito.HttpVersion;
import org.t2framework.confeito.annotation.ForwardTo;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.NoForwardToAnnotationException;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.UrlBuilder;

/**
 * <#if locale="en">
 * <p>
 * Forward response.To forward, there are two options:by path, or by class. By
 * path, you can just set the path you would like to navigate.By class, you set
 * the class which is annotated by {@literal @ForwardTo}.
 * 
 * </p>
 * <#else>
 * <p>
 * フォワード遷移を実現する{@link Navigation}です.フォワード遷移するには2つの方法があります.
 * 1つがパスを直接渡す方法でほとんどの場合この方法を取ります.もう1つの方法がClassを使う方法です. Classを使う場合は、{@literal
 * @ForwardTo}でアノテートしたClassを渡します.
 * </p>
 * </#if>
 * 
 * @author shot
 * @author c9katayama
 */
public class Forward extends WebNavigation<Forward> {

	/**
	 * Logger for this Forward.
	 */
	private static Logger logger = Logger.getLogger(Forward.class);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Create Forward instance by class.
	 * </p>
	 * <#else>
	 * <p>
	 * ForwardインスタンスをClassから生成します.
	 * </p>
	 * </#if>
	 * 
	 * @param forwardClass
	 * @return Forward instance
	 */
	public static Forward to(final Class<?> forwardClass) {
		return new Forward(forwardClass);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create Forward instance by path.
	 * </p>
	 * <#else>
	 * <p>
	 * Forwardインスタンスを遷移先パスから生成します.
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 * @return Forward instance
	 */
	public static Forward to(final String path) {
		return new Forward(path);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct Forward instance by path.
	 * </p>
	 * <#else>
	 * <p>
	 * 遷移パスでForwardインスタンスを構築します.
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 */
	public Forward(String path) {
		super(path);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct Forward instance by class.
	 * </p>
	 * <#else>
	 * <p>
	 * クラスでForwardインスタンスを構築します.
	 * </p>
	 * </#if>
	 * 
	 * @param pageClass
	 */
	public Forward(Class<?> pageClass) {
		super(pageClass);
	}

/**
	 * <#if locale="en">
	 * <p>
	 * Execute to navigate.
	 * 
	 * If the class is given, then get {@literal
	 * @ForwardTo#value()} as a path.If the path string is given, just use it to navigate.
	 * 
	 * After that, path is normalized to the real path executed by {@link UrlBuilder#build()}.
	 * {@link UrlBuilder} adds request parameters to path if there is no {@literal '?'} including in the given path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * フォワード遷移を実行します.
	 * 
	 * クラスがユーザから与えられていた場合、{@literal @ForwardTo}から遷移先パスを構築します.パスが与えられていた場合はそのまま使用します.
	 * パスは正規化され、クエリストリングが元々付加されていない場合、リクエストパラメータからクエリストリングを構築します.これは{@link UrlBuilder#build()}で行われます.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.commons.util.UrlBuilder
	 * @throws NoForwardToAnnotationException if the class given does not contain {@literal @ForwardTo} annotation.
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		String path = null;
		final Class<?> pageClass = getPageClass();
		if (pageClass != null) {
			ForwardTo forwardTo = this.pageClass.getAnnotation(ForwardTo.class);
			if (forwardTo == null) {
				throw new NoForwardToAnnotationException(pageClass);
			}
			path = forwardTo.value();
		} else {
			path = getPath();
		}
		if (path.startsWith("/") == false) {
			logger.log("ITDT0020", new Object[] { "Forward" });
			path = PathUtil.appendStartSlashIfNeed(path);
		}
		String realpath = buildForwardPath(context, path);
		forward(context, realpath);
	}

	protected String buildForwardPath(WebContext context, String path) {
		final UrlBuilder builder = new UrlBuilder(path);
		Map<String, String[]> parameters = context.getRequest()
				.getParametersAsMap();
		if (parameters.isEmpty() == false && path.indexOf('?') < 0) {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				String key = entry.getKey();
				String[] value = entry.getValue();
				if (value != null && value.length == 1) {
					builder.add(key, value[0]);
				} else {
					builder.add(key, value);
				}
			}
		}
		return builder.build();
	}

	protected void forward(WebContext context, String path) {
		context.forward(path);
	}

	@Override
	public Forward setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		return this;
	}
}
