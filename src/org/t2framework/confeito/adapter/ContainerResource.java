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
package org.t2framework.confeito.adapter;

/**
 * <#if locale="en">
 * <p>
 * ContainerResouce is an interface to provode any container like DI container.
 * This interface is not intended to implement directly by user.
 * </p>
 * <#else>
 * <p>
 * ContainerResouceはDIコンテナのようなコンテナオブジェクトを返すインタフェースです.
 * ユーザがこのインタフェースを意識することはありません.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 * @param <C>
 *            <#if locale="en">
 *            <p>
 *            the container type
 *            </p>
 *            <#else>
 *            <p>
 *            コンテナのクラス
 *            </p>
 *            </#if>
 */
public interface ContainerResource<C> {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get container as parameterized C.
	 * </p>
	 * <#else>
	 * <p>
	 * コンテナオブジェクトを取得します.
	 * </p>
	 * </#if>
	 * 
	 * @return <#if locale="en">
	 *         <p>
	 *         the container instance
	 *         </p>
	 *         <#else>
	 *         <p>
	 *         コンテナのインスタンス
	 *         </p>
	 *         </#if>
	 */
	C getContainer();
}
