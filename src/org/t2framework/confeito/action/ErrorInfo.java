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

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * <#if locale="en">
 * <p>
 * {@link ErrorInfo} is an representation class of error information for
 * invoking action.If any error exists, {@link ErrorInfo#hasError()} returns
 * true and gets as map of arrays of {@link Throwable}.
 * </p>
 * <#else>
 * <p>
 * {@link ErrorInfo}はアクション実行時のエラー情報を表現します．何かのエラーがある場合、
 * {@link ErrorInfo#hasError()}がtrueを返し、{@link ErrorInfo#getErrors()}
 * によって例外を返します．
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface ErrorInfo extends Serializable {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Add {@link Throwable} with key.Both of key and {@link Throwable} must not
	 * be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 発生した例外を足します.キーである文字列と例外はどちらもnullであってはいけません．
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 *            <#if locale="en">
	 *            <p>
	 *            the key for this exception
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            発生した例外のキー
	 *            </p>
	 *            </#if>
	 * @param t
	 *            <#if locale="en">
	 *            <p>
	 *            the exception instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            発生した例外インスタンス
	 *            </p>
	 *            </#if>
	 */
	void addErrorInfo(String key, Throwable t);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if any action invoking error exist.
	 * </p>
	 * <#else>
	 * <p>
	 * もし例外があった場合、trueを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         true if there is an error or more, otherwise false
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         例外が会った場合、trueを返します.
	 *         </p>
	 *         </#if>
	 */
	boolean hasError();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get all errrors as {@link Map}.The returned map must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 全ての保持する例外をMapとして返します.このメソッドはnullを返しません.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         all errors as map
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         例外のMap
	 *         </p>
	 *         </#if>
	 */
	Map<String, Throwable> getErrorsAsMap();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get all errors as array.The returned array must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 保持する例外を配列で返します．このメソッドはnullを返しません.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         all errors as array
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         例外の配列
	 *         </p>
	 *         </#if>
	 */
	Throwable[] getErrors();

}
