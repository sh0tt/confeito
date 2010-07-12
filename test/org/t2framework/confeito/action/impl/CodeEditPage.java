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

import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.annotation.Var;
import org.t2framework.confeito.navigation.Forward;
import org.t2framework.confeito.spi.Navigation;

@Page("/edit")
public class CodeEditPage {

	public CodeEditPage() {
	}

	@Default
	@ActionPath("/{foo}")
	public Navigation index(@Var("foo") String foo) {
		System.out.println("index() - {foo}");
		System.out.println("foo: " + foo);
		return Forward.to("default");
	}

	@GET
	@ActionPath("/hoge/{foo}")
	public Navigation var(@Var("foo") String foo) {
		System.out.println("var() - hoge/{foo}");
		System.out.println("foo: " + foo);
		return Forward.to("var");
	}

	@GET
	@ActionPath("/hoge/{foo}/{bar}")
	public Navigation var2(@Var("foo") String foo, @Var("bar") String bar) {
		System.out.println("var2() - hoge/{foo}/{bar}");
		System.out.println("foo: " + foo + ", bar:" + bar);
		return Forward.to("var2");
	}
}
