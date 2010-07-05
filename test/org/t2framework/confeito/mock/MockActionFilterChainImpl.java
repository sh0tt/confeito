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
package org.t2framework.confeito.mock;

import java.util.concurrent.atomic.AtomicBoolean;

import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.impl.ActionFilterChainImpl;
import org.t2framework.confeito.model.PageComponent;

public class MockActionFilterChainImpl extends ActionFilterChainImpl {

	protected AtomicBoolean called = new AtomicBoolean(false);

	public MockActionFilterChainImpl(ActionFilter... filters) {
		super(filters);
	}

	@Override
	public void invokeChain(ActionInvokingContext invokingContext,
			PageComponent pageDesc) {
		super.invokeChain(invokingContext, pageDesc);
		called.set(true);
	}

	public AtomicBoolean getCalled() {
		return called;
	}

}
