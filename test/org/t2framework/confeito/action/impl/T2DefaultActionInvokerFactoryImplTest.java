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
package org.t2framework.confeito.action.impl;

import java.util.List;

import junit.framework.TestCase;

import org.t2framework.confeito.action.ActionInvoker;
import org.t2framework.confeito.action.ActionInvokerFactory;
import org.t2framework.confeito.mock.NullWebConfiguration;

public class T2DefaultActionInvokerFactoryImplTest extends TestCase {

	public void testCreate() throws Exception {
		ActionInvokerFactory factory = new ActionInvokerFactoryImpl();
		List<? extends ActionInvoker> actionInvokers = factory
				.createActionInvoker(new NullWebConfiguration());
		assertNotNull(actionInvokers);
		assertTrue(actionInvokers.isEmpty() == false);
		assertTrue(actionInvokers.get(0).getClass() == ActionInvokerImpl.class);
	}
}
