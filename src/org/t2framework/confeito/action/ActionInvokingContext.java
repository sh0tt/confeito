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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.method.ActionMethodResolver;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.parameter.ParameterResolver;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * Context object for invoking an action.
 * 
 * </p>
 * <#else>
 * <p>
 * アクション実行時のコンテキストオブジェクトです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface ActionInvokingContext {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ActionContext}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionContext}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the target ActionContext
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         対象のActionContext
	 *         </p>
	 *         </#if>
	 * 
	 */
	ActionContext getActionContext();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link WebContext}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebContext}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the target WebContext
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         対象のWebContext
	 *         </p>
	 *         </#if>
	 * 
	 */
	WebContext getWebContext();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get the status of processing action.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 現在のアクションの状態を返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the current status of invoking action
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         現在のアクションの実行状態
	 *         </p>
	 *         </#if>
	 * 
	 */
	ActionStatus getActionStatus();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set the current status of action.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 現在のアクションの実行状態を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param actionStatus
	 *            <#if locale="en">
	 *            <p>
	 *            the current status of invoking action
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            現在のアクションの実行状態
	 *            </p>
	 *            </#if>
	 * 
	 */
	void setActionStatus(ActionStatus actionStatus);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get the result of processing and invoking action as {@link Navigation}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行結果の{@link Navigation}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the result navigation instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         アクション実行結果であるNavigationインスタンス
	 *         </p>
	 *         </#if>
	 * 
	 */
	Navigation getResultNavigation();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set the result as {@link Navigation}.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行結果の{@link Navigation}を設定します.
	 * </p>
	 * </#if>
	 * 
	 * @param navigation
	 *            <#if locale="en">
	 *            <p>
	 *            the result navigation instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            アクション実行結果であるNavigationインスタンス
	 *            </p>
	 *            </#if>
	 * 
	 */
	void setResultNavigation(Navigation navigation);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link AnnotationResolverCreator}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link AnnotationResolverCreator}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the AnnotationResolverCreator instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         AnnotationResolverCreatorインスタンス
	 *         </p>
	 *         </#if>
	 * 
	 */
	AnnotationResolverCreator getResolverCreator();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ContainerAdapter}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ContainerAdapter}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the ContainerAdapter instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ContainerAdapterインスタンス
	 *         </p>
	 *         </#if>
	 */
	ContainerAdapter<?> getContainerAdapter();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link PluginProcessor} for processing plugin.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link PluginProcessor}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the PluginProcessor instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         PluginProcessorインスタンス
	 *         </p>
	 *         </#if>
	 */
	PluginProcessor getPluginProcessor();

	/**
	 * <#if locale="en">
	 * <p>
	 * Create page instance by {@link PageComponent} and
	 * {@link ContainerAdapter} .This method may return null.
	 * </p>
	 * <#else>
	 * <p>
	 * ページのインスタンスを{@link PageComponent}と{@link ContainerAdapter}
	 * を使って生成します.このメソッドはnullを返す場合があります.
	 * </p>
	 * </#if>
	 * 
	 * @param pageDesc
	 *            <#if locale="en">
	 *            <p>
	 *            the PageDesc instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            PageDescインスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         the created page instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         生成されたページインスタンス
	 *         </p>
	 *         </#if>
	 */
	Object createPage(PageComponent pageDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Create map of {@link ActionMethodResolver}.The key of map is an
	 * annotation class, although the value is {@link ActionMethodResolver}.
	 * This method must not return null and return empty map instead.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionMethodResolver}のMapを生成します.Mapのキーはアノテーションのクラスで、値は
	 * {@link ActionMethodResolver}のインスタンスです.このメソッドはnullを返してはいけません.
	 * 代わりに空のMapを返します .
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the map of annotation class and ActionMethodResolver instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         アノテーションクラスとActionMethodResolverインスタンスのMap
	 *         </p>
	 *         </#if>
	 * @see org.t2framework.confeito.method.ActionMethodResolver
	 */
	Map<Class<? extends Annotation>, ActionMethodResolver> createActionMethodResolvers();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Create default action method resolver.This method must not return null.
	 * </p>
	 * <#else>
	 * <p>
	 * アクションメソッドのデフォルトを指定するActionMethodResolverを返します.このメソッドはnullを返してはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the default ActionMethodResolver instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         デフォルトをあらわすActionMethodResolverインスタンス
	 *         </p>
	 *         </#if>
	 * @see org.t2framework.confeito.method.ActionMethodResolver
	 */
	ActionMethodResolver createDefaultActionMethodResolver();

	/**
	 * <#if locale="en">
	 * <p>
	 * Create list of {@link ParameterResolver}.This method must return empty
	 * list instead of return null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ParameterResolver}のリストを返します.このメソッドはnullを返す代わりに空のListを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the list of ParameterResolver instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ParameterResolverインスタンスのList
	 *         </p>
	 *         </#if>
	 * @see org.t2framework.confeito.parameter.ParameterResolver
	 */
	List<ParameterResolver> createParameterResolvers();

	/**
	 * <#if locale="en">
	 * <p>
	 * Build {@link ActionContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionContext}を生成、ビルドします.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the created ActionContext
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         生成されたActionContextインスタンス
	 *         </p>
	 *         </#if>
	 */
	ActionContext buildActionContext();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set to next {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * 対象の{@link PageComponent}を次のインスタンスに設定します.
	 * </p>
	 * </#if>
	 */
	void nextPageDesc();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set arguments for the target action method.This given arguments must not
	 * be null.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行に必要な引数を設定します.与えられる引数はnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param args
	 *            <#if locale="en">
	 *            <p>
	 *            the action arguments
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            アクションの引数
	 *            </p>
	 *            </#if>
	 */
	void setActionArguments(Object[] args);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get action arguments.This method must not return null.
	 * </p>
	 * <#else>
	 * <p>
	 * アクション実行に必要な引数を取得します.このメソッドはnullを返しません.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the action arguments
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         アクションの引数
	 *         </p>
	 *         </#if>
	 */
	Object[] getActionArguments();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set created page instance for using at invoking action time. Page
	 * instance is created by
	 * {@link ActionInvokingContext#createPage(PageComponent)} . The page
	 * instance must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionInvokingContext#createPage(PageComponent)}
	 * によって、生成されたページインスタンスを設定します.ページインスタンスはnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param page
	 *            <#if locale="en">
	 *            <p>
	 *            the page instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            ページインスタンス
	 *            </p>
	 *            </#if>
	 */
	void setPage(Object page);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get page instance.The instance must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * ページインスタンスを生成します.このメソッドはnullを返しません.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the page instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         ページインスタンス
	 *         </p>
	 *         </#if>
	 */
	Object getPage();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get current trying {@code PageDesc}.There may be multiple page instance
	 * candidates for one request, and T2 framework accepts this.Because of
	 * that, this method returns each {@link PageComponent} one by one so that
	 * the caller(it may be {@link ActionContextBuilder} implementation or so)
	 * can try each page instance and choose much more appropriate one and its
	 * action method.
	 * </p>
	 * <#else>
	 * <p>
	 * 現在の{@link PageComponent}インスタンスを返します.
	 * T2では1つのリクエストに対して複数のページインスタンス候補があることを許容します. そのため、 {@link PageComponent}
	 * は1つ1つ試され、より適切なページインスタンスが用いられる必要があります.ほとんどの場合において、その実行役は
	 * {@link ActionContextBuilder} インスタンスのケースです.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the current {@link PageComponent} instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         現在の{@link PageComponent}インスタンス
	 *         </p>
	 *         </#if>
	 */
	PageComponent getCurrentPageDesc();
}
