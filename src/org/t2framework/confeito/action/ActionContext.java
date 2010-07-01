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

import java.lang.reflect.Method;
import java.util.List;

import org.t2framework.confeito.contexts.AmfContext;
import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.model.PageComponent;

/**
 * 
 * <#if locale="en">
 * <p>
 * {@link ActionContext} is a context class that has all information for
 * invoking an action method.
 * </p>
 * <#else>
 * <p>
 * {@link ActionContext}はアクションメソッドを実行するための情報を保持するコンテキストオブジェクトのインタフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface ActionContext {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set target {@link MethodDesc}.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象の{@link MethodDesc}を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param methodDesc
	 *            <#if locale="en">
	 *            <p>
	 *            the target MethodDesc
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            対象のMethodDesc
	 *            </p>
	 *            </#if>
	 */
	void setTargetMethodDesc(Method methodDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get target action method.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象の{@link MethodDesc}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the target MethodDesc
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         対象のMethodDesc
	 *         </p>
	 *         </#if>
	 * @throws IllegalStateException
	 *             <#if locale="en">
	 *             <p>
	 *             in case of the target MethodDesc is null
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             対象のMethodDescが無い場合
	 *             </p>
	 *             </#if>
	 */
	Method getTargetMethodDesc() throws IllegalStateException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get page template path.In case of target {@link PageComponent} is not
	 * set, it may throw {@link IllegalStateException}.
	 * </p>
	 * <#else>
	 * <p>
	 * ページインスタンスのテンプレートパスを取得します．{@link PageComponent}が無い場合、
	 * {@link IllegalStateException}が発生します．
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the page template path
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ページインスタンスに指定されたテンプレートパス
	 *         </p>
	 *         </#if>
	 * @throws IllegalStateException
	 *             <#if locale="en">
	 *             <p>
	 *             in case of PageDesc is not set
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             PageDescがセットされていない場合
	 *             </p>
	 *             </#if>
	 */
	String getPageTemplatePath() throws IllegalStateException;

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Request}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Request}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         request instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         Requestのインスタンス
	 *         </p>
	 *         </#if>
	 */
	Request getRequest();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Response}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Response}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         response instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         Responseのインスタンス
	 *         </p>
	 *         </#if>
	 */
	Response getResponse();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Session}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Session}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         session instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         Sessionのインスタンス
	 *         </p>
	 *         </#if>
	 */
	Session getSession();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link Application}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Application}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         application instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         Sessionのインスタンス
	 *         </p>
	 *         </#if>
	 */
	Application getApplication();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Increment match counts when finding target method.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象となるメソッド候補が見つかった場合にカウントアップします.
	 * </p>
	 * </#if>
	 * 
	 */
	void incrementMatchCount();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get how many action method matches.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象となるメソッド候補がどれだけ条件にマッチしているかを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         matched counts
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         対象メソッド候補のマッチしている数
	 *         </p>
	 *         </#if>
	 */
	int getMatchCount();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Clear matching count.
	 * </p>
	 * <#else>
	 * <p>
	 * メソッド候補のカウントをクリアします.
	 * </p>
	 * </#if>
	 * 
	 */
	void clearMatchCount();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link ErrorInfo}. {@link ErrorInfo} notifies whether there are
	 * errors at converting or not.It may returns null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ErrorInfo}を取得します.
	 * ErrorInfoはコンバージョン結果がエラーを返すかどうかを保持します.このメソッドはnullを返す場合があります.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         an {@link ErrorInfo} instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         {@link ErrorInfo}のインスタンス
	 *         </p>
	 *         </#if>
	 * @see org.t2framework.confeito.action.ErrorInfo
	 */
	ErrorInfo getErrorInfo();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set {@link ErrorInfo}.The ErrorInfo instance must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ErrorInfo}を設定します.ErrorInfoのインスタンスはnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param errorInfo
	 *            <#if locale="en">
	 *            <p>
	 *            an {@link ErrorInfo} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link ErrorInfo}のインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setErrorInfo(ErrorInfo errorInfo);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Clear {@link ErrorInfo} if exists.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ErrorInfo}のインスタンスをクリアします.
	 * </p>
	 * </#if>
	 * 
	 */
	void clearErrorInfo();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set candidates of {@link PageComponent} for invoking appropriate action
	 * method. The candidates should be found by
	 * {@link PageDescFinder#find(org.t2framework.confeito.contexts.WebContext)}
	 * .
	 * </p>
	 * <#else>
	 * <p>
	 * アクションメソッドの実行をするための{@link PageComponent}の候補を設定します.この候補は
	 * {@link PageDescFinder#find(org.t2framework.confeito.contexts.WebContext)}
	 * によって見つかったものです.
	 * </p>
	 * </#if>
	 * 
	 * @param pageDescCandidates
	 *            <#if locale="en">
	 *            <p>
	 *            list of {@link PageComponent} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link PageComponent}のリスト
	 *            </p>
	 *            </#if>
	 */
	void setPageDescCandidates(List<PageComponent> pageDescCandidates);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get the action invoking candidates of {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行のための{@link PageComponent}候補リストを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         list of {@link PageComponent} candidates
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         {@link PageComponent}のリスト
	 *         </p>
	 *         </#if>
	 */
	List<PageComponent> getPageDescCandidates();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if the {@link PageComponent} candidates exist for processing this
	 * request.
	 * </p>
	 * <#else>
	 * <p>
	 * 候補となる{@link PageComponent}がいる場合、trueを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         true if candidates of page instance exist, otherwise false
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         候補となる{@link PageComponent}が存在する場合true、それ以外の場合はfalseを返します.
	 *         </p>
	 *         </#if>
	 */
	boolean hasPageDescCandidates();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set target {@link PageComponent}.This must be done before actual finding
	 * action method and invoking these method.
	 * </p>
	 * <#else>
	 * <p>
	 * 候補の中から対象とする{@link PageComponent}を設定します.
	 * このメソッドはアクションメソッドの探索と実行が行われる前に呼び出す必要があります
	 * </p>
	 * </#if>
	 * 
	 * @param pageDesc
	 *            <#if locale="en">
	 *            <p>
	 *            the target PageDesc instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            対象となった{@link PageComponent}インスタンスを返します.
	 *            </p>
	 *            </#if>
	 */
	void setTargetPageDesc(PageComponent pageDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get the target {@link PageComponent}.Throw {@link IllegalStateException}
	 * if no page desc found.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象となった{@link PageComponent}を返します.もし対象が無い場合、{@link IllegalStateException}
	 * が発生します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the target PageDesc instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         対象となった{@link PageComponent}インスタンスを返します.
	 *         </p>
	 *         </#if>
	 * @throws IllegalStateException
	 */
	PageComponent getTargetPageDesc() throws IllegalStateException;

	/**
	 * <#if locale="en">
	 * <p>
	 * Add variables, which is represented by "{}". Varialbles is sometime used
	 * by {@literal @Page}, or {@link @ActionPath} to get some of the part of
	 * url in application code.See
	 * {@link org.t2framework.confeito.annotation.Var}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * T2の変数("{}"で囲まれている部分)のキーと値を保持します.変数は、{@literal @Page}や{@link @ActionPath}で
	 * URLの一部を取得するために用いられます.
	 * 
	 * 取得の仕方は、{@link org.t2framework.confeito.annotation.Var}を見てください.
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 *            <#if locale="en">
	 *            <p>
	 *            the key for this variable
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            変数のキー
	 *            </p>
	 *            </#if>
	 * @param value
	 *            <#if locale="en">
	 *            <p>
	 *            the value for this variable
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            変数の値
	 *            </p>
	 *            </#if>
	 */
	void addVariables(String key, String value);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get varialbes.
	 * </p>
	 * <#else>
	 * <p>
	 * 変数の値を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 *            <#if locale="en">
	 *            <p>
	 *            the key for this variable
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            変数のキー
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the value for this variable
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         変数の値
	 *         </p>
	 *         </#if>
	 */
	String getVariables(String key);

	/**
	 * <#if locale="en">
	 * <p>
	 * Clean variables.
	 * </p>
	 * <#else>
	 * <p>
	 * このコンテキストに設定した変数をクリアします.
	 * </p>
	 * </#if>
	 */
	void cleanVariables();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link AmfContext}, the context object for request coming from
	 * Flex/AIR.
	 * </p>
	 * <#else>
	 * <p>
	 * Flex/AIRからのリクエストである{@link AmfContext}を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param amfContext
	 * @see org.t2framework.confeito.contexts.AmfContext
	 */
	void setAmfContext(AmfContext amfContext);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link AmfContext}.This method might return null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link AmfContext}を設定します.このメソッドはnullを返す場合があります.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         an AmfContext instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         AmfContextのインスタンス
	 *         </p>
	 *         </#if>
	 * @see org.t2framework.confeito.contexts.AmfContext
	 */
	AmfContext getAmfContext();
}
