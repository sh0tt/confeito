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
package org.t2framework.confeito.handler;

import java.util.List;

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.ExceptionHandlerCyclicRuntimeException;
import org.t2framework.confeito.exception.ExceptionHandlerProcessingRuntimeException;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * {@link ExceptionHandlerChain} is chain class for {@link ExceptionHandler}.
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
public class ExceptionHandlerChain {

	/**
	 * <#if locale="en">
	 * <p>
	 * List of all {@link ExceptionHandler}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected final List<ExceptionHandler<Throwable, Exception>> exceptionHandlers;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this object.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param exceptionHandlers
	 */
	public ExceptionHandlerChain(
			List<ExceptionHandler<Throwable, Exception>> exceptionHandlers) {
		this.exceptionHandlers = Assertion.notNull(exceptionHandlers);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Processing {@link ExceptionHandler}.The given {@link Throwable} matches
	 * to process, the {@link ExceptionHandler} handles and return
	 * {@link Navigation} to notify how the result is like and where the result
	 * go.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param t
	 * @param context
	 * @return navigation to be executed
	 * @throws {@link ExceptionHandlerProcessingRuntimeException}
	 * @throws {@link ExceptionHandlerCyclicRuntimeException}
	 */
	public Navigation doChain(final Throwable t, final WebContext context,
			final Navigation result) {
		Throwable target = t;
		Navigation n = null;
		Exception cyclic = null;
		for (ExceptionHandler<Throwable, Exception> handler : exceptionHandlers) {
			if (handler.isTargetException(target) == false) {
				continue;
			}
			try {
				n = handler.handleException(t, context, result);
			} catch (Exception e) {
				if (e.equals(cyclic)) {
					throw new ExceptionHandlerCyclicRuntimeException(target);
				}
				target = e;
				cyclic = e;
				n = null;
				continue;
			}
			if (n != null) {
				break;
			}
		}
		if (n == null && target != t) {
			throw new ExceptionHandlerProcessingRuntimeException(target);
		}
		return n;
	}

}
