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

public class NoSuchMethodRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	private String methodName;

	private Class<?>[] argTypes;

	public NoSuchMethodRuntimeException(NoSuchMethodException cause,
			Class<?> clazz, String methodName) {
		super(cause, "ECMN0015", cause, clazz.getName(), methodName);
	}

	public NoSuchMethodRuntimeException(String methodName) {
		super("ECMN0016", methodName);
	}

	public NoSuchMethodRuntimeException(Class<?> targetClass,
			String methodName, Class<?>[] argTypes, NoSuchMethodException cause) {
		super(cause, "ECMN0017",
				new Object[] {
						cause,
						targetClass.getName(),
						methodName,
						T2BaseRuntimeException.getMethodSignature(methodName,
								argTypes) });
		this.methodName = methodName;
		this.argTypes = argTypes;
	}

	public String getMethodName() {
		return methodName;
	}

	public Class<?>[] getArgTypes() {
		return argTypes;
	}

}
