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
 * <#if locale="en">
 * <p>
 * IllegalAccessRuntimeException is an exception class wrapping
 * IllegalAccessException.
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
public class IllegalAccessRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	private Class<?> targetClass;

	/**
	 * <#if locale="en">
	 * <p>
	 * This constructor is for Field and Method reflections.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param declaringClass
	 * @param type
	 * @param cause
	 */
	public IllegalAccessRuntimeException(Class<?> declaringClass,
			Class<?> type, IllegalAccessException cause) {
		super(cause, "ECMN0030", declaringClass.getName(), type.getName(),
				cause.getMessage());
	}

	public IllegalAccessRuntimeException(Class<?> targetClass,
			IllegalAccessException cause) {
		this(targetClass, cause, Constants.EMPTY_ARRAY);
	}

	public IllegalAccessRuntimeException(Class<?> targetClass,
			IllegalAccessException cause, Object... args) {
		super(cause, "ECMN0003", targetClass.getName(), cause.getMessage(),
				args);
		this.targetClass = targetClass;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}
}
