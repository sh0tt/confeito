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

import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.handler.ExceptionHandlerFactory;
import org.t2framework.confeito.model.Component;

/**
 * <#if locale="en">
 * <p>
 * Container adapter interface for object life cycle management object like DI
 * container.ContainerAdapter should not be implemented by user directly.Instead
 * of that,use {@link AbstractContainerAdapter} as the base class of extension
 * point for ContainerAdapter.
 * </p>
 * <#else>
 * <p>
 * DIコンテナのようなオブジェクトライフサイクルを管理するコンテナへのアダプタインタフェースです.
 * このインタフェースはユーザが直接実装するべきではありません.その代わりに、拡張ポイントとして
 * {@link AbstractContainerAdapter}を実装してください.
 * </p>
 * </#if>
 * 
 * @author shot
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
 * @see org.t2framework.confeito.adapter.AbstractContainerAdapter
 */
public interface ContainerAdapter<C> extends ContainerResource<C>,
		ExceptionHandlerFactory {

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize this ContainerAdapter instance without any configuration.This
	 * method(or {@link ContainerAdapter#init(String)}) must be called before
	 * any other method calling, otherwise some exception may be thrown.
	 * </p>
	 * <#else>
	 * <p>
	 * このContainerAdapterインスタンスをユーザの設定なしで初期化します. このメソッド(または
	 * {@link ContainerAdapter#init(String)})はこのクラスのその他のどのメソッドよりも先に呼ばれる必要があります.
	 * もし呼ばれない場合、何かの例外が発生する可能性があります.
	 * </p>
	 * </#if>
	 */
	void init();

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize this ContainerAdapter instance with user configuration.This
	 * method must be called before any other method calling, otherwise some
	 * exception may be thrown.
	 * </p>
	 * <#else>
	 * <p>
	 * このContainerAdapterインスタンスをユーザ設定で初期化します.
	 * このメソッドはこのクラスのその他のどのメソッドよりも先に呼ばれる必要があります. もし呼ばれない場合、何かの例外が発生する可能性があります.
	 * </p>
	 * </#if>
	 * 
	 * @param configPath
	 *            <#if locale="en">
	 *            <p>
	 *            user configuration path
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            ユーザの設定ファイルのパス
	 *            </p>
	 *            </#if>
	 */
	void init(String configPath);

	/**
	 * <#if locale="en">
	 * <p>
	 * True if a component instance exist for the type, otherwise false.
	 * </p>
	 * <#else>
	 * <p>
	 * クラスに対応するコンポーネントが存在する場合trueを返します.そうでない場合、falseを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param componentClass
	 *            <#if locale="en">
	 *            <p>
	 *            a component type
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのクラス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         true if the component exist, otherwise false
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         コンポーネントがあればtrue、それ以外の場合false
	 *         </p>
	 *         </#if>
	 */
	<T> boolean hasComponent(Class<T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get a component instance by the class.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたクラスに対応するインスタンスを取得します.
	 * </p>
	 * </#if>
	 * 
	 * @param componentClass
	 *            <#if locale="en">
	 *            <p>
	 *            a component type
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのクラス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the component
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         コンポーネント
	 *         </p>
	 *         </#if>
	 */
	<T> T getComponent(Class<? super T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get list of component by class.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたクラスに該当するコンポーネントのリストを取得します.
	 * </p>
	 * </#if>
	 * 
	 * @param componentClass
	 *            <#if locale="en">
	 *            <p>
	 *            a component type
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのクラス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         list of component candidates
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         コンポーネントのリスト
	 *         </p>
	 *         </#if>
	 */
	<T> List<T> getComponents(Class<? super T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link BeanDesc} fro the given class.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたクラスに対応する{@link BeanDesc}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @param componentClass
	 *            <#if locale="en">
	 *            <p>
	 *            a component type
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのクラス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the BeanDesc of the looking type
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         クラスに対応するBeanDescインスタンス
	 *         </p>
	 *         </#if>
	 */
	<T> Component<T> getBeanDesc(Class<? super T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Register a component by class.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたクラスを登録します.
	 * </p>
	 * </#if>
	 * 
	 * @param componentClass
	 *            <#if locale="en">
	 *            <p>
	 *            a component type
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのクラス
	 *            </p>
	 *            </#if>
	 */
	<T> void register(Class<? extends T> componentClass);

	/**
	 * <#if locale="en">
	 * <p>
	 * Register a component instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたインスタンスを登録します.
	 * </p>
	 * </#if>
	 * 
	 * @param component
	 *            <#if locale="en">
	 *            <p>
	 *            a component instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのインスタンス
	 *            </p>
	 *            </#if>
	 */
	<T> void register(T component);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link ServletContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ServletContext}インスタンスを設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param servletContext
	 *            <#if locale="en">
	 *            <p>
	 *            ServletContext instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            ServletContextのインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setServletContext(ServletContext servletContext);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link FilterConfig}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link FilterConfig}インスタンスを設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 *            <#if locale="en">
	 *            <p>
	 *            FilterConfig instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            FilterConfigのインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setFilterConfig(FilterConfig config);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link HttpServletRequest}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletRequest}インスタンスを設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 *            <#if locale="en">
	 *            <p>
	 *            HttpServletRequest instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            HttpServletRequestのインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setRequest(HttpServletRequest request);

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link HttpServletResponse}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletResponse}インスタンスを設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param response
	 *            <#if locale="en">
	 *            <p>
	 *            HttpServletResponse instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            HttpServletResponseのインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setResponse(HttpServletResponse response);

	/**
	 * <#if locale="en">
	 * <p>
	 * Inject external dependency to the managed components.
	 * </p>
	 * <#else>
	 * <p>
	 * 外部コンポーネントを管理下のコンポーネントにインジェクトします.
	 * </p>
	 * </#if>
	 * 
	 * @param t
	 *            <#if locale="en">
	 *            <p>
	 *            a component instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            コンポーネントのインスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the component
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         コンポーネントのインスタンス
	 *         </p>
	 *         </#if>
	 */
	<T> T injectDependency(T t);

	/**
	 * <#if locale="en">
	 * <p>
	 * Register components instance immediatelly if true.
	 * </p>
	 * <#else>
	 * <p>
	 * trueの場合、フレームワークからインスタンスを登録します.
	 * </p>
	 * </#if>
	 */
	boolean getEagerLoad();

	/**
	 * <#if locale="en">
	 * <p>
	 * This method invoke after request process end.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストが終わった後に呼び出されます.
	 * </p>
	 * </#if>
	 */
	void endRequest();

	/**
	 * <#if locale="en">
	 * <p>
	 * Destroy this ContainerAdapter instance.
	 * </p>
	 * <#else>
	 * <p>
	 * このContainerAdapterインスタンスを破棄します.
	 * </p>
	 * </#if>
	 */
	void destroy();

}
