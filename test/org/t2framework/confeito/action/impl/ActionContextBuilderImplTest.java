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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

import junit.framework.TestCase;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.exception.NoDefaultActionMethodFoundRuntimeException;
import org.t2framework.confeito.mock.MockPageComponent;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;
import org.t2framework.confeito.util.Reflections.MethodUtil;

public class ActionContextBuilderImplTest extends TestCase {

	private AnnotationResolverCreator creator = new DefaultAnnotationResolverCreatorImpl();

	private SimpleContainerAdapter containerAdapter = new SimpleContainerAdapter();

	public void testBuild1_defaultOnly() throws Exception {
		MockWebContext mock = MockWebContext.createMock("/hoge");

		ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
				mock, creator, containerAdapter, null);
		PageComponent pageDesc = new MockPageComponent(Hoge.class, "/moge");
		ActionContext actionContext = new ActionContextImpl(mock, pageDesc);
		mock.setActionContext(actionContext);

		actionContext = invokingContext.buildActionContext();

		Method m = MethodUtil.getDeclaredMethod(Hoge.class, "default_",
				Constants.EMPTY_CLASS_ARRAY);

		Method md = actionContext.getTargetMethodDesc();
		assertEquals(m, md);
	}

	public void testBuild2_defaultAndActionPath() throws Exception {

		ActionContextBuilderImpl builder = new ActionContextBuilderImpl(creator
				.createActionMethodResolvers(containerAdapter), creator
				.createDefaultActionMethodResolver(containerAdapter));
		MockWebContext mock = MockWebContext.createMock("/hoge", "foo/list");
		mock.getMockHttpServletRequest().setMethod("GET");

		PageComponent pageDesc = new MockPageComponent(Foo.class, "foo");
		ActionContext actionContext = new ActionContextImpl(mock);
		actionContext.setPageDescCandidates(Arrays.asList(pageDesc));
		mock.setActionContext(actionContext);

		ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
				mock, creator, containerAdapter, null);

		builder.build(invokingContext);

		Method m = MethodUtil.getDeclaredMethod(Foo.class, "list",
				Constants.EMPTY_CLASS_ARRAY);

		Method md = actionContext.getTargetMethodDesc();
		assertEquals(m, md);
	}

	public void testBuild3_otherAnnotationShouldBeIgnored() throws Exception {
		ActionContextBuilderImpl builder = new ActionContextBuilderImpl(creator
				.createActionMethodResolvers(containerAdapter), creator
				.createDefaultActionMethodResolver(containerAdapter));
		MockWebContext mock = MockWebContext.createMock("/hoge", "bar/exec");
		mock.getMockHttpServletRequest().setMethod("GET");
		mock.getMockHttpServletRequest().setParameter("exec", "hogehoge");

		PageComponent pageDesc = new MockPageComponent(Bar.class, "bar");
		mock.setActionContext(new ActionContextImpl(mock, pageDesc));

		ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
				mock, creator, containerAdapter, null);
		ActionContext actionContext = builder.build(invokingContext);

		Method m = MethodUtil.getMethod(Bar.class, "exec",
				Constants.EMPTY_CLASS_ARRAY);

		Method md = actionContext.getTargetMethodDesc();
		assertEquals(m, md);
	}

	public void testBuild4_noActionMethod() throws Exception {
		ActionContextBuilderImpl builder = new ActionContextBuilderImpl(creator
				.createActionMethodResolvers(containerAdapter), creator
				.createDefaultActionMethodResolver(containerAdapter));
		MockWebContext mock = MockWebContext.createMock("/hoge",
				"baz/no_such_actionmethod");
		mock.getMockHttpServletRequest().setMethod("GET");

		PageComponent pageDesc = new MockPageComponent(Baz.class, "baz");
		mock.setActionContext(new ActionContextImpl(mock, pageDesc));

		ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
				mock, creator, containerAdapter, null);

		try {
			@SuppressWarnings("unused")
			ActionContext actionContext = builder.build(invokingContext);
			fail();
		} catch (NoDefaultActionMethodFoundRuntimeException expected) {
		}
	}

	@Page
	public static class Hoge {

		@Default
		public Navigation default_() {
			return null;
		}
	}

	@Page("foo")
	public static class Foo {

		@Default
		public Navigation default_() {
			return null;
		}

		@GET
		@ActionPath
		public Navigation list() {
			return null;
		}
	}

	@Page("bar")
	public static class Bar {

		@Aaa
		@GET
		@Bbb
		@ActionParam
		@ActionPath
		public Navigation exec() {
			return null;
		}
	}

	@Page("baz")
	public static class Baz {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Aaa {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Bbb {
	}

}
