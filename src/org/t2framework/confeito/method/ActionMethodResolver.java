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
package org.t2framework.confeito.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;

/**
 * <#if locale="en">
 * <p>
 * ActionMethodResolver is an interface to resolve action method mostly by
 * {@link Annotation}.
 * </p>
 * <#else>
 * <p>
 * ActionMethodResolverは、アクションメソッドアノテーションを解決するインターフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public interface ActionMethodResolver {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook point to process something before resolve() is called.
	 * </p>
	 * <#else>
	 * <p>
	 * resolve()が呼ばれる前に処理を行う為のフックポイントです.
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param annotation
	 * @param targetMethodDesc
	 */
	void preResolve(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if this action method should be handled by this resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * 引数のアクションメソッドがこのresolverで処理出来るかどうか.Trueを返す場合、このresolverでを処理する必要があります.
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param annotation
	 * @param targetMethodDesc
	 * @return <#if locale="en">
	 *         <p>
	 *         true if this action method should be handled by this resolver,
	 *         otherwise false.
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         引数のアノテーションを処理できるかどうか.Trueを返す場合、このアクションメソッドを処理する必要があります.
	 *         それ以外はfalseを返します .
	 *         </p>
	 *         </#if>
	 */
	boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Processing something for the given annotation.Usually, store method
	 * invoking information to {@link ActionContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 引数のアノテーションを元に、処理を行います.メソッド呼び出しに使用する情報は、{@link ActionContext}に格納します.
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param annotation
	 * @param targetMethodDesc
	 */
	void resolve(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Hook point to process something after resolve() is called.
	 * </p>
	 * <#else>
	 * <p>
	 * resolve()が呼ばれた後に処理を行う為のフックポイントです.
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param annotation
	 * @param targetMethodDesc
	 */
	void postResolve(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc);
}
