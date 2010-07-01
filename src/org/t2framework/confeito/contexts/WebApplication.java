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
package org.t2framework.confeito.contexts;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.urltemplate.UrlTemplate;

/**
 * 
 * <#if locale="en">
 * <p>
 * WebApplication is a factory interface to create context object. This class
 * has responsibility to create meta object at the initialized time for POJO
 * instance called Page, which is an object to communicate with T2, and keeps as
 * map<String, PageDesc> for the time of invoking appropriate method of a
 * page.The scope of WebApplication is same as T2Filter.
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
public interface WebApplication {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Initialize web application context.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebApplication}を初期化します.．
	 * </p>
	 * </#if>
	 * 
	 */
	void initialize();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Create {@code WebContext}, the root context object for T2.
	 * </p>
	 * <#else>
	 * <p>
	 * コンテキスト情報の大元である{@code WebContext}を作成します．
	 * </p>
	 * </#if>
	 * 
	 * @param req
	 * @param res
	 * @param filterChain
	 * @param config
	 * @return web context
	 */
	WebContext createContext(HttpServletRequest req, HttpServletResponse res,
			FilterChain filterChain, FilterConfig config);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Create map of {@code PageDesc}.This map has basic information for each
	 * page object.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return map of created {@link PageComponent}
	 */
	Map<String, PageComponent> createPageDescMap();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get map of page class and urltemplate cache.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	Map<Class<?>, UrlTemplate> getClassCache();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get root packages that is configured at web.xml.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return root packages
	 */
	String[] getRootPackages();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Destroy any resources that {@link WebApplication} may have.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link WebApplication}が持つリソースを破棄します．
	 * </p>
	 * </#if>
	 * 
	 */
	void destroy();

}
