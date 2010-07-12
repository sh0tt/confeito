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
package org.t2framework.confeito.mock;

import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.impl.ActionContextImpl;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.util.StringUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * Mock object of {@link ActionContext}.
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class MockActionContextImpl extends ActionContextImpl {

	protected Request request;

	protected String pagePath;

	protected Method defaultMethodDesc;

	protected String pageTemplatePath;

	public MockActionContextImpl() {
		super(null, null);
	}

	public MockActionContextImpl(WebContext context) {
		super(context, null);
		this.request = context.getRequest();
	}

	public MockActionContextImpl(WebContext context, PageComponent pageDesc) {
		super(context, pageDesc);
		this.request = context.getRequest();
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Request getRequest() {
		return request;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setDefaultMethodDesc(Method defaultMethodDesc) {
		this.defaultMethodDesc = defaultMethodDesc;
	}

	public String getPageTemplatePath() {
		return !StringUtil.isEmpty(pageTemplatePath) ? pageTemplatePath : super
				.getPageTemplatePath();
	}

	public void setPageTemplatePath(String pageTemplatePath) {
		this.pageTemplatePath = pageTemplatePath;
	}

}
