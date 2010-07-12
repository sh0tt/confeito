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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.ActionMethodInvocationException;
import org.t2framework.confeito.exception.ExceptionHandlerProcessingRuntimeException;
import org.t2framework.confeito.handler.ExceptionHandler;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;
import org.t2framework.confeito.urltemplate.UrlTemplate;

public class ExceptionHandlerActionFilterImplTest extends TestCase {

	private static AnnotationResolverCreator creator = new DefaultAnnotationResolverCreatorImpl();

	protected ActionInvokingContext createInvokingContext(WebContext context,
			ContainerAdapter<?> containerAdapter) {
		return new ActionInvokingContextImpl(context, creator,
				containerAdapter, new PluginProcessor(containerAdapter));
	}

	public void test1_actoinMethodInvocationExceptionShouldBeUnrapped()
			throws Exception {
		ExceptionHandlerActionFilterImpl filter = new ExceptionHandlerActionFilterImpl();
		Set<Class<? extends Annotation>> set = Collections.emptySet();
		PageComponent pageDesc = new PageComponent(Hoeeee.class,
				new UrlTemplate("hoee"), set);

		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/invoke/hoge.html");
		context.createAndSetMockActionContext();

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();

		ActionInvokingContext invokingContext = createInvokingContext(context,
				adapter);

		try {
			filter.invoke(invokingContext, pageDesc, new ActionFilterChain() {

				@Override
				public void invokeChain(ActionInvokingContext context,
						PageComponent pageDesc) {
					throw new ActionMethodInvocationException(
							new NullPointerException());
				}
			});
			fail();
		} catch (NullPointerException expected) {
		}
		assertTrue(invokingContext.getActionStatus() == ActionStatus.ERROR);
	}

	public void test2_exceptionHandlerFailToHandle() throws Exception {
		ExceptionHandlerActionFilterImpl filter = new ExceptionHandlerActionFilterImpl();
		Set<Class<? extends Annotation>> set = Collections.emptySet();
		PageComponent pageDesc = new PageComponent(Hoeeee.class,
				new UrlTemplate("hoee"), set);

		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/invoke/hoge.html");
		context.createAndSetMockActionContext();

		SimpleContainerAdapter adapter = new SimpleContainerAdapter() {

			@SuppressWarnings("rawtypes")
			@Override
			public List<ExceptionHandler<Throwable, Exception>> createExceptionHandlers() {
				ExceptionHandler handler = new ExceptionHandler<RuntimeException, Exception>() {

					@Override
					public Navigation handleException(RuntimeException t,
							WebContext context, Navigation result)
							throws Exception {
						throw new Exception("hoge");
					}

					@Override
					public boolean isTargetException(Throwable t) {
						return true;
					}
				};
				List<ExceptionHandler<Throwable, Exception>> handlers = new ArrayList<ExceptionHandler<Throwable, Exception>>();
				handlers.add(handler);
				return handlers;
			}

		};
		adapter.init();

		ActionInvokingContext invokingContext = createInvokingContext(context,
				adapter);

		try {
			filter.invoke(invokingContext, pageDesc, new ActionFilterChain() {

				@Override
				public void invokeChain(ActionInvokingContext context,
						PageComponent pageDesc) {
					throw new RuntimeException();
				}
			});
			fail();
		} catch (ExceptionHandlerProcessingRuntimeException expected) {
		}
		assertTrue(invokingContext.getActionStatus() == ActionStatus.ERROR);
	}

	@Page
	public static class Hoeeee {

	}
}
