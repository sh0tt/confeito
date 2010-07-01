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
package org.t2framework.confeito.adapter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * The base {@link ContainerAdapter} class.User should extend this base class
 * instead of {@link ContainerAdapter} itself because of compatibility issue.
 * </p>
 * <#else>
 * <p>
 * {@link ContainerAdapter}の共通クラスです.
 * 各ユーザはこのクラスを拡張して、自前のContainerAdapterを作成することが出来ます.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 * @param <C>
 *            <#if locale="en">
 *            <p>
 *            the container type
 *            </p>
 *            <#else>
 *            <p>
 *            コンテナのクラス
 *            </p>
 *            </#if>
 */
public abstract class AbstractContainerAdapter<C> implements
		ContainerAdapter<C> {

	/**
	 * <#if locale="en">
	 * <p>
	 * A {@link FilterConfig} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link FilterConfig}のインスタンスです.
	 * </p>
	 * </#if>
	 */
	protected FilterConfig config;

	/**
	 * <#if locale="en">
	 * <p>
	 * A {@link ServletContext} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ServletContext}のインスタンスです.
	 * </p>
	 * </#if>
	 */
	protected ServletContext servletContext;

	/**
	 * <#if locale="en">
	 * <p>
	 * A threadlocal {@link HttpServletRequest} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * スレッドローカル領域の{@link HttpServletRequest}のインスタンスです.
	 * </p>
	 * </#if>
	 */
	protected ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();

	/**
	 * <#if locale="en">
	 * <p>
	 * A threadlocal {@link HttpServletResponse} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * スレッドローカル領域の{@link HttpServletResponse}のインスタンスです.
	 * </p>
	 * </#if>
	 */
	protected ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

	/**
	 * <#if locale="en">
	 * <p>
	 * Eagerload option.
	 * </p>
	 * <#else>
	 * <p>
	 * イーガーロードのオプション.　
	 * </p>
	 * </#if>
	 */
	protected boolean eagerLoad;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilterConfig(FilterConfig config) {
		this.config = Assertion.notNull(config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRequest(HttpServletRequest request) {
		this.requestThreadLocal.set(Assertion.notNull(request));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResponse(HttpServletResponse response) {
		this.responseThreadLocal.set(Assertion.notNull(response));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getEagerLoad() {
		return eagerLoad;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link HttpServletRequest} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletRequest}を返します.
	 * </p>
	 * </#if>
	 */
	public HttpServletRequest getRequest() {
		return requestThreadLocal.get();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link HttpServletResponse} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletResponse}を返します.
	 * </p>
	 * </#if>
	 */
	public HttpServletResponse getResponse() {
		return responseThreadLocal.get();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set eager load.
	 * </p>
	 * <#else>
	 * <p>
	 * eagerLoadに値をセットします.
	 * </p>
	 * </#if>
	 */
	public void setEagerLoad(boolean eagerLoad) {
		this.eagerLoad = eagerLoad;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Clear request and responce.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストとレスポンスをクリアします.
	 * </p>
	 * </#if>
	 */
	@Override
	public void endRequest() {
		this.requestThreadLocal.remove();
		this.responseThreadLocal.remove();
	}

}
