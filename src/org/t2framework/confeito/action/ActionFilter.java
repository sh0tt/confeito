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

import org.t2framework.confeito.model.PageComponent;

/**
 * 
 * <#if locale="en">
 * <p>
 * {@link ActionFilter} is a filter class for processing an action with page
 * instance.This filter is enable to extend and customize the process of
 * invoking action method.
 * 
 * For example, default behavior looks like:
 * <ol>
 * <li>Set error handling feature</li>
 * <li>Create page instance</li>
 * <li>Set up action arguments</li>
 * <li>Invoke the action</li>
 * </ol>
 * 
 * It is invoked by {@link ActionFilterChain}.
 * </p>
 * <#else>
 * <p>
 * {@link ActionFilter}はアクションを実行するための細分化されたフィルタです.このフィルタによって、
 * 柔軟にアクション実行までのプロセスをカスタマイズする事が可能です.例えばデフォルトでは以下のようになっています.
 * 
 * <ol>
 * <li>
 * エラーハンドリング設定</li>
 * <li>ページインスタンス生成</li>
 * <li>アクションメソッドの引数の特定</li>
 * <li>アクション実行</li>
 * </ol>
 * 
 * この実行は{@link ActionFilterChain}が行っています.
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.action.ActionFilterChain
 */
public interface ActionFilter {

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke this filter.
	 * </p>
	 * <#else>
	 * <p>
	 * このフィルタを実行します.
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 *            <#if locale="en">
	 *            <p>
	 *            the {@link ActionInvokingContext} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link ActionInvokingContext}のインスタンス
	 *            </p>
	 *            </#if>
	 * @param pageDesc
	 *            <#if locale="en">
	 *            <p>
	 *            the target {@link PageComponent} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            対象の{@link PageComponent}インスタンス
	 *            </p>
	 *            </#if>
	 * @param chain
	 *            <#if locale="en">
	 *            <p>
	 *            the executing {@link ActionFilterChain} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            このフィルタを実行している{@link ActionFilterChain}インスタンス
	 *            </p>
	 *            </#if>
	 */
	void invoke(ActionInvokingContext context, PageComponent pageDesc,
			ActionFilterChain chain);
}
