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
package org.t2framework.confeito.mock;

import javax.servlet.http.HttpSession;

/**
 * <#if locale="en">
 * <p>
 * Mock class of {@link HttpSession}.
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
public interface MockHttpSession extends HttpSession {

	/**
	 * <#if locale="en">
	 * <p>
	 * True if it is valid.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	boolean isValid();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set valid.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param valid
	 */
	void setValid(boolean valid);

	/**
	 * <#if locale="en">
	 * <p>
	 * Access session.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	void access();
}
