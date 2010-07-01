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

import java.util.List;

import org.t2framework.confeito.contexts.WebConfiguration;

/**
 * <#if locale="en">
 * <p>
 * Factory class for {@link ActionInvoker}.This interface is expected to extend
 * by user.The user can implement own factory to return custom
 * {@link ActionInvoker}.
 * 
 * </p>
 * <#else>
 * <p>
 * {@link ActionInvoker} のためのファクトリインタフェースです.このインタフェースはユーザによって拡張されることを意図しています.
 * このファクトリを実装することで、ユーザは自分のActionInvokerを返したり、 ActionInvokerの順序を修正することが出来ます.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface ActionInvokerFactory {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create list of {@link ActionInvoker}.This method must not return null,
	 * instead of that, return empty list.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionInvoker}のリストを返します.
	 * このメソッドはnullを返してはいけません.null相当を返したい場合は空のリストを返してください.
	 * </p>
	 * </#if>
	 * 
	 * @param webConfig
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
	 *         the list of {@link ActionInvoker} instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         {@link ActionInvoker}インスタンスのリスト
	 *         </p>
	 *         </#if>
	 * 
	 */
	List<ActionInvoker> createActionInvoker(WebConfiguration webConfig);
}
