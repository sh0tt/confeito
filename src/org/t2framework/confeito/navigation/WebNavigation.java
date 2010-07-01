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
package org.t2framework.confeito.navigation;

import org.t2framework.confeito.HttpVersion;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Normal web response base class.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 */
public abstract class WebNavigation<T extends WebNavigation<T>> implements
		Navigation {

	/**
	 * HTTP version that this Navigation supports. Default is HTTP 1.0
	 * compatible.
	 * 
	 * @since 0.6.3
	 */
	protected HttpVersion httpVersion = HttpVersion.HTTP10;

	/**
	 * <#if locale="en">
	 * <p>
	 * Class for navigation.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected Class<?> pageClass;

	/**
	 * <#if locale="en">
	 * <p>
	 * Path for navigation.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected String path;

	public WebNavigation() {
	}

	/**
	 * Construct this {@link WebNavigation}.
	 * 
	 * @param path
	 */
	public WebNavigation(String path) {
		this.path = Assertion.notNull(path);
	}

	public WebNavigation(Class<?> pageClass) {
		this.pageClass = Assertion.notNull(pageClass);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get page class.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return page class
	 */
	public Class<?> getPageClass() {
		return pageClass;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set page class.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param pageClass
	 */
	public void setPageClass(Class<?> pageClass) {
		this.pageClass = pageClass;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get path.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set path.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Set HTTP version for change behavior of navigation.
	 * 
	 * @param httpVersion
	 * @return
	 * @since 0.6.3
	 */
	public abstract T setHttpVersion(HttpVersion httpVersion);

}
