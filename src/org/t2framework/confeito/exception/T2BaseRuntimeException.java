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
import org.t2framework.confeito.util.MessageFormatter;

/**
 * <#if locale="en">
 * <p>
 * T2 base exception.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see BaseRuntimeException
 */
public class T2BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String messageCode;

	protected Object[] args;

	public T2BaseRuntimeException() {
		this("ETDT0001", Constants.EMPTY_ARRAY);
	}

	public T2BaseRuntimeException(String messageCode, Object... args) {
		this(null, messageCode, args);
	}

	public T2BaseRuntimeException(Throwable cause, String messageCode,
			Object... args) {
		super(MessageFormatter.getMessage(messageCode, args), cause);
		this.messageCode = messageCode;
		this.args = args;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public static String getMethodSignature(String methodName,
			Class<?>[] argTypes) {
		StringBuffer buf = new StringBuffer(100);
		buf.append(methodName);
		buf.append("(");
		if (argTypes != null) {
			for (int i = 0; i < argTypes.length; ++i) {
				if (i > 0) {
					buf.append(", ");
				}
				buf.append(argTypes[i].getName());
			}
		}
		buf.append(")");
		return buf.toString();
	}

}
