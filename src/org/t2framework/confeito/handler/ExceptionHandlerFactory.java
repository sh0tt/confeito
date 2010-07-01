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

/**
 * <#if locale="en">
 * <p>
 * {@link ExceptionHandlerFactory} is a factory class of
 * {@link ExceptionHandler} and {@link GlobalExceptionHandler}.
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
public interface ExceptionHandlerFactory {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create and return list of {@code ExceptionHandler}.This method must not
	 * return null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return List<ExceptionHandler>
	 */
	List<ExceptionHandler<Throwable, Exception>> createExceptionHandlers();

	/**
	 * <#if locale="en">
	 * <p>
	 * Create and return {@code GlobalExceptionHandler}.This method must not
	 * return null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return GlobalExceptionHandler
	 */
	GlobalExceptionHandler createGlobalExceptionHandler();
}
