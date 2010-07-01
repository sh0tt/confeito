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
 * The root package for T2 framework.
 * To learn T2 framework itself, the packages below is something you should look at.
 * 
 * <ul>
 * <li>org.t2framework.t2.action</li>
 * 	<ul><li>The core of T2: handle request, match url to managed POJO, invoke the target action method.</li></ul>
 * <li>org.t2framework.t2.contexts</li>
 * 	<ul><li>The package for context objects, such as request, response, session.</li></ul>
 * <li>org.t2framework.t2.development</li>
 * 	<ul><li>The package for development utility.</li></ul>
 * <li>org.t2framework.t2.filter</li>
 * 	<ul><li>The package for entry point of T2 framework.</li></ul>
 * <li>org.t2framework.t2.format</li>
 * 	<ul><li>The package for format object such as AMF or SOAP.</li></ul>
 * <li>org.t2framework.t2.navigation</li>
 * 	<ul><li>The package for {@link org.t2framework.confeito.spi.Navigation}, the user interface of T2 framework.</li></ul>
 * <li>org.t2framework.t2.plugin</li>
 * 	<ul><li>The package for user extension plugin.</li></ul>
 * <li>org.t2framework.t2.spi</li>
 * 	<ul><li>The package for SPI interface.</li></ul>
 * </ul>
 * 
 * </p>
 * <#else>
 * <p>
 * T2フレームワークのルートパッケージです.
 * T2をより良く知るには以下のパッケージを参照してみてください.
 * 
 * <ul>
 * <li>org.t2framework.t2.action</li>
 * 	<ul><li>T2のコア部分です.リクエストを処理するクラスとメソッドを特定し、実行します.</li></ul>
 * <li>org.t2framework.t2.contexts</li>
 * 	<ul><li>T2のコンテキストオブジェクト、例えばリクエストやページ情報など、があります.</li></ul>
 * <li>org.t2framework.t2.development</li>
 * 	<ul><li>T2の開発モードとそれに関するクラスがあります.</li></ul>
 * <li>org.t2framework.t2.filter</li>
 * 	<ul><li>T2のエントリポイントであるフィルタがあります.</li></ul>
 * <li>org.t2framework.t2.format</li>
 * 	<ul><li>AMFやSOAPなどのフォーマットを解釈するクラスがあります.</li></ul>
 * <li>org.t2framework.t2.navigation</li>
 * 	<ul><li>T2のユーザインタフェースの一つである、{@link org.t2framework.confeito.spi.Navigation}の実装クラスがあります.</li></ul>
 * <li>org.t2framework.t2.plugin</li>
 * 	<ul><li>プラグイン機構があります.</li></ul>
 * <li>org.t2framework.t2.spi</li>
 * 	<ul><li>T2の拡張インタフェースとデフォルト実装があります.</li></ul>
 * </ul>
 * </p>
 * </#if>
 * 
 */
package org.t2framework.confeito;

