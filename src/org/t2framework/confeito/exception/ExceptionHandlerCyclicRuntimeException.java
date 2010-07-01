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

import org.t2framework.confeito.Constants;

/**
 * 
 * <#if locale="en">
 * <p>
 * Exception throws when
 * {@link org.t2framework.confeito.handler.ExceptionHandlerChain} handles same
 * exceptions cyclically.
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.handler.ExceptionHandlerChain
 */
public class ExceptionHandlerCyclicRuntimeException extends
		T2InternalRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * <#if locale="en">
	 * <p>
	 * TODO error message.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param cause
	 */
	public ExceptionHandlerCyclicRuntimeException(Throwable cause) {
		super(cause, "ETDA0001", Constants.EMPTY_ARRAY);
	}

}
