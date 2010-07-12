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

import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.mock.MockHttpServletResponse;
import org.t2framework.confeito.mock.MockPageComponent;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;

public class PageCreationFilterImplTest extends TestCase {

	public void test1_invoke() throws Exception {
		PageCreationFilterImpl filter = new PageCreationFilterImpl();
		MockPageComponent pageDesc = new MockPageComponent(TargetPage.class,
				"target");
		ActionInvokingContext invokingContext = createInvokingContext(pageDesc);
		invokingContext.getContainerAdapter().register(TargetPage.class);
		final boolean[] called = new boolean[] { false };
		filter.invoke(invokingContext, pageDesc, new ActionFilterChain() {

			@Override
			public void invokeChain(ActionInvokingContext context,
					PageComponent pageDesc) {
				called[0] = true;
			}
		});
		assertTrue(called[0]);
	}

	public void test2_noPage() throws Exception {
		PageCreationFilterImpl filter = new PageCreationFilterImpl();
		MockPageComponent pageDesc = new MockPageComponent(TargetPage.class,
				"target");
		ActionInvokingContext invokingContext = createInvokingContext(pageDesc);
		final boolean[] called = new boolean[] { false };
		filter.invoke(invokingContext, pageDesc, new ActionFilterChain() {

			@Override
			public void invokeChain(ActionInvokingContext context,
					PageComponent pageDesc) {
				called[0] = true;
			}
		});
		assertFalse(called[0]);
		assertEquals(ActionStatus.NO_PAGE, invokingContext.getActionStatus());
		MockHttpServletResponse res = (MockHttpServletResponse) invokingContext
				.getWebContext().getResponse().getNativeResource();
		assertTrue(res.getStatus() == 404);
	}

	protected ActionInvokingContext createInvokingContext(
			final MockPageComponent pageDesc) {
		MockWebContext webContext = MockWebContext.createMock("/hoge");
		webContext.createAndSetMockActionContext();
		SimpleContainerAdapter containerAdapter = new SimpleContainerAdapter();
		containerAdapter.init();
		ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
				webContext, new DefaultAnnotationResolverCreatorImpl(),
				containerAdapter, new PluginProcessor(containerAdapter)) {

			@Override
			public PageComponent getCurrentPageDesc() {
				return pageDesc;
			}

		};
		return invokingContext;
	}

	public static class TargetPage {
		@Default
		public Navigation index() {
			return null;
		}
	}

	public static class TargetPage2 {
	}

}
