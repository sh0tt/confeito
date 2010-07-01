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

import java.util.List;
import java.util.Map;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvoker;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.ActionStatus;
import org.t2framework.confeito.action.PageDescFinder;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.ArrayUtil;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.PrintableUtil;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link ActionInvoker}.
 * </p>
 * <#else>
 * <p>
 * {@link ActionInvoker}の実装クラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionInvokerImpl implements ActionInvoker {

	protected static Logger log = Logger.getLogger(ActionInvokerImpl.class);

	/**
	 * <#if locale="en">
	 * <p>
	 * 　Action filters.
	 * </p>
	 * <#else>
	 * <p>
	 * Action filters.
	 * </p>
	 * </#if>
	 */
	protected ActionFilter[] filters = new ActionFilter[] {
			new ExceptionHandlerActionFilterImpl(),
			new PageCreationFilterImpl(),
			new ActionArgumentsPreparationFilterImpl(),
			new ActionInvokerFilterImpl() };

	protected PageDescFinder pageDescFinder = new PageDescFinderImpl();

	public ActionInvokerImpl() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize by {@link PageDescFinder#initialize(Map)}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link PageDescFinder#initialize(Map)}から初期化時に呼び出されます.
	 * </p>
	 * </#if>
	 */
	@Override
	public void initialize(WebConfiguration webConfiguration,
			Map<String, PageComponent> pageDescMap) {
		getPageDescFinder().initialize(pageDescMap);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke each {@link ActionFilter} by {@link ActionFilterChain}, and get
	 * from result as {@link Navigation}.If the result is not null, then
	 * invoking each {@link ActionFilter} immediately stop and returns
	 * result.The result may be null.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionFilterChain}により{@link ActionFilter}を呼び出し,呼び出しの戻り値として
	 * {@link Navigation}を取得します.もし戻り値がnullでない場合、{@link ActionFilter}
	 * の呼び出しを停止し、その戻り値を返します. このメソッドの戻り値は、nullの場合があります.
	 * </p>
	 * </#if>
	 */
	@Override
	public Navigation invoke(ActionInvokingContext invokingContext) {
		Assertion.notNull(invokingContext);
		final ActionContext actionContext = setupActionContext(invokingContext);
		invokingContext.setActionStatus(ActionStatus.BEGIN);
		final List<PageComponent> pageCandidates = actionContext
				.getPageDescCandidates();
		Navigation ret = null;
		for (PageComponent pageDesc : pageCandidates) {
			ActionFilterChain chain = new ActionFilterChainImpl(filters);

			chain.invokeChain(invokingContext, pageDesc);

			ret = invokingContext.getResultNavigation();
			if (ret != null) {
				break;
			}
			invokingContext.nextPageDesc();
		}
		invokingContext.setActionStatus(ActionStatus.END);
		return ret;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get or create an {@link ActionContext} and set target
	 * {@link PageComponent} for the user request.Page finding is done by
	 * {@link PageDescFinder} which must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.PageDescFinder
	 * @param invokingContext
	 * @return
	 */
	protected ActionContext setupActionContext(
			final ActionInvokingContext invokingContext) {
		final WebContext context = invokingContext.getWebContext();
		ActionContext actionContext = context.getActionContext();
		if (actionContext == null) {
			actionContext = createActionContext(context);
		}
		final List<PageComponent> pageDescCandidates = getPageDescFinder()
				.find(context);
		logFoundPageDescCandidates(pageDescCandidates);
		actionContext.setPageDescCandidates(pageDescCandidates);
		return actionContext;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Logging {@link PageComponent} candidates.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param pageDescCandidates
	 */
	protected void logFoundPageDescCandidates(
			List<PageComponent> pageDescCandidates) {
		if (pageDescCandidates != null) {
			Object[] args = new Object[pageDescCandidates.size()];
			for (int i = 0; i < pageDescCandidates.size(); i++) {
				PageComponent pd = pageDescCandidates.get(i);
				args[i] = pd.getPageName();
			}
			if (0 < args.length) {
				log.log("ITDT0020", new Object[] { PrintableUtil
						.toPrintableString(args) });
			}
		}
	}

	protected ActionContext createActionContext(WebContext context) {
		ActionContext actionContext = new ActionContextImpl(context);
		context.setActionContext(actionContext);
		return actionContext;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set array of {@link ActionFilter}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionFilter}の配列をセットします.
	 * </p>
	 * </#if>
	 * 
	 * @param filters
	 */
	public void setActionFilters(ActionFilter[] filters) {
		this.filters = filters;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Add {@link ActionFilter} to the last.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionFilter}を、配列の最後に追加します.
	 * </p>
	 * </#if>
	 * 
	 * @param filter
	 */
	public void addActionFilter(ActionFilter filter) {
		ArrayUtil.add(filters, filter);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set {@link PageDescFinder}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link PageDescFinder}をセットします.
	 * </p>
	 * </#if>
	 * 
	 * @param pageDescFinder
	 */
	public void setPageDescFinder(PageDescFinder pageDescFinder) {
		this.pageDescFinder = Assertion.notNull(pageDescFinder);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link PageDescFinder}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link PageDescFinder}を返します.
	 * </p>
	 * </#if>
	 * 
	 * @return pageDescFinder
	 */
	public PageDescFinder getPageDescFinder() {
		return pageDescFinder;
	}

}
