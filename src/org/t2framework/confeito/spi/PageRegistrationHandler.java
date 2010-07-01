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
package org.t2framework.confeito.spi;

import java.util.Map;

import org.t2framework.confeito.urltemplate.UrlTemplate;

/**
 * 
 * <#if locale="en">
 * <p>
 * Routing handler for registration classes.
 * 
 * </p>
 * <#else>
 * <p>
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public interface PageRegistrationHandler {

	/**
	 * Invoke page registration process.There are several registration prorcess
	 * accepted.It may use package name as the basic point of directory
	 * traversal, or package name may just the key of configuration file as
	 * well.
	 * 
	 * @param packageName
	 * @param classCache
	 * @return PageRegistrationCommand
	 */
	PageRegistrationCommand handle(String packageName,
			Map<Class<?>, UrlTemplate> classCache);

}
