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
import org.t2framework.confeito.contexts.WebContext;

/**
 * <#if locale="en">
 * <p>
 * No operation response.
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
public class NoOperation extends WebNavigation<NoOperation> {

	/**
	 * <#if locale="en">
	 * <p>
	 * singleton instance for this navigation.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static final NoOperation INSTANCE = new NoOperation();

	public static NoOperation noOp() {
		return INSTANCE;
	}

	@Override
	public void execute(WebContext context) throws Exception {
		// no op;
	}

	@Override
	public NoOperation setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		return this;
	}

}
