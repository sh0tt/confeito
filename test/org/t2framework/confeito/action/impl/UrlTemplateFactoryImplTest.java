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

import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.urltemplate.UrlTemplateFactory;

public class UrlTemplateFactoryImplTest extends TestCase {

	public void testCreateTemplate1() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge");
		assertNotNull(urlTemplate);
	}

	public void testCreateTemplate2() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge", "foo");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplate3() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge/", "foo");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplate4() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge/", "/foo");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplate5() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge", "/foo");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplate6() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("", "/foo");
		assertNotNull(urlTemplate);
		assertEquals("/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplate7() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge", "");
		assertNotNull(urlTemplate);
		assertEquals("hoge/", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplateWithJSessionId1() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge",
				"foo;jsessionid=abc");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo", urlTemplate.getTemplatePath());
	}

	public void testCreateTemplateWithJSessionId2() throws Exception {
		UrlTemplateFactory factory = new UrlTemplateFactory();
		UrlTemplate urlTemplate = factory.getUrlTemplate("hoge",
				"foo/bar;jsessionid=abc");
		assertNotNull(urlTemplate);
		assertEquals("hoge/foo/bar", urlTemplate.getTemplatePath());
	}

}
