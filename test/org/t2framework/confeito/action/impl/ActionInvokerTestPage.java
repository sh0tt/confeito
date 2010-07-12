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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.annotation.RequestParam;
import org.t2framework.confeito.annotation.Var;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.navigation.Forward;
import org.t2framework.confeito.spi.Navigation;

@Page("/page")
public class ActionInvokerTestPage {

	@ActionPath("/")
	public Navigation handleIndex() {
		return Forward.to("index");
	}

	@ActionPath
	public Navigation hoge(WebContext webContext) {
		ActionInvokerImplTest.assertNotNull(webContext);
		return Forward.to("hoge");
	}

	@GET
	@ActionPath
	public Navigation fuga() {
		return Forward.to("fuga");
	}

	@GET
	@ActionPath("fugafuga")
	public Navigation fugafugaHandler() {
		return Forward.to("fugafuga");
	}

	@GET
	@ActionParam
	public Navigation foo() {
		return Forward.to("foo");
	}

	@GET
	@ActionParam("bar")
	public Navigation barCommand() {
		return Forward.to("bar");
	}

	@GET
	@ActionParam("bar")
	public Navigation barCommand(@RequestParam("bar") String bar) {
		ActionInvokerImplTest.assertEquals("barvalue", bar);
		return Forward.to("bar");
	}

	@POST
	@ActionPath
	public Navigation hoehoe(HttpServletRequest request,
			HttpServletResponse response) {
		ActionInvokerImplTest.assertNotNull(request);
		ActionInvokerImplTest.assertNotNull(response);
		return Forward.to("hoehoe");
	}

	@POST
	@ActionPath("lolo")
	@ActionParam("lolocommand")
	public Navigation loloHandler(WebContext context,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("lolocommand") String command,
			@RequestParam("lolocommand2") String command2) {
		ActionInvokerImplTest.assertNotNull(command);
		ActionInvokerImplTest.assertNotNull(request);
		ActionInvokerImplTest.assertNotNull(response);
		ActionInvokerImplTest.assertEquals("lolocommandvalue", command);
		ActionInvokerImplTest.assertEquals("lolocommandvalue2", command2);
		return Forward.to("lolo");
	}

	@ActionPath("hogebar/{foo}")
	public Navigation var(@Var("foo") String foo) {
		ActionInvokerImplTest.assertEquals("foovar", foo);
		return Forward.to(foo);
	}

	@ActionPath("hogebar2/{foo}/{foo2}")
	public Navigation var2(@Var("foo") String foo, @Var("foo2") String foo2) {
		ActionInvokerImplTest.assertEquals("foo1", foo);
		ActionInvokerImplTest.assertEquals("foo2", foo2);
		return Forward.to(foo2);
	}

	@ActionPath("hogebar2/{foo}")
	@ActionParam("param-{foo2}")
	public Navigation var3(@Var("foo") String foo, @Var("foo2") String foo2) {
		ActionInvokerImplTest.assertEquals("foo1", foo);
		ActionInvokerImplTest.assertEquals("foo2", foo2);
		return Forward.to(foo2);
	}

}