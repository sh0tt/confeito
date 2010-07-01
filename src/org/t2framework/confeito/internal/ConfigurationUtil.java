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
package org.t2framework.confeito.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterConfig;

import org.t2framework.confeito.ConfigurationKey;
import org.t2framework.confeito.Constants;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.spi.MultipartRequestHandler;
import org.t2framework.confeito.util.StringUtil;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * <#if locale="en">
 * <p>
 * T2 configuration utility.
 * </p>
 * <#else>
 * <p>
 * T2の設定関連のユーティリティクラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class ConfigurationUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * T2 eager load option default value.
	 * </p>
	 * <#else>
	 * <p>
	 * T2のイーガーロードオプションのデフォルト値です.
	 * </p>
	 * </#if>
	 */
	public static final boolean EAGER_LOAD_DEFAULT_VALUE = false;

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ContainerAdapter} class name from web.xml filter-config init
	 * parameter. If there is not specified,
	 * {@link ConfigurationKey#DEFAULT_ADAPTER_CLASS} is used as default.
	 * 
	 * The specified string must be class name of {@link ContainerAdapter}
	 * implementation.
	 * </p>
	 * <#else>
	 * <p>
	 * コンテナアダプタのクラス名を取得します.もし指定されない場合、
	 * {@link ConfigurationKey#DEFAULT_ADAPTER_CLASS}で指定されているクラスが使われます.
	 * 
	 * また、指定するクラスは{@link ContainerAdapter}インタフェースの実装クラスである必要があります.
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return container adapter class name
	 * @see org.t2framework.confeito.adapter.ContainerAdapter
	 */
	public static String getAdapterClassName(final FilterConfig config) {
		String adapterClassName = config
				.getInitParameter(ConfigurationKey.CONTAINER_ADAPTER_CLASS);
		if (StringUtil.isEmpty(adapterClassName)) {
			adapterClassName = ConfigurationKey.DEFAULT_ADAPTER_CLASS;
		}
		return adapterClassName;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * 
	 * Get encoding from web.xml servlet context init parameter.If not
	 * specified, {@link ConfigurationKey#DEFAULT_ENCODING} is used as default.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return encoding
	 */
	public static String getEncoding(final FilterConfig config) {
		String encoding = config.getServletContext().getInitParameter(
				ConfigurationKey.ENCODING_KEY);
		if (encoding == null) {
			encoding = ConfigurationKey.DEFAULT_ENCODING;
		}
		return encoding;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get user config from web.xml filter config init parameter.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return user configuration path
	 */
	public static String getUserConfig(FilterConfig config) {
		return config.getInitParameter(ConfigurationKey.USER_CONFIG_KEY);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get T2 page instances root package names from web.xml filter init
	 * parameter.This is must-be-configured one to use T2 framework.
	 * 
	 * The root packages configures multiple items like {@literal hoge.foo,
	 * moge.bar}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return root packages
	 */
	public static String[] getRootPackages(final FilterConfig config) {
		String roots = config
				.getInitParameter(ConfigurationKey.ROOT_PACKAGE_KEY);
		if (StringUtil.isEmpty(roots)) {
			roots = "";
		}
		return split(roots);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get exclude resources from web.xml filter init parameter.Exclude
	 * resources means these resources is not handled by T2 framework.You can
	 * specify multiple items like {@literal .gif, .css, .png}.If none
	 * specified, all requests is handled by T2 framework.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return exclude resources name
	 */
	public static String[] getExcludeResources(final FilterConfig config) {
		String s = config
				.getInitParameter(ConfigurationKey.EXCLUDE_RESOURCES_KEY);
		if (StringUtil.isEmpty(s)) {
			return Constants.EMPTY_STRING_ARRAY;
		}
		return split(s);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get eager load option from web.xml filter init parameter.Eager load is
	 * the feature which is dynamically register component if components is
	 * found by {@link WebApplication#initialize()}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return eager load option
	 */
	@Deprecated
	public static boolean getEagerLoad(final FilterConfig config) {
		String eagerLoad = config
				.getInitParameter(ConfigurationKey.EAGER_LODE_KEY);
		if (StringUtil.isEmpty(eagerLoad)) {
			return EAGER_LOAD_DEFAULT_VALUE;
		}
		return Boolean.valueOf(eagerLoad);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link MultipartRequestHandler} from web.xml filter configuration
	 * initialize parameter.If none specified,
	 * {@link ConfigurationKey#DEFAULT_MULTIPART_HANDLER_CLASS} is used to
	 * create instance.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return multipart request handler instance
	 */
	public static MultipartRequestHandler getMultipartRequestHandler(
			final FilterConfig config) {
		String handlerClass = config
				.getInitParameter(ConfigurationKey.MULTIPART_HANDLER_CLASS);
		if (StringUtil.isEmpty(handlerClass)) {
			handlerClass = ConfigurationKey.DEFAULT_MULTIPART_HANDLER_CLASS;
		}
		return ClassUtil.newInstanceByName(handlerClass);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get T2 extension classses from web.xml definition.This method does not
	 * return null, instead return empty list.
	 * 
	 * About extension classes which may return from this method, it must have
	 * default constructor for creating instance, especially for
	 * {@link SimpleContainerAdapter}
	 * </p>
	 * <#else>
	 * <p>
	 * web.xmlに指定されたT2の拡張モジュールのクラスのリストを返します.このメソッドはnullを返すことはありません.
	 * 
	 * このメソッドが返す拡張クラスはデフォルトコンストラクタを持たねばなりません.{@link SimpleContainerAdapter}
	 * を使う場合などはその必要があります.
	 * </p>
	 * </#if>
	 * 
	 * @param config
	 * @return
	 */
	public static List<Class<?>> getExtensionClasses(final FilterConfig config) {
		if (config == null) {
			return Collections.emptyList();
		}
		final String s = config
				.getInitParameter(ConfigurationKey.COMPONENTS_KEY);
		if (StringUtil.isEmpty(s)) {
			return Collections.emptyList();
		} else {
			List<Class<?>> ret = new ArrayList<Class<?>>();
			String[] array = StringUtil.split(s, ", ");
			for (String componentStr : array) {
				final Class<?> c = ClassUtil.forName(componentStr);
				ret.add(c);
			}
			return ret;
		}
	}

	protected static String[] split(String s) {
		return StringUtil.split(s, ", ");
	}

}
