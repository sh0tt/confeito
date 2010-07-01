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
 * Package provides action invoking features for T2 framework.This is truly the core package for T2.
 * It Provides {@link org.t2framework.confeito.action.ActionInvoker}, 
 * a invoker class for requested action. 
 * This should be the first stop for anyone learning how T2 handles a request as an action.
 * To learn this, you should need to become familiar with the following:
 * <ul>
 *   <li>{@link org.t2framework.confeito.action.ActionInvoker}</li>
 *   <li>{@link org.t2framework.confeito.action.ActionFilter}</li>
 *   <li>{@link org.t2framework.confeito.action.PageDescFinder}</li>
 * </ul>
 * 
 * </p>
 * <#else>
 * <p>
 * T2のアクション実行のためのパッケージです.このパッケージはT2のコアの一つです.
 * このパッケージで主要なインタフェースとしては、リクエストされたアクションを実行する、
 * {@link org.t2framework.confeito.action.ActionInvoker}があります.
 * 
 * このパッケージはT2をより学ぶために最初のステップとして適切です.以下のインタフェースから見てみてください.
 * <ul>
 *   <li>{@link org.t2framework.confeito.action.ActionInvoker}</li>
 *   <li>{@link org.t2framework.confeito.action.ActionFilter}</li>
 *   <li>{@link org.t2framework.confeito.action.PageDescFinder}</li>
 * </ul>
 * 
 * </p>
 * </#if>
 * @startuml img/sequence_img001.png
 * ActionInvoker --> ActionContext: create.
 * ActionInvoker --> PageDescFinder: Find List<PageDesc>
 * ActionInvoker --> ActionInvokerFilter: Create and passes ActionFilters.
 * ActionInvokerFilter --> ActionFilter: Invoke each ActionFilter.
 * ActionFilter --> ActionInvokingContext:set result as Navigation
 * ActionInvoker <-- ActionInvokingContext:get result as Navigation
 * note over ActionInvoker
 *   return Navigation to the caller.
 * end note
 * @enduml
 */
package org.t2framework.confeito.action;

