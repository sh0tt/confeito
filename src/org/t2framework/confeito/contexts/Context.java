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

import java.util.Locale;

/**
 * <#if locale="en">
 * <p>
 * The root context class that has all information you need.
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
public interface Context<REQ, RES, APP, SES> {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get request.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return request as parameterized REQ
	 */
	REQ getRequest();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get response.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return response as parameterized RES
	 */
	RES getResponse();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get application.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return application as parameterized APP
	 */
	APP getApplication();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get session.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return session as parameterized SES
	 */
	SES getSession();

	/**
	 * <#if locale="en">
	 * <p>
	 * Return requested locale.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return requested locale
	 */
	Locale getRequestLocale();

	/**
	 * <#if locale="en">
	 * <p>
	 * Return the locale where T2 is running.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return server side locale
	 */
	Locale getDefaultLocale();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get chain.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return chain
	 */
	Chain getChain();
}
