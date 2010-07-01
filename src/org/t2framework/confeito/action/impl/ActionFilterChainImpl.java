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

import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.util.Assertion;

/**
 * 
 * <#if locale="en">
 * <p>
 * {@link ActionFilterChainImpl} is a chain implementation for invoking
 * {@link ActionFilter} sequentially.
 * </p>
 * <#else>
 * <p>
 * {@link ActionFilterChainImpl}は、{@link ActionFilter}を順次呼び出すためのチェーン実装を提供します.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionFilterChainImpl implements ActionFilterChain {

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link ActionFilter} to be invoked.
	 * </p>
	 * <#else>
	 * <p>
	 * 呼び出される{@link ActionFilter}.
	 * </p>
	 * </#if>
	 */
	protected ActionFilter[] filters;

	/**
	 * <#if locale="en">
	 * <p>
	 * Current index.
	 * </p>
	 * <#else>
	 * <p>
	 * 今呼び出しをしているfiltersのインデックス.
	 * </p>
	 * </#if>
	 */
	protected int current = 0;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this filter. {@link ActionFilter} arguments must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * このフィルターを構築します.{@link ActionFilter}はnullであってはいけません.
	 * </p>
	 * </#if>
	 * 
	 * @param filters
	 */
	public ActionFilterChainImpl(ActionFilter[] filters) {
		Assertion.notNull(filters);
		this.filters = filters;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionFilterChain#invokeChain(org.t2framework.confeito.action.ActionInvokingContext,
	 *      org.t2framework.t2.contexts.PageComponent)
	 * @param invokingContext
	 * @param pageDesc
	 */
	@Override
	public void invokeChain(ActionInvokingContext invokingContext,
			PageComponent pageDesc) {
		if (current < filters.length) {
			invokingContext.setActionStatus(ActionStatus.INVOKE_BEGIN);
			filters[current++].invoke(invokingContext, pageDesc, this);
		}
		setStatusEndIfNoError(invokingContext);
	}

	protected void setStatusEndIfNoError(ActionInvokingContext invokingContext) {
		ActionStatus status = invokingContext.getActionStatus();
		if (status != ActionStatus.ERROR || status != ActionStatus.NO_PAGE) {
			invokingContext.setActionStatus(ActionStatus.INVOKE_END);
		}
	}
}
