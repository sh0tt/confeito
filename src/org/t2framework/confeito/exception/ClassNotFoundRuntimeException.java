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
 * A runtime exception for ClassNotFoundException.
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
public class ClassNotFoundRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = -9022468864937761059L;

	private String className;

	public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
		this(null, cause);
	}

	public ClassNotFoundRuntimeException(String className,
			ClassNotFoundException cause) {
		this(className, cause, Constants.EMPTY_ARRAY);
	}

	public ClassNotFoundRuntimeException(String className,
			ClassNotFoundException cause, Object... args) {
		super("ECMN0005", (className != null) ? className : "", cause, args);
		setClassName(className);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
