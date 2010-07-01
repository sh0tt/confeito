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
package org.t2framework.confeito.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * <#if locale="en">
 * <p>
 * Resource bundle utility.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ResourceBundleUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} with caching.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @return
	 */
	public static ResourceBundle getBundle(String bundleName) {
		return getBundle(bundleName, Locale.getDefault(), Thread
				.currentThread().getContextClassLoader());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} with caching.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param loader
	 * @return
	 */
	public static ResourceBundle getBundle(String bundleName, ClassLoader loader) {
		return getBundle(bundleName, Locale.getDefault(), loader);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} with caching.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getBundle(String bundleName, Locale locale) {
		return getBundle(bundleName, locale, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} with caching.If
	 * {@link MissingResourceException} throws, simply return null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param locale
	 * @param loader
	 * @return
	 */
	public static ResourceBundle getBundle(String bundleName, Locale locale,
			ClassLoader loader) {
		try {
			return ResourceBundle.getBundle(bundleName, locale, loader);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} without caching.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @return
	 */
	public static ResourceBundle getBundleNoCache(String bundleName) {
		return getBundleNoCache(bundleName, Locale.getDefault(), Thread
				.currentThread().getContextClassLoader());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} without caching.
	 * 
	 * TODO : this method does not work with IBM V9. It says:
	 * 
	 * <pre>
	 * java.lang.ClassCastException: org.t2framework.commons.util.StringLoaderTest incompatible with java.util.ResourceBundle
	 * 	at java.util.ResourceBundle$Control.newBundle(Unknown Source)
	 *  at java.util.ResourceBundle.processGetBundle(Unknown Source)
	 *  at java.util.ResourceBundle.getBundle(Unknown Source)
	 *  at org.t2framework.commons.util.ResourceBundleUtil.getBundleNoCache(ResourceBundleUtil.java:74)
	 *  at org.t2framework.commons.util.ResourceBundleUtil.getBundleNoCache(ResourceBundleUtil.java:36)
	 *  at org.t2framework.commons.util.StringLoader.getBundle(StringLoader.java:58)
	 * </pre>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param loader
	 * @return
	 */
	public static ResourceBundle getBundleNoCache(String bundleName,
			ClassLoader loader) {
		return getBundleNoCache(bundleName, Locale.getDefault(), loader);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} without caching.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getBundleNoCache(String bundleName,
			Locale locale) {
		return getBundleNoCache(bundleName, locale, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link ResourceBundle} without caching.If
	 * {@link MissingResourceException} throws, simply return null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param bundleName
	 * @param locale
	 * @param loader
	 * @return
	 */
	public static ResourceBundle getBundleNoCache(String bundleName,
			Locale locale, ClassLoader loader) {
		try {
			return ResourceBundle.getBundle(bundleName, locale, loader,
					new ResourceBundle.Control() {

						@Override
						public long getTimeToLive(String baseName, Locale locale) {
							return Control.TTL_DONT_CACHE;
						}

					});
		} catch (MissingResourceException e) {
			return null;
		}
	}

}
