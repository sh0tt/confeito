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
package org.t2framework.confeito.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.action.ActionInvoker;
import org.t2framework.confeito.action.ActionInvokerFactory;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.action.impl.ActionInvokerFactoryImpl;
import org.t2framework.confeito.action.impl.ActionInvokingContextImpl;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.contexts.impl.WebConfigurationImpl;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.navigation.PassThrough;
import org.t2framework.confeito.plugin.Plugin;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Logger;

/**
 * <#if locale="en">
 * <p>
 * The entry point for T2 framework.All of process for T2 framework should begin
 * from this filter.This filter is doing works below:
 * </p>
 * <ul>
 * <li>Initialize the core components such as {@link WebConfiguration},
 * {@link PluginProcessor}, and {@link ActionInvokerFactory}.</li>
 * <li>Executes request these processes below:</li>
 * <ul>
 * <li>Create {@link WebContext}, the root context object</li>
 * <li>Check whether T2 framework should take care of incoming request or not</li>
 * <li>Execute {@link ActionInvoker#invoke(ActionInvokingContext)} to find and
 * invoke a method for incoming request, then get {@link Navigation} as an
 * result.The result gets from</li>
 * <li>Invoke {@link Navigation#execute(WebContext)}</li>
 * </ul>
 * <li>Destroy this filer</li> </ul> <#else>
 * <p>
 * T2フレームワークのエントリポイントとなるフィルタです．T2の処理構造の全ては原則的にこのフィルタから始まります．
 * このフィルタは下記のような処理を行います:
 * </p>
 * <ul>
 * <li>T2を構成するコアコンポーネントを初期化します.例えば{@link WebConfiguration}や
 * {@link PluginProcessor}、{@link ActionInvokerFactory}などです.</li>
 * <li>以下の順番で処理を行います:</li>
 * <ul>
 * <li>ルートコンテキストオブジェクトである、{@link WebContext}を生成します.</li>
 * <li>T2で処理すべきリクエストかどうかを{@link WebConfiguration#getExcludeResources()}
 * を使ってチェックします.</li>
 * <li>{@link ActionInvoker#invoke(ActionInvokingContext)}を実行して、結果を
 * {@link Navigation}として取得します.</li>
 * <li>{@link Navigation#execute(WebContext)}を実行して、画面遷移とレンダリングの開始をWebコンテナに通知します.
 * </li>
 * </ul>
 * <li>T2Filterを破棄します.</li> </ul> </#if>
 * 
 * @author shot
 */
public class T2Filter implements Filter {

	/**
	 * Logger for this filter.
	 */
	protected Logger logger = Logger.getLogger(T2Filter.class);

	/**
	 * {@link WebConfiguration} instance which is used entire life cycle for
	 * this application.
	 */
	protected WebConfiguration webConfig;

	/**
	 * {@link PluginProcessor} instance which is used at entire life cycle for
	 * this application.
	 */
	protected PluginProcessor pluginProcessor;

	/**
	 * List of {@link ActionInvoker}.
	 */
	protected volatile List<? extends ActionInvoker> actionInvokerList;

	/**
	 * Construct this instance.
	 */
	public T2Filter() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Initialize {@link T2Filter}.{@link WebConfiguration} and
	 * {@link PluginProcessor} is instantiated and initialized at this time.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link T2Filter}を初期化します．{@link WebConfiguration}と{@link PluginProcessor}
	 * の初期化がこのタイミングで行われます.
	 * </p>
	 * </#if>
	 * 
	 * @throws ServletException
	 *             if any error occurred
	 */
	public void init(final FilterConfig config) throws ServletException {
		try {
			WebConfiguration webConfig = new WebConfigurationImpl(config);
			webConfig.initialize();
			PluginProcessor pluginProcessor = new PluginProcessor(webConfig
					.getContainerAdapter());
			pluginProcessor.invokeInit(webConfig.getServletContext(), webConfig
					.getWebApplication());
			this.webConfig = webConfig;
			this.pluginProcessor = pluginProcessor;
		} catch (Throwable t) {
			throw new ServletException(t);
		}
		logger.log("DTDT0044");
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke {@link T2Filter}.
	 * 
	 * There are several phases to execute for incoming request defined by
	 * {@link Plugin} and {@link PluginProcessor}.The context object through
	 * request processing is {@link WebContext} which is scoped the
	 * filter#doFilter method only.
	 * 
	 * If T2 should handle the request, the framework finds
	 * {@link ActionInvoker#invoke(ActionInvokingContext)} to invoke action
	 * methods with some page instance and action method finding logic.
	 * 
	 * After invoking, the result, which is {@link Navigation}, should be
	 * executed as {@link Navigation#execute(WebContext)}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * {@link T2Filter}を実行します．
	 * 
	 * 送信されてきたリクエストを処理するのに、T2は{@link Plugin}と{@link PluginProcessor}
	 * で定義されたフェーズにそって実行します． リクエストが処理されている間のコンテキストオブジェクトは、{@link WebContext}
	 * で、必要な情報の全てが入っています.
	 * 
	 * もしT2が処理すべきリクエストだった場合、T2は
	 * {@link ActionInvoker#invoke(ActionInvokingContext)}
	 * を呼び出し、Pageのインスタンスとアクションメソッドを探し出して実行します． 実行が終わると、結果は{@link Navigation}
	 * の形で返ってくるので、{@link Navigation#execute(WebContext)}
	 * で次の遷移先とレスポンスへの出力方法を確定させます.
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @see org.t2framework.confeito.plugin.Plugin
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		final WebConfiguration webConfig = getWebConfig();
		final PluginProcessor pluginProcessor = getPluginProcessor();
		webConfig.setupRequestAndResponse(req, res);
		pluginProcessor.beginRequest(req, res);
		WebContext context = null;
		Navigation navigation = null;
		try {
			req = pluginProcessor.createRequest(req, res);
			res = pluginProcessor.createResponse(req, res);
			final WebApplication webapp = webConfig.getWebApplication();
			final FilterConfig filterConfig = webConfig.getFilterConfig();
			context = webapp.createContext(req, res, chain, filterConfig);
			if (isExcludeRequest(req)) {
				handleNotT2Request(context);
				return;
			}
			navigation = invokeAction(context, webConfig, pluginProcessor);
			handleNavigation(navigation, context, pluginProcessor);
		} catch (Throwable t) {
			handleException(t, context, navigation);
		} finally {
			try {
				pluginProcessor.endRequest(req, res);
			} finally {
				webConfig.endRequest();
				WebContext.clear();
			}
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke actions.
	 * </p>
	 * <#else>
	 * <p>
	 * アクションを実行します.
	 * 
	 * 実行するアクションは{@link #getActionInvokerList()}によって返された{@link ActionInvoker}
	 * 達によって一つずつ処理が進みます．
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 * @param webConfig
	 * @param pluginProcessor
	 * @return
	 */
	protected Navigation invokeAction(final WebContext context,
			final WebConfiguration webConfig,
			final PluginProcessor pluginProcessor) {
		Navigation navigation = null;
		for (ActionInvoker invoker : getActionInvokerList()) {
			final ActionInvokingContext invokingContext = new ActionInvokingContextImpl(
					context, webConfig, pluginProcessor);
			navigation = invoker.invoke(invokingContext);
			if (navigation != null) {
				logger.log("DTDT0045", new Object[] { navigation });
				break;
			}
		}
		return navigation;
	}

	protected void handleNavigation(Navigation navigation, WebContext context,
			PluginProcessor pluginProcessor) throws Throwable {
		if (navigation != null) {
			executeNavigation(navigation, context, pluginProcessor);
		} else {
			handleNotT2Request(context);
		}
	}

	protected void executeNavigation(Navigation navigation, WebContext context,
			PluginProcessor processor) throws Throwable {
		try {
			processor.beforeNavigation(context);
			navigation.execute(context);
		} finally {
			processor.afterNavigation(context);
		}
	}

	protected void handleException(Throwable t, WebContext context,
			Navigation result) throws ServletException {
		getWebConfig().getGlobalExceptionHandler().handleException(t, context,
				result);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Destroy {@link T2Filter}.
	 * {@link PluginProcessor#destroy(javax.servlet.ServletContext, WebApplication)}
	 * and {@link WebConfiguration#destroy()} is executed at this time.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public void destroy() {
		getPluginProcessor().destroy(webConfig.getServletContext(),
				webConfig.getWebApplication());
		this.webConfig.destroy();
		WebContext.clear();
		this.webConfig = null;
		this.pluginProcessor = null;
		logger.log("DTDT0046");
	}

	protected boolean isExcludeRequest(HttpServletRequest req) {
		final String uri = req.getRequestURI();
		final String[] excludes = webConfig.getExcludeResources();
		for (String exclude : excludes) {
			if (uri.endsWith(exclude)) {
				return true;
			}
		}
		return false;
	}

	protected void handleNotT2Request(WebContext context) throws Exception {
		getOtherRequestNavigation().execute(context);
	}

	protected Navigation getOtherRequestNavigation() throws Exception {
		return PassThrough.pass();
	}

	protected WebConfiguration getWebConfig() {
		return webConfig;
	}

	protected PluginProcessor getPluginProcessor() {
		return pluginProcessor;
	}

	protected List<? extends ActionInvoker> getActionInvokerList() {
		initActionInvokerList();
		return actionInvokerList;
	}

	protected void initActionInvokerList() {
		if (actionInvokerList != null) {
			return;
		}
		synchronized (this) {
			if (actionInvokerList != null) {
				return;
			}
			final Map<String, PageComponent> pageDescMap = getWebConfig()
					.getWebApplication().createPageDescMap();
			List<? extends ActionInvoker> invokerList = getActionInvokerFactory()
					.createActionInvoker(getWebConfig());
			Map<String, PageComponent> unmodifiedMap = Collections
					.unmodifiableMap(pageDescMap);
			for (ActionInvoker invoker : invokerList) {
				invoker.initialize(getWebConfig(), unmodifiedMap);
			}
			this.actionInvokerList = invokerList;
		}
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link ActionInvokerFactory}.This method must not return null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return action invoker factory
	 */
	protected ActionInvokerFactory getActionInvokerFactory() {
		ActionInvokerFactory factory = getCustomActionInvokerFactory();
		if (factory == null) {
			factory = getDefaultActionInvokerFactory();
		}
		return factory;
	}

	protected ActionInvokerFactory getCustomActionInvokerFactory() {
		if (getWebConfig().hasFrameworkComponent(ActionInvokerFactory.class)) {
			try {
				return getWebConfig().getFrameworkComponent(
						ActionInvokerFactory.class);
			} catch (Throwable ignore) {
			}
		}
		return null;
	}

	protected ActionInvokerFactory getDefaultActionInvokerFactory() {
		return ActionInvokerFactoryImpl.INSTANCE;
	}
}
