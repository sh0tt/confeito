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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.ExceptionHandlerProcessingRuntimeException;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * GlobalExceptionHandler is the exception handler class that is handling
 * exception at the last moment of processing request.
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
public class GlobalExceptionHandler implements
		ExceptionHandler<Throwable, ServletException> {

	private static Logger logger = Logger
			.getLogger(GlobalExceptionHandler.class.getName());

	/**
	 * <#if locale="en">
	 * <p>
	 * True if the given {@link Throwable} is target for this handler.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isTargetException(Throwable t) {
		return t != null;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Just logging and wraps to ServletException by default.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Navigation handleException(final Throwable t,
			final WebContext context, final Navigation result)
			throws ServletException {
		Throwable target = unwrap(t);
		logger.log(Level.SEVERE, t.getMessage());
		throw new ServletException(target);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Unwrap if the {@link Throwable} is
	 * {@link ExceptionHandlerProcessingRuntimeException}.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param t
	 * @return
	 */
	protected Throwable unwrap(Throwable t) {
		Throwable ret = t;
		if (t instanceof ExceptionHandlerProcessingRuntimeException) {
			ret = t.getCause();
		}
		return ret;
	}

}
