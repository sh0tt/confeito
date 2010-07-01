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

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * 
 * ExceptionHandler is an interface that handles an Exception.
 * {@link ExceptionHandler} checks if the {@link Throwable} is target or not,
 * and then handle exception and returns appropriate {@link Navigation} to let
 * T2 know where to navigate.
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
 * @param <T>
 *            as parameter exception.
 * @param <R>
 *            as return exception if needed.
 */
public interface ExceptionHandler<T extends Throwable, R extends Exception> {

	/**
	 * <#if locale="en">
	 * <p>
	 * Check if the {@link Throwable} is target for this handler.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param t
	 * @return true {@link Throwable} is target for this handler, otherwise
	 *         false
	 */
	boolean isTargetException(Throwable t);

	/**
	 * <#if locale="en">
	 * <p>
	 * Handle exception and returns appropriate {@link Navigation} for next
	 * process ot throw appropriate exception.
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
	 * @return navigation
	 * @throws R
	 */
	Navigation handleException(T t, WebContext context, Navigation result)
			throws R;

}
