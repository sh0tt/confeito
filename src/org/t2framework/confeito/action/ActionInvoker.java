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
package org.t2framework.confeito.action;

import java.util.Map;

import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * ActionInvoker is an interface that has responsible for invoking action
 * method.
 * </p>
 * <#else>
 * <p>
 * ActionInvokerはアクション実行のためのインタフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public interface ActionInvoker {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Initialize ActionInvoker.This method must call once before invoking
	 * action for initializing this instance.
	 * </p>
	 * <#else>
	 * <p>
	 * ActionInvokerの初期化を行います。このメソッドはインスタンス化後に１度だけ呼ばれるため、
	 * invoke呼び出し時に必要な初期化処理をここで行ってください。
	 * 引数のpageDescMapはunmodifiableMapなので、Mapを変更したい場合は、Mapをコピーして 使用してください。
	 * </p>
	 * </#if>
	 * 
	 * @param webConfiguration
	 *            <#if locale="en">
	 *            <p>
	 *            the {@link WebConfiguration} instance.
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link WebConfiguration}インスタンス
	 *            </p>
	 *            </#if>
	 * @param pageDescMap
	 *            <#if locale="en">
	 *            <p>
	 *            unmodifiable map of url and {@link PageComponent}
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            変更不可能なURLと{@link PageComponent}のMap
	 *            </p>
	 *            </#if>
	 */
	void initialize(WebConfiguration webConfiguration,
			Map<String, PageComponent> pageDescMap);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Invoke the action with the candidates of {@link PageComponent} which is
	 * given at initializing time.This method may return null if there is no
	 * page instance for processing this request.
	 * </p>
	 * <#else>
	 * <p>
	 * アクションを実行します.実行にはinitializeメソッドで与えられた{@link PageComponent}候補を使って実行します.
	 * </p>
	 * </#if>
	 * 
	 * @param invokingContext
	 *            <#if locale="en">
	 *            <p>
	 *            the {@link WebConfiguration} instance.
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link WebConfiguration}インスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the {@link Navigation} instance to create response.
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         次の画面遷移を示す{@link Navigation}インスタンス
	 *         </p>
	 *         </#if>
	 */
	Navigation invoke(ActionInvokingContext invokingContext);

}
