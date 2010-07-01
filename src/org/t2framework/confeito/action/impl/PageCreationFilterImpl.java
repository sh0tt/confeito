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

import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.NoActionMethodFoundRuntimeException;
import org.t2framework.confeito.internal.NavigationUtil;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * Page creation {@link ActionFilter}.This filter is intended to work before
 * action method and its parameter preparation.
 * </p>
 * <#else>
 * <p>
 * Pageクラスのインスタンスを生成する{@link ActionFilter}の実装クラス.
 * このフィルターは、アクションメソッドを決定する処理と、そのメソッドの引数の解決をする前に処理が呼ばれる必要があります.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class PageCreationFilterImpl implements ActionFilter {

	/**
	 * <#if locale="en">
	 * <p>
	 * Create targeted page instance.If page creation failed, chain invocation
	 * is terminated immediately and response is set error as
	 * {@link HttpServletResponse#SC_NOT_FOUND}.
	 * </p>
	 * <#else>
	 * <p>
	 * 実行対象のPageクラスのインスタンスを生成します.生成に失敗した場合、ActionFilterのチェーン呼び出しを停止し、
	 * {@link HttpServletResponse#SC_NOT_FOUND}のエラーをレスポンスにセットします.
	 * </p>
	 * </#if>
	 * 
	 * @throws NoActionMethodFoundRuntimeException
	 *             if there is no action method found.
	 */
	@Override
	public void invoke(ActionInvokingContext invokingContext,
			PageComponent pageDesc, ActionFilterChain chain) {
		WebContext context = invokingContext.getWebContext();
		final ActionContext actionContext = invokingContext
				.buildActionContext();
		if (hasActionMethod(invokingContext, actionContext) == false) {
			throw new NoActionMethodFoundRuntimeException();
		}
		final Object page = invokingContext.createPage(pageDesc);
		if (page == null) {
			invokingContext.setActionStatus(ActionStatus.NO_PAGE);
			context.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		invokingContext.setPage(page);
		Navigation ret = invokingContext.getPluginProcessor()
				.invokeComponentCreated(context, page);
		if (NavigationUtil.isNavigateImmediately(ret)) {
			invokingContext.setResultNavigation(ret);
			return;
		}
		chain.invokeChain(invokingContext, pageDesc);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if any action method is available to use.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param invokingContext
	 * @param actionContext
	 * @return true if any action method is available
	 */
	protected boolean hasActionMethod(ActionInvokingContext invokingContext,
			ActionContext actionContext) {
		final Method actionMethod = actionContext.getTargetMethodDesc();
		return actionMethod != null
				&& invokingContext.getActionStatus() == ActionStatus.FOUND_ACTION_METHOD;
	}

}
