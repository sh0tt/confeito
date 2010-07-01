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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ErrorInfo;
import org.t2framework.confeito.contexts.AmfContext;
import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Concrete class of {@link ActionContext}.
 * </p>
 * <#else>
 * <p>
 * {@link ActionContext}の実装クラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.action.ActionContext
 */
public class ActionContextImpl implements ActionContext {

	protected PageComponent targetPageDesc;

	protected Method targetMethodDesc;

	protected final WebContext context;

	protected AtomicInteger matchCount = new AtomicInteger(0);

	protected Object body;

	protected List<PageComponent> pageDescCandidates;

	protected PageComponent pageDesc;

	protected ConcurrentHashMap<Method, List<UrlTemplate>> templatesMap = new ConcurrentHashMap<Method, List<UrlTemplate>>();

	protected ErrorInfo errorInfo;

	protected Map<String, String> variablesMap = new HashMap<String, String>();

	protected AmfContext amfContext;

	public ActionContextImpl(final WebContext context) {
		this.context = context;
	}

	public ActionContextImpl(final WebContext context,
			final PageComponent pageDesc) {
		this.context = context;
		this.pageDesc = pageDesc;
		this.pageDescCandidates = Arrays.asList(this.pageDesc);
	}

	@Override
	public Method getTargetMethodDesc() {
		if (this.targetMethodDesc == null) {
			throw new IllegalStateException(
					"target method desc is not set yet.");
		}
		return targetMethodDesc;
	}

	@Override
	public synchronized void setTargetMethodDesc(Method methodDesc) {
		this.targetMethodDesc = methodDesc;
	}

	@Override
	public String getPageTemplatePath() {
		return getTargetPageDesc().getPageTemplatePath();
	}

	@Override
	public Request getRequest() {
		return context.getRequest();
	}

	public void incrementMatchCount() {
		matchCount.incrementAndGet();
	}

	public void clearMatchCount() {
		matchCount.set(0);
	}

	public int getMatchCount() {
		return matchCount.get();
	}

	@Override
	public Response getResponse() {
		return context.getResponse();
	}

	@Override
	public Session getSession() {
		return context.getSession();
	}

	@Override
	public Application getApplication() {
		return context.getApplication();
	}

	@Override
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = Assertion.notNull(errorInfo);
	}

	@Override
	public void clearErrorInfo() {
		this.errorInfo = null;
	}

	@Override
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	@Override
	public void setPageDescCandidates(List<PageComponent> pageDescCandidates) {
		this.pageDescCandidates = pageDescCandidates;
	}

	public List<PageComponent> getPageDescCandidates() {
		return pageDescCandidates;
	}

	@Override
	public boolean hasPageDescCandidates() {
		return getPageDescCandidates() != null
				&& getPageDescCandidates().isEmpty() == false;
	}

	@Override
	public PageComponent getTargetPageDesc() {
		if (this.pageDesc == null) {
			throw new IllegalStateException("no such page.");
		}
		return pageDesc;
	}

	@Override
	public void setTargetPageDesc(PageComponent pageDesc) {
		this.pageDesc = pageDesc;
	}

	@Override
	public void addVariables(String key, String value) {
		variablesMap.put(key, value);
	}

	@Override
	public void cleanVariables() {
		variablesMap.clear();
	}

	@Override
	public String getVariables(String key) {
		return variablesMap.get(key);
	}

	@Override
	public void setAmfContext(AmfContext amfContext) {
		this.amfContext = amfContext;

	}

	@Override
	public AmfContext getAmfContext() {
		return amfContext;
	}

}
