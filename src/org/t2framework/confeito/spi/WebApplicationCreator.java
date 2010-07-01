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

import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;

/**
 * <#if locale="en">
 * <p>
 * SPI point for creating context, such as {@link WebApplication},
 * {@link Request}, and so on.
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
public interface WebApplicationCreator {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@link WebApplication}.The {@link WebConfiguration} must not be
	 * null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param configuration
	 * @return {@link WebApplication} instance
	 */
	WebApplication createWebApplication(WebConfiguration configuration);
}
