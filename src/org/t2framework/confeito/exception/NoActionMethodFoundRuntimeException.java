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

import java.util.Set;

import org.t2framework.confeito.util.PrintableUtil;

/**
 * <#if locale="en">
 * <p>
 * NoActionMethodFoundRuntimeException throws when any action method is not
 * found at target class.
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
public class NoActionMethodFoundRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public NoActionMethodFoundRuntimeException(Set<String> methodNames) {
		super("ETDT0003", PrintableUtil.toPrintableString(methodNames));
	}

	public NoActionMethodFoundRuntimeException() {
		super("ETDT0050");
	}

}
