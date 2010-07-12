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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.mock.MockPageComponent;
import org.t2framework.confeito.mock.MockWebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.navigation.Forward;
import org.t2framework.confeito.spi.Navigation;

public class PageDescFinderImplTest extends TestCase {

	public void testFind_normalCase() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("aaa", "bbb");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("bbb", new MockPageComponent(PageDescFinderImplTest.class,
					"bbb"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("bbb", find.get(0).getPageTemplatePath());
		}
	}

	public void testFind_pathDoesnotMatch() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("aaa", "bbb");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("not_match_path", new MockPageComponent(
					PageDescFinderImplTest.class, "not_match_path"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertTrue(find.isEmpty());
		}
	}

	public void testFind_longestFirst() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		List<PageComponent> find = null;
		{
			MockWebContext mock = MockWebContext.createMock("aaa",
					"bbb/ccc/ddd");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("bbb", new MockPageComponent(PageDescFinderImplTest.class,
					"bbb"));
			map.put("bbb/ccc/ddd", new MockPageComponent(
					PageDescFinderImplTest.class, "bbb/ccc/ddd"));
			map.put("bbb/ccc", new MockPageComponent(
					PageDescFinderImplTest.class, "bbb/ccc"));
			finder.initialize(map);
			find = finder.find(mock);
		}
		assertNotNull(find);
		assertTrue(find.size() == 3);
		assertEquals("bbb/ccc/ddd", find.get(0).getPageTemplatePath());
		assertEquals("bbb/ccc", find.get(1).getPageTemplatePath());
		assertEquals("bbb", find.get(2).getPageTemplatePath());
	}

	public void testFind_expressionLast() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		List<PageComponent> find = null;
		{
			MockWebContext mock = MockWebContext.createMock("aaa",
					"bbb/ccc/ddd");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("bbb", new MockPageComponent(PageDescFinderImplTest.class,
					"bbb"));
			map.put("bbb/ccc/ddd", new MockPageComponent(
					PageDescFinderImplTest.class, "bbb/ccc/ddd"));
			map.put("bbb/ccc", new MockPageComponent(
					PageDescFinderImplTest.class, "bbb/ccc"));
			map.put("bbb/{hoge}", new MockPageComponent(
					PageDescFinderImplTest.class, "bbb/{hoge}"));
			finder.initialize(map);
			find = finder.find(mock);
		}
		assertNotNull(find);
		assertTrue(find.size() == 4);
		assertEquals("bbb/ccc/ddd", find.get(0).getPageTemplatePath());
		assertEquals("bbb/ccc", find.get(1).getPageTemplatePath());
		assertEquals("bbb/{hoge}", find.get(2).getPageTemplatePath());
		assertEquals("bbb", find.get(3).getPageTemplatePath());
	}

	public void testFind_expressionLast2() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		List<PageComponent> find = null;
		{
			MockWebContext mock = MockWebContext.createMock("aaa",
					"aaa/bbb/ccc");
			MockPageComponent pd1 = new MockPageComponent(
					PageDescFinderImplTest.class, "aaa");
			MockPageComponent pd2 = new MockPageComponent(
					PageDescFinderImplTest.class, "aaa/bbb/ccc");
			MockPageComponent pd3 = new MockPageComponent(
					PageDescFinderImplTest.class, "aaa/bbb");
			MockPageComponent pd4 = new MockPageComponent(
					PageDescFinderImplTest.class, "aaa/bbb/{hoge}");
			MockPageComponent pd5 = new MockPageComponent(
					PageDescFinderImplTest.class, "aaa/{hoge}");
			finder.initialize(createMap(pd1, pd2, pd3, pd4, pd5));
			find = finder.find(mock);
		}
		assertNotNull(find);
		assertTrue(find.size() == 5);
		for (PageComponent pd : find) {
			System.out.println(pd.getPageTemplatePath());
		}

		assertEquals("aaa/bbb/ccc", find.get(0).getPageTemplatePath());
		assertEquals("aaa/bbb/{hoge}", find.get(1).getPageTemplatePath());
		assertEquals("aaa/bbb", find.get(2).getPageTemplatePath());
		assertEquals("aaa/{hoge}", find.get(3).getPageTemplatePath());
		assertEquals("aaa", find.get(4).getPageTemplatePath());
	}

	public void testFind_rootPath() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2-spring", "/");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("/", new MockPageComponent(PageDescFinderImplTest.class,
					"/"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("/", find.get(0).getPageTemplatePath());
		}
	}

	protected Map<String, PageComponent> createMap(PageComponent... pageDescs) {
		Map<String, PageComponent> map = new HashMap<String, PageComponent>();
		for (PageComponent pd : pageDescs) {
			map.put(pd.getPageTemplatePath(), pd);
		}
		return map;
	}

	public void testCreate1() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2", "/test");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("/test", new MockPageComponent(
					PageDescFinderImplTest.class, "/test"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("/test", find.get(0).getPageTemplatePath());
		}
	}

	public void testCreate2() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext
					.createMock("/t2", "/test/0!_-");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("/test/{hoge}", new MockPageComponent(
					PageDescFinderImplTest.class, "/test/{hoge}"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("/test/{hoge}", find.get(0).getPageTemplatePath());
		}
	}

	public void testCreate3() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2", "/0");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("{aaa}", new MockPageComponent(
					PageDescFinderImplTest.class, "{hoge}"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("{hoge}", find.get(0).getPageTemplatePath());
		}
	}

	public void testUnnaturalRequest1() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2", "/");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map
					.put("", new MockPageComponent(
							PageDescFinderImplTest.class, ""));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertNotNull(find);
			assertTrue(find.size() == 1);
			assertEquals("", find.get(0).getPageTemplatePath());
		}
	}

	public void testUnnaturalRequest2() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2", "");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("a", new MockPageComponent(PageDescFinderImplTest.class,
					"a"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertTrue(find.size() == 0);
		}
	}

	public void testMatch_el3() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2", "moge/hoge");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("{aaa}/hoge", new MockPageComponent(
					PageDescFinderImplTest.class, "{aaa}/hoge"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertTrue(find.size() == 1);
		}
	}

	public void testMatch_el4() throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2",
					"moge/hoge/foo");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("{aaa}/hoge/{bbb}", new MockPageComponent(
					PageDescFinderImplTest.class, "{aaa}/hoge/{bbb}"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertTrue(find.size() == 1);
		}
	}

	public void testMatch_el5_requestedUrlShouldBeChoppedToVerify()
			throws Exception {
		PageDescFinderImpl finder = new PageDescFinderImpl();
		{
			MockWebContext mock = MockWebContext.createMock("/t2",
					"/page/invoke/moge/123");
			Map<String, PageComponent> map = new HashMap<String, PageComponent>();
			map.put("/page/{hoge}", new MockPageComponent(
					PageDescFinderImplTest.class, "/page/{hoge}"));
			finder.initialize(map);
			List<PageComponent> find = finder.find(mock);
			assertTrue(find.size() == 1);
		}
	}

	public static Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>();

	static {
		set.add(ActionPath.class);
		set.add(ActionParam.class);
		set.add(GET.class);
		set.add(POST.class);
	}

	@Page("/test")
	public static class TestPage {

		@Default
		public Navigation init(WebContext context) {
			return null;
		}

		@GET
		@ActionPath
		public Navigation a(WebContext context) {
			return null;
		}

		@POST
		@ActionParam
		public Navigation b(WebContext context) {
			return null;
		}

	}

	@Page("/test2")
	public static class Test2Page {

		@Default
		public Navigation getAaa(WebContext context) {
			return Forward.to("aaa.jsp");
		}

		@GET
		@POST
		@ActionParam("button1")
		public Navigation submit(WebContext context) {
			return null;
		}

		@GET
		@ActionParam("link2")
		public Navigation link(WebContext context) {
			return null;
		}

	}

	@Page("/test/{hoge}")
	public static class Test3Page {
	}

	@Page
	public static class Test4Page {
	}

	@Page
	public static class Test5Page {
		@Default
		public Navigation hoge(WebContext context) {
			return Forward.to("aaa.jsp");
		}

		@ActionParam("hogehoge")
		public Navigation hoge(WebContext context, Request request) {
			return null;
		}
	}
}
