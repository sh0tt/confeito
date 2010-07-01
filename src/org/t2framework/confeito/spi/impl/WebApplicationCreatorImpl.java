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
package org.t2framework.confeito.spi.impl;

import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.impl.WebApplicationImpl;
import org.t2framework.confeito.spi.WebApplicationCreator;

/**
 * <#if locale="en">
 * <p>
 * A default implementation of {@code WebApplicationCreator}.
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
public class WebApplicationCreatorImpl implements WebApplicationCreator {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@code WebApplicationImpl}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public WebApplication createWebApplication(WebConfiguration webconf) {
		return new WebApplicationImpl(webconf);
	}

}
