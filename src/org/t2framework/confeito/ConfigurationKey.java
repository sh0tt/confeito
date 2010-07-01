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
package org.t2framework.confeito;

import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.spi.impl.MultipartRequestHandlerImpl;

/**
 * <#if locale="en">
 * <p>
 * T2 configuration keys.
 * 
 * </p>
 * <#else>
 * <p>
 * T2の設定項目のキーです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public interface ConfigurationKey {

	/**
	 * <#if locale="en">
	 * <p>
	 * Default encoding is UTF-8.
	 * </p>
	 * <#else>
	 * <p>
	 * デフォルトエンコーディング(UTF-8)です.
	 * </p>
	 * </#if>
	 */
	String DEFAULT_ENCODING = "UTF-8";

	/**
	 * <#if locale="en">
	 * <p>
	 * Root package key for page class.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のルートパッケージ設定のキーです.
	 * </p>
	 * </#if>
	 */
	String ROOT_PACKAGE_KEY = "t2.rootpackage";

	/**
	 * <#if locale="en">
	 * <p>
	 * User external configuration key.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のユーザ設定ファイルのキーです.
	 * </p>
	 * </#if>
	 */
	String USER_CONFIG_KEY = "t2.config";

	/**
	 * <#if locale="en">
	 * <p>
	 * Encoding key.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のエンコーディング設定のキーです.
	 * </p>
	 * </#if>
	 */
	String ENCODING_KEY = "t2.encoding";

	/**
	 * <#if locale="en">
	 * <p>
	 * Exclude resources key T2 does not handle, like css/js.
	 * </p>
	 * <#else>
	 * <p>
	 * T2で処理させないリソース設定のキーです.
	 * </p>
	 * </#if>
	 */
	String EXCLUDE_RESOURCES_KEY = "t2.exclude-resources";

	/**
	 * <#if locale="en">
	 * <p>
	 * ContainerAdapter class key.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のコンテナアダプタ機能設定のキーです.
	 * </p>
	 * </#if>
	 */
	String CONTAINER_ADAPTER_CLASS = "t2.container.adapter";

	/**
	 * <#if locale="en">
	 * <p>
	 * ContainerAdapter class.Default adapter is {@code SimpleContainerAdapter}.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のデフォルトコンテナアダプタ(SimpleContainerAdapter)のクラス名です.
	 * </p>
	 * </#if>
	 */
	String DEFAULT_ADAPTER_CLASS = SimpleContainerAdapter.class.getName();

	/**
	 * <#if locale="en">
	 * <p>
	 * Eager loading page classes key.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のイーガーロードオプション設定のキーです.
	 * </p>
	 * </#if>
	 */
	@Deprecated
	String EAGER_LODE_KEY = "t2.eagerload";

	/**
	 * <#if locale="en">
	 * <p>
	 * Upload max size for MultiPartRequestFilter.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のアップロード最大サイズ設定のキーです.
	 * </p>
	 * </#if>
	 */
	String UPLOAD_MAX_SIZE = "uploadMaxSize";

	/**
	 * <#if locale="en">
	 * <p>
	 * Upload max file size for MultiPartRequestFilter.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のアップロード最大ファイルサイズ設定のキーです.
	 * </p>
	 * </#if>
	 */
	String UPLOAD_MAX_FILE_SIZE = "uploadMaxFileSize";

	/**
	 * <#if locale="en">
	 * <p>
	 * Upload threshold for MultiPartRequestFilter.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のしきい値設定のキーです.
	 * </p>
	 * </#if>
	 */
	String UPLOAD_THRESHOLD_SIZE = "uploadThresholdSize";

	/**
	 * <#if locale="en">
	 * <p>
	 * Path to where upload file persists temporally.It's for
	 * MultiPartRequestFilter.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のアップロード先のパス設定のキーです.
	 * </p>
	 * </#if>
	 */
	String UPLOAD_REPOSITORY_PATH = "uploadRepositoryPath";

	/**
	 * <#if locale="en">
	 * <p>
	 * Multipart request handler.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のマルチパート処理クラス設定のキーです.
	 * </p>
	 * </#if>
	 */
	String MULTIPART_HANDLER_CLASS = "t2.mutipart.handler";

	/**
	 * <#if locale="en">
	 * <p>
	 * default multipart handler class.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のファイルアップロード機能のマルチパート処理クラスのデフォルトです.
	 * </p>
	 * </#if>
	 */
	String DEFAULT_MULTIPART_HANDLER_CLASS = MultipartRequestHandlerImpl.class
			.getName();

	/**
	 * <#if locale="en">
	 * <p>
	 * T2 extension components key.This key works only if user is using
	 * {@link SimpleContainerAdapter} by default.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のフレームワーク拡張コンポーネントを入れ替えるためのキーです.このキーは、デフォルトのT2の状態では ユーザが
	 * {@link SimpleContainerAdapter}
	 * を使っている場合にしか効力を発揮しません.ユーザが独自の拡張をする場合には使用することが出来ます.
	 * </p>
	 * </#if>
	 */
	String COMPONENTS_KEY = "t2.components";

}
