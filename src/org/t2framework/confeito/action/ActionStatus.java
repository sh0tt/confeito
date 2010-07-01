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

/**
 * 
 * <#if locale="en">
 * <p>
 * The status instance for the time of invoking action.
 * </p>
 * <#else>
 * <p>
 * アクション実行時の状態をあらわすインスタンスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public enum ActionStatus {
	/**
	 * <#if locale="en">
	 * <p>
	 * The first status.Nothing happens yet.
	 * </p>
	 * <#else>
	 * <p>
	 * 初期状態です．何もアクションは実行されていません．
	 * </p>
	 * </#if>
	 */
	NONE,

	/**
	 * <#if locale="en">
	 * <p>
	 * The beginning status.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行開始状態です．
	 * </p>
	 * </#if>
	 */
	BEGIN,

	/**
	 * <#if locale="en">
	 * <p>
	 * Starts action invoking.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行中の状態です．
	 * </p>
	 * </#if>
	 */
	INVOKE_BEGIN,

	/**
	 * <#if locale="en">
	 * <p>
	 * Found the target action method.
	 * </p>
	 * <#else>
	 * <p>
	 * 実行すべきアクションメソッドが見つかった状態です．
	 * </p>
	 * </#if>
	 */
	FOUND_ACTION_METHOD,

	/**
	 * <#if locale="en">
	 * <p>
	 * Ends action invoking.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行終了状態です．
	 * </p>
	 * </#if>
	 */
	INVOKE_END,

	/**
	 * <#if locale="en">
	 * <p>
	 * Something error happens.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行がエラー状態です．
	 * </p>
	 * </#if>
	 */
	ERROR,

	/**
	 * <#if locale="en">
	 * <p>
	 * No target instance found.Usually, it happens when configuration file is
	 * something wrong.
	 * </p>
	 * <#else>
	 * <p>
	 * ページインスタンスが見つからなかった場合の状態です．この状態の場合、設定ファイルなどが何かおかしい可能性があります．
	 * </p>
	 * </#if>
	 */
	NO_PAGE,

	/**
	 * <#if locale="en">
	 * <p>
	 * Ends all of invoking processing.Well done:)
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行状態が全て完了した状態です．
	 * </p>
	 * </#if>
	 */
	END;
}
