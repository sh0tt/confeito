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

import junit.framework.TestCase;

import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.mock.MockActionContextImpl;
import org.t2framework.confeito.mock.MockActionFilterChainImpl;
import org.t2framework.confeito.mock.MockActionInvokingContextImpl;
import org.t2framework.confeito.mock.MockPageComponent;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.navigation.NoOperation;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;
import org.t2framework.confeito.util.Reflections.MethodUtil;

public class ActionArgumentsPreparationFilterImplTest extends TestCase {

	public void test1() throws Exception {
		ActionArgumentsPreparationFilterImpl filter = new ActionArgumentsPreparationFilterImpl();
		MockWebContext context = MockWebContext.createMock("/hoge");
		MockActionContextImpl ac = new MockActionContextImpl(context);
		ac.setTargetMethodDesc(MethodUtil.getDeclaredMethod(
				Target1Page.class, "hoge", new Class[] { Request.class }));
		context.setActionContext(ac);
		context.containerAdapter(new SimpleContainerAdapter());
		MockActionInvokingContextImpl invokingContext = new MockActionInvokingContextImpl(
				context);
		invokingContext
				.setResolverCreator(new DefaultAnnotationResolverCreatorImpl());
		MockActionFilterChainImpl chain = new MockActionFilterChainImpl();
		filter.invoke(invokingContext, new MockPageComponent(Target1Page.class,
				"/hoge"), chain);
		assertTrue(chain.getCalled().get());
		Object[] args = invokingContext.getActionArguments();
		assertNotNull(args);
		assertTrue(args.length == 1);
		assertTrue(Request.class.isAssignableFrom(args[0].getClass()));
	}

	public static class Target1Page {

		public Navigation hoge(Request request) {
			return NoOperation.INSTANCE;
		}
	}
}
