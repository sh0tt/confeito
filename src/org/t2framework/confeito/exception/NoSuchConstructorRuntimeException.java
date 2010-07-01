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

import org.t2framework.confeito.util.Reflections.ClassUtil;

public class NoSuchConstructorRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	private Class<?>[] argTypes;

	public NoSuchConstructorRuntimeException(Throwable cause, Class<?> clazz) {
		super(cause, "ECMN0019", clazz.getName());
	}

	public NoSuchConstructorRuntimeException(Class<?> targetClass,
			Class<?>[] argTypes, NoSuchMethodException cause) {
		super(cause, "ECMN0020", getArgs(targetClass, argTypes, cause));
		this.argTypes = argTypes;
	}

	private static Object[] getArgs(Class<?> targetClass, Class<?>[] argTypes,
			Throwable cause) {
		return new Object[] {
				targetClass.getName(),
				T2BaseRuntimeException.getMethodSignature(ClassUtil
						.getShortClassName(targetClass), argTypes), cause };
	}

	public Class<?>[] getArgTypes() {
		return argTypes;
	}

}
