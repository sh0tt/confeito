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
/**
 * <#if locale="en">
 * <p>
 * This package provides classes which is an adapter for 
 * object life cycle management container such as DI container.
 * 
 * You should look at these interfaces and classes below:
 * <ul>
 * 	<li>{@link org.t2framework.confeito.adapter.ContainerAdapter} : root interface for the adapter mechanism</li>
 * 	<li>{@link org.t2framework.confeito.adapter.AbstractContainerAdapter} : base adapter class</li>
 * 	<li>{@link org.t2framework.confeito.adapter.SimpleContainerAdapter} : default adapter class</li>
 *  <li>{@link org.t2framework.t2.adapter.LucyContainerAdapter} : container adapter for Lucy framework</li>
 * </ul>
 * </p>
 * <#else>
 * <p>
 * このパッケージでは、DIコンテナのようなオブジェクトのライフサイクルを管理するコンテナへのアダプタ機能を提供します.
 * 以下のインタフェースとクラスを見てみてください.
 * <ul>
 * 	<li>{@link org.t2framework.confeito.adapter.ContainerAdapter} : アダプタ機構のコアインタフェースです.</li>
 * 	<li>{@link org.t2framework.confeito.adapter.AbstractContainerAdapter} : アダプタの汎用親クラスです.</li>
 * 	<li>{@link org.t2framework.confeito.adapter.SimpleContainerAdapter} : デフォルトのシンプルなアダプタクラスです.</li>
 *  <li>{@link org.t2framework.t2.adapter.LucyContainerAdapter} : Lucyコンテナとの連携アダプタクラスです.</li>
 * </ul>
 * </p>
 * </#if>
 */
package org.t2framework.confeito.adapter;

