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
package org.t2framework.confeito.spi;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.method.ActionMethodResolver;
import org.t2framework.confeito.parameter.ParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * AnnotationResolverCreator is an interface to map annotation and its handler
 * for processing method annotation and parameter annotation. This is the
 * extension point to use with {@link ContainerAdapter} or
 * {@link java.util.ServiceLoader}. You can add
 * /META-INF/services/org.t2framework.spi.AnnotationResolverCreator file and
 * write your own AnnotationResolverCreator implementation.
 * </p>
 * <#else>
 * <p>
 * AnnotationResolverCreatorはメソッドアノテーションとそのハンドラである{@link ActionMethodResolver}
 * 、引数アノテーションとそのハンドラである{@link ParameterResolver}を結びつける
 * 機能を提供するインタフェースです．このインタフェースはユーザによって拡張されることを意図しており、実装としては主に
 * {@link ContainerAdapter}からの取得か、または{@link java.util.ServiceLoader}を使う想定です．
 * そのため、/META-INF/services/org.t2framework.spi.AnnotationResolverCreatorというファイルを
 * 配置して、その中に実装クラスを置くことで独自アノテーションとそのハンドラを追加したり、既存のものを変更したりすることが出来ます.
 * </p>
 * </#if>
 * 
 * @author shot
 * @see java.util.ServiceLoader
 * @see org.t2framework.confeito.method.ActionMethodResolver
 * @see org.t2framework.t2.action.method.ParameterResolver
 */
public interface AnnotationResolverCreator {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create list of {@link ParameterResolver}, which is a resolver class for
	 * the specified parameter annotation or type.This method must not return
	 * null, instead return empty list if there is none.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ParameterResolver}のリストを返します.{@link ParameterResolver}
	 * は引数アノテーションまたはクラスに対応する挙動を司るクラスです.このメソッドはnullを返す事はありません.代わりに空のListを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 *            <#if locale="en">
	 *            <p>
	 *            {@link ContainerAdapter} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link ContainerAdapter}のインスタンス
	 *            </p>
	 *            </#if>
	 * @param context
	 *            <#if locale="en">
	 *            <p>
	 *            {@link WebContext} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link WebContext}のインスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         list of {@link ParameterResolver}
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         {@link ParameterResolver}のList
	 *         </p>
	 *         </#if>
	 */
	List<ParameterResolver> createParameterResolvers(
			ContainerAdapter<?> containerAdapter, WebContext context);

	/**
	 * <#if locale="en">
	 * <p>
	 * Get sets of annotations that is used for action method.This method must
	 * not return null, instead return empty set.
	 * </p>
	 * <#else>
	 * <p>
	 * アクションメソッドで使うアノテーションのSetを返します.このメソッドはnullを返す事はありません.代わりに空のSetを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 *            <#if locale="en">
	 *            <p>
	 *            {@link ContainerAdapter} instance
	 *            </p>
	 *            <#else>
	 *            <p>
	 *            {@link ContainerAdapter}のインスタンス
	 *            </p>
	 *            </#if>
	 * @return <#if locale="en">
	 *         <p>
	 *         set of action method annotation classes
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         アクションメソッド用のアノテーションのSet
	 *         </p>
	 *         </#if>
	 */
	Set<Class<? extends Annotation>> getActionAnnotationSet(
			ContainerAdapter<?> containerAdapter);

	/**
	 * <#if locale="en">
	 * <p>
	 * Create map of Annotation and ActionAnnotationResolver, which is a class
	 * to handle how to behave if the specified method annotation is annotated.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 * @return map of annotation and {@link ActionMethodResolver}
	 */
	Map<Class<? extends Annotation>, ActionMethodResolver> createActionMethodResolvers(
			ContainerAdapter<?> containerAdapter);

	/**
	 * <#if locale="en">
	 * <p>
	 * Return default annotation resolver.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param containerAdapter
	 * @return default {@link ActionMethodResolver}
	 */
	ActionMethodResolver createDefaultActionMethodResolver(
			ContainerAdapter<?> containerAdapter);

}
