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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.t2framework.confeito.ConfigurationKey;
import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.annotation.RequestParam;
import org.t2framework.confeito.annotation.Var;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.mock.MockFilterConfigImpl;
import org.t2framework.confeito.mock.MockHttpServletRequest;
import org.t2framework.confeito.mock.MockHttpServletResponse;
import org.t2framework.confeito.mock.MockPageComponent;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.mock.NullWebConfiguration;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.navigation.Forward;
import org.t2framework.confeito.navigation.Redirect;
import org.t2framework.confeito.plugin.AbstractPlugin;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.spi.impl.DefaultAnnotationResolverCreatorImpl;

public class ActionInvokerImplTest extends TestCase {

	private static AnnotationResolverCreator creator = new DefaultAnnotationResolverCreatorImpl();

	protected ActionInvokerImpl create(WebContext context,
			Map<String, PageComponent> PageComponentMap) {
		ActionInvokerImpl invoker = new ActionInvokerImpl();
		MockFilterConfigImpl filteConfig = new MockFilterConfigImpl();
		filteConfig.addInitParameter(ConfigurationKey.ROOT_PACKAGE_KEY,
				"org.t2framework");
		invoker.initialize(new NullWebConfiguration(), PageComponentMap);
		return invoker;
	}

	protected ActionInvokingContext createInvokingContext(WebContext context,
			ContainerAdapter<?> containerAdapter) {
		return new ActionInvokingContextImpl(context, creator,
				containerAdapter, new PluginProcessor(containerAdapter));
	}

	public void testInvoke() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/invoke/hoge.html");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(InvokeTestPage.class,
				"/invoke");
		map.put("^[hoge].*", pd);
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage.class);
		ActionInvokerImpl invoker = create(context, map);
		Navigation next = invoker
				.invoke(createInvokingContext(context, adapter));
		assertEquals(NextPage.class, ((Forward) next).getPageClass());
	}

	public void testInvoke_checkAfterInvokeThereAreRequestParamSaving()
			throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/invoke/hoge.html");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "hogemoge");
		mockRequest.addParameter("aaa", "12.12");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(InvokeTestPage.class,
				"/invoke");
		map.put("^[hoge].*", pd);
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage.class);
		ActionInvokerImpl invoker = create(context, map);
		Navigation next = invoker
				.invoke(createInvokingContext(context, adapter));
		assertEquals(NextPage.class, ((Forward) next).getPageClass());

		assertEquals("hogemoge", mockRequest.getAttribute("hoge"));
		assertEquals("12.12", mockRequest.getAttribute("aaa"));
	}

	public void testInvoke_default() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/invoke");
		context.getMockHttpServletRequest().setMethod("GET");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(InvokeTestPage3.class,
				"invoke");
		map.put("^[hoge].*", pd);
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage3.class);
		ActionInvokerImpl invoker = create(context, map);
		Navigation next = invoker
				.invoke(createInvokingContext(context, adapter));
		assertEquals(NextPage.class, ((Forward) next).getPageClass());
	}

	public void testMethodArgsInvoke() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"invoke2");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		mockRequest.addParameter("param", "testParam");
		mockRequest.addParameter("params", "testParams1");
		mockRequest.addParameter("params", "testParams2");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		{
			PageComponent pd = new MockPageComponent(InvokeTestPage2.class,
					"/invoke2");
			map.put("^[hoge].*", pd);
		}
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage2.class);
		ActionInvokerImpl invoker = create(context, map);
		Navigation next = invoker
				.invoke(createInvokingContext(context, adapter));
		assertNotNull(next);
		assertEquals(NextPage.class, ((Forward) next).getPageClass());
	}

	public void testMethodArgsParamRequiredInvoke() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"/hoge.html");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		{
			PageComponent pd = new MockPageComponent(InvokeTestPage2.class,
					"/hoge.html");
			map.put("^[hoge].*", pd);
		}
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage2.class);
		ActionInvokerImpl invoker = create(context, map);
		try {
			invoker.invoke(createInvokingContext(context, adapter));
			fail();
		} catch (RuntimeException e) {
			success();
		}
	}

	private void success() {
		assertTrue(true);
	}

	public void testInvokeNoArgs() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"invoke4/hoge.html");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		{
			PageComponent pd = new MockPageComponent(InvokeTestPage4.class,
					"/invoke4");
			map.put("^[hoge].*", pd);
		}
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage4.class);
		ActionInvokerImpl invoker = create(context, map);
		Navigation next = invoker
				.invoke(createInvokingContext(context, adapter));
		assertEquals(NextPage.class, ((Forward) next).getPageClass());
	}

	public void testInvokeWithException() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"invoke5/hoge.html");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		{
			PageComponent pd = new MockPageComponent(InvokeTestPage5.class,
					"/invoke5");
			map.put("^[hoge].*", pd);
		}
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(InvokeTestPage5.class);
		ActionInvokerImpl invoker = create(context, map);
		try {
			invoker.invoke(createInvokingContext(context, adapter));
			fail();
		} catch (IllegalArgumentException expected) {
			success();
		}
	}

	public void testPageValueTest() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"page/invoke/index");
		MockHttpServletRequest mockRequest = context
				.getMockHttpServletRequest();
		mockRequest.addParameter("hoge", "");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(PageValueTestTarget.class,
				"page/invoke");
		map.put("^[hoge].*", pd);
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(PageValueTestTarget.class);
		ActionInvokerImpl invoker = create(context, map);
		Forward invoke = (Forward) invoker.invoke(createInvokingContext(
				context, adapter));
		assertNotNull(invoke);
		assertTrue(invoke.getPageClass() == NextPage.class);
	}

	public void testVarValueTest() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"page/invoke/moge/123");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(VarValueTestTarget.class,
				"page/invoke");
		map.put("page/invoke", pd);
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(VarValueTestTarget.class);
		ActionInvokerImpl invoker = create(context, map);
		invoker.invoke(createInvokingContext(context, adapter));
	}

	public void testPageNotFound() throws Exception {
		MockWebContext context = MockWebContext.createMock("/t2-test",
				"page/invoke/moge/123");
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		{
			PageComponent pd = new MockPageComponent(VarValueTestTarget.class,
					"page/invoke");
			map.put("page/invoke", pd);
		}
		SimpleContainerAdapter adapter = new SimpleContainerAdapter() {

			@Override
			public <T> T getComponent(Class<? super T> componentClass) {
				return null;
			}

		};
		adapter.init();
		adapter.register(VarValueTestTarget.class);
		ActionInvokerImpl invoker = create(context, map);
		invoker.invoke(createInvokingContext(context, adapter));

		MockHttpServletResponse mockHttpServletResponse = context
				.getMockHttpServletResponse();
		int status = mockHttpServletResponse.getStatus();
		assertTrue(status == HttpServletResponse.SC_NOT_FOUND);
	}

	@Page("/invoke")
	public static class InvokeTestPage {

		@POST
		@ActionParam
		public Navigation hoge(WebContext context) {
			return Forward.to(NextPage.class);
		}

	}

	@Page("/invoke2")
	public static class InvokeTestPage2 {

		@POST
		@ActionParam
		public Navigation hoge(@RequestParam("param") String param,
				@RequestParam("params") String[] params, WebContext context,
				HttpServletRequest request, HttpServletResponse response) {
			assertNotNull(context);
			assertNotNull(request);
			assertNotNull(response);
			assertEquals("testParam", param);
			assertEquals("testParams1", params[0]);
			assertEquals("testParams2", params[1]);
			assertEquals(2, params.length);
			return Forward.to(NextPage.class);
		}
	}

	public void testIndex() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app", "/page");
		context.getMockHttpServletRequest().setMethod("GET");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("index", next.getPath());

	}

	public void testActionPath() throws Exception {
		MockWebContext context = MockWebContext
				.createMock("/app", "/page/hoge");
		context.getMockHttpServletRequest().setMethod("GET");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("hoge", next.getPath());
	}

	public void testActionPathWithValue() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/fugafuga");
		context.getMockHttpServletRequest().setMethod("GET");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("fugafuga", next.getPath());
	}

	public void testActionParam() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app", "/page");
		context.getMockHttpServletRequest().setMethod("GET");
		context.getMockHttpServletRequest().addParameter("foo", "foovalue");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("foo", next.getPath());
	}

	public void testActionParamWithValue() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app", "/page");
		context.getMockHttpServletRequest().setMethod("GET");
		context.getMockHttpServletRequest().addParameter("bar", "barvalue");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("bar", next.getPath());
	}

	public void testActionParamAndPathWithValue() throws Exception {
		MockWebContext context = MockWebContext
				.createMock("/app", "/page/lolo");
		context.getMockHttpServletRequest().setMethod("POST");
		context.getMockHttpServletRequest().addParameter("lolocommand",
				"lolocommandvalue");
		context.getMockHttpServletRequest().addParameter("lolocommand2",
				"lolocommandvalue2");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("lolo", next.getPath());
	}

	public void testNotInvokeBecauseGET() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hoehoe");
		context.getMockHttpServletRequest().setMethod("GET");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		try {
			// cannot invoke because HTTPMethod is GET
			invoker.invoke(createInvokingContext(context, adapter));
			fail();
		} catch (RuntimeException e) {
			success();
		}
	}

	public void testPOST() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hoehoe");
		context.getMockHttpServletRequest().setMethod("POST");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("hoehoe", next.getPath());
	}

	public void testVar() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hogebar/foovar");
		context.getMockHttpServletRequest().setMethod("POST");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("foovar", next.getPath());
	}

	public void testVar2() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hogebar2/foo1/foo2");
		context.getMockHttpServletRequest().setMethod("POST");
		context.getMockHttpServletRequest().addParameter("paramfoovar2",
				"param");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("foo2", next.getPath());
	}

	public void testInvokeWithPlugin1_beforeActionInvoke() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hoehoe");
		context.getMockHttpServletRequest().setMethod("POST");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		adapter.register(MyPlugin1.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Redirect next = (Redirect) invoker.invoke(createInvokingContext(
				context, adapter));
		assertEquals("hoge", next.getPath());

	}

	public void testInvokeWithPlugin2_afterActionInvoke() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/page/hoehoe");
		context.getMockHttpServletRequest().setMethod("POST");

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(ActionInvokerTestPage.class);
		adapter.register(MyPlugin2.class);
		ActionInvokerImpl invoker = create(context, createPageComponentMap());
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("aaa", next.getPath());

	}

	public void testMultipleActionMethodWithUrl_deepestUrlFirst()
			throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/edit/hoge/foostr/barstr");
		context.getMockHttpServletRequest().setMethod("GET");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(CodeEditPage.class);

		CodeEditPage component = adapter.getComponent(CodeEditPage.class);
		assertNotNull(component);

		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(CodeEditPage.class, "/edit");
		map.put("/edit", pd);

		ActionInvokerImpl invoker = create(context, map);
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("var2", next.getPath());
	}

	public void testMultipleActionMethodWithUrl2_deeperComestNext()
			throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/edit/hoge/foostr");
		context.getMockHttpServletRequest().setMethod("GET");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(CodeEditPage.class);

		CodeEditPage component = adapter.getComponent(CodeEditPage.class);
		assertNotNull(component);

		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(CodeEditPage.class, "/edit");
		map.put("/edit", pd);

		ActionInvokerImpl invoker = create(context, map);
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("var", next.getPath());
	}

	public void testMultipleActionMethodWithUrl3_noOneFound_thenInvokeDefault()
			throws Exception {
		MockWebContext context = MockWebContext.createMock("/app",
				"/edit/mukyakya");
		context.getMockHttpServletRequest().setMethod("GET");
		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(CodeEditPage.class);

		CodeEditPage component = adapter.getComponent(CodeEditPage.class);
		assertNotNull(component);

		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(CodeEditPage.class, "/edit");
		map.put("/edit", pd);

		ActionInvokerImpl invoker = create(context, map);
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		assertEquals("default", next.getPath());
	}

	public void testActionMethodOverloaded() throws Exception {
		MockWebContext context = MockWebContext.createMock("/app", "/overload");
		context.getMockHttpServletRequest().setMethod("GET");

		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(OverloadMethodPage.class,
				"overload");
		map.put("/overload", pd);

		SimpleContainerAdapter adapter = new SimpleContainerAdapter();
		adapter.init();
		adapter.register(OverloadMethodPage.class);
		ActionInvokerImpl invoker = create(context, map);
		Forward next = (Forward) invoker.invoke(createInvokingContext(context,
				adapter));
		System.out.println(next);
	}

	public static class MyPlugin1 extends AbstractPlugin {

		@Override
		public Navigation beforeActionInvoke(ActionContext actionContext,
				Method targetMethod, Object page, Object[] args) {
			return Redirect.to("hoge");
		}

	}

	
	public static class MyPlugin2 extends AbstractPlugin {

		@Override
		public Navigation afterActionInvoke(ActionContext actionContext,
				Method targetMethod, Object page, Object[] args,
				Navigation result) {
			return Forward.to("aaa");
		}

	}

	/**
	 * ActionParameterResolver does not support expression.
	 * 
	 * @return
	 */
	// public void testVar3() throws Exception {
	// adapter.init();adapter.register(ActionInvokerTestPage.class);
	// MockWebContext context = MockWebContext.createMock("/app",
	// "/page/hogebar2/foo1");
	// context.getMockHttpServletRequest().setMethod("POST");
	// context.getMockHttpServletRequest().addParameter("param-foo2",
	// "mogemoge");
	// LucyContainerAdapter adapter = new LucyContainerAdapter();
	// adapter.init();
	// ActionInvokerImpl invoker = create(context, createPageComponentMap(),
	// adapter);
	// assertTrue(invoker.match());
	// Forward next = (Forward) invoker.invoke();
	// assertEquals("mogemoge", next.getPath());
	// }
	private Map<String, PageComponent> createPageComponentMap() {
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		PageComponent pd = new MockPageComponent(ActionInvokerTestPage.class,
				"page");
		map.put("/page", pd);
		return map;
	}

	
	@Page("/invoke3")
	public static class InvokeTestPage3 {

		@Default
		public Navigation index(WebContext context) {
			return Forward.to(NextPage.class);
		}

	}

	
	@Page("/invoke4")
	public static class InvokeTestPage4 {

		@Default
		public Navigation index() {
			return Forward.to(NextPage.class);
		}

	}

	
	@Page("/invoke5")
	public static class InvokeTestPage5 {

		@Default
		public Navigation index() {
			throw new IllegalArgumentException("illegal args!");
		}

	}

	// 
	// @Page("/sample")
	// public static class InvokeTestPage5 {
	//
	// @Ajax
	// @GET
	// public Navigation moge() {
	// return Forward.to(NextPage.class);
	// }
	//
	// }

	
	@Page("/page/invoke")
	public static class PageValueTestTarget {

		@Default
		public Navigation index() {
			return Forward.to(NextPage.class);
		}

	}

	
	@Page("/page/invoke")
	public static class VarValueTestTarget {

		@ActionPath("/moge/{muge}")
		public Navigation index(@Var("muge") String str) {
			assertEquals("123", str);
			return Forward.to(NextPage.class);
		}

	}

	@Page
	public static class NextPage {
	}

	@Page("/overload")
	public static class OverloadMethodPage {

		@Default
		public Navigation index() {
			return Forward.to(NextPage.class);
		}

		@ActionPath
		public Navigation index(WebContext context) {
			return Forward.to(VarValueTestTarget.class);
		}

	}
}
