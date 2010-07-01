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

/**
 * 
 * <#if locale="en">
 * <p>
 * {@link FrameworkComponent} interfaces tells the implementation of this
 * interface is executed by T2 framework itself, such as
 * {@link FrameworkComponent#setRequest(Request)}to swap request.
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
public interface FrameworkComponent {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set externally constructed {@link Request}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 */
	void setRequest(Request request);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set externally constructed {@link Response}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param response
	 */
	void setResponse(Response response);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set externally constructed {@link Application}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param application
	 */
	void setApplication(Application application);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set externally constructed {@link Session}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param session
	 */
	void setSession(Session session);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set externally constructed {@link Chain}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param chain
	 */
	void setChain(Chain chain);

}
