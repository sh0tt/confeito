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

public class IORuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * TODO ID and message.
	 * 
	 * @param cause
	 */
	public IORuntimeException(Throwable cause) {
		super(cause, "ECMN0009", cause);
	}

	protected IORuntimeException(Throwable cause, String messageCode,
			Object... args) {
		super(cause, messageCode, args);
	}
}
