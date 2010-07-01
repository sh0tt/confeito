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

import org.t2framework.confeito.exception.NoActionMethodFoundRuntimeException;

/**
 * <#if locale="en">
 * <p>
 * ActionContextBuilder is an interface which has responsible for building and
 * configuring {@link ActionContext}.
 * </p>
 * <#else>
 * <p>
 * ActionContextBuilderは{@link ActionContext}を生成して、
 * 実行時に必要なアクションメソッドや変数情報を設定するインタフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public interface ActionContextBuilder {

	/**
	 * <#if locale="en">
	 * <p>
	 * Build and set up ActionContext with {@link ActionInvokingContext}
	 * instance.{@link ActionInvokingContext} instance must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionInvokingContext}インスタンスを使って、ActionContextをビルドします.
	 * {@link ActionInvokingContext}インスタンスはnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param invokingActionContext
	 *            <#if locale="en">
	 *            <p>
	 *            an {@link ActionInvokingContext} instance.
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link ActionInvokingContext}インスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         an built {@link ActionContext} instance.
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ビルドされた{@link ActionContext}インスタンス
	 *         </p>
	 *         </#if>
	 * @throws NoActionMethodFoundRuntimeException
	 *             <#if locale="en">
	 *             <p>
	 *             if any action method is not found
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             実行すべきアクションメソッドが一つも見つからない場合
	 *             </p>
	 *             </#if>
	 */
	ActionContext build(final ActionInvokingContext invokingActionContext)
			throws NoActionMethodFoundRuntimeException;

}
