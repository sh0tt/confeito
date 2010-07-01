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
package org.t2framework.confeito.exception;

import org.t2framework.confeito.handler.ExceptionHandlerChain;

/**
 * <#if locale="en">
 * <p>
 * {@link ActionMethodInvocationException} is internal runtime exception class
 * to notify {@link ExceptionHandlerChain} that there is an exception occurred
 * at action invoking.
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
public class ActionMethodInvocationException extends T2BaseRuntimeException {

	/**
	 * <#if locale="en">
	 * <p>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	private static final long serialVersionUID = 1L;

	protected Throwable t;

	public ActionMethodInvocationException(Throwable t) {
		this.t = t;
	}

	@Override
	public Throwable getCause() {
		return t;
	}

}
