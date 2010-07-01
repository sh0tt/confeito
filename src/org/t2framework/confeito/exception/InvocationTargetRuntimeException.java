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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;

import org.t2framework.confeito.Constants;

public class InvocationTargetRuntimeException extends T2BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	protected Member member;

	public InvocationTargetRuntimeException(Member member,
			InvocationTargetException e) {
		this(member, e, Constants.EMPTY_ARRAY);
	}

	public InvocationTargetRuntimeException(Member member,
			InvocationTargetException cause, Object... args) {
		super(cause.getTargetException(), "ECMN0004", member, cause
				.getTargetException(), args);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

}
