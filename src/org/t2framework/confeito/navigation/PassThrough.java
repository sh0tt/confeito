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

import org.t2framework.confeito.contexts.Chain;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * Pass through response.
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
public class PassThrough implements Navigation {

	/**
	 * <#if locale="en">
	 * <p>
	 * Singleton instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static PassThrough INSTANCE = new PassThrough();

	public static PassThrough pass() {
		return INSTANCE;
	}

	public PassThrough() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke {@link Chain#doFilter(WebContext)}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		context.getChain().doFilter(context);
	}

}
