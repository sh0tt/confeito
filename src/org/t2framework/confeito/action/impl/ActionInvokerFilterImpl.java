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

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ActionFilter;
import org.t2framework.confeito.action.ActionFilterChain;
import org.t2framework.confeito.action.ActionInvokingContext;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.exception.ActionMethodInvocationException;
import org.t2framework.confeito.exception.IllegalAccessRuntimeException;
import org.t2framework.confeito.exception.InvocationTargetRuntimeException;
import org.t2framework.confeito.internal.NavigationUtil;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.PrintableUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * ActionInvokerFilterImpl is an action filter class for invoking action method.
 * Before to do so, two things should be done: create page instance, and prepare
 * method arguments.By default, these are basically handled by
 * {@link PageCreationFilterImpl}, and
 * {@link ActionArgumentsPreparationFilterImpl}.
 * </p>
 * <#else>
 * <p>
 * ActionInvokerFilterImplはアクションメソッドを呼び出すためのActionFilterです.このフィルターを実行する前に、
 * Pageクラスのインスタンスと、アクションメソッドの 引数の準備をしておく必要があります.通常、これらの処理は
 * {@link PageCreationFilterImpl}と{@link ActionArgumentsPreparationFilterImpl}
 * で行います.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionInvokerFilterImpl implements ActionFilter {

	protected static Logger logger = Logger
			.getLogger(ActionInvokerFilterImpl.class);

	/**
	 * <#if locale="en">
	 * <p>
	 * Invoke an action with prepared page instance and action arguments passed
	 * by {@link ActionInvokingContext}.Before to do that, save request
	 * parameters for restore and invoke
	 * {@link PluginProcessor#beforeActionInvoke(ActionContext, MethodDesc, Object, Object[])}
	 * for extension point.
	 * 
	 * After invocation,
	 * {@link PluginProcessor#afterActionInvoke(ActionContext, MethodDesc, Object, Object[])}
	 * invokes whether the result of invoking action method is done normally or
	 * causes error.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link ActionInvokingContext}
	 * から渡されるPageクラスのインスタンスとメソッドの引数を使って、アクションメソッドを呼び出します.
	 * メソッド呼び出しを呼び出す直前の処理として、リクエストパラメータの保存と、拡張ポイントである
	 * {@link PluginProcessor#beforeActionInvoke(ActionContext, MethodDesc, Object, Object[])}
	 * を呼び出します.
	 * 
	 * メソッド呼び出し後、
	 * {@link PluginProcessor#afterActionInvoke(ActionContext, MethodDesc, Object, Object[])}
	 * を 呼び出します。この呼び出しは、アクションメソッドが正常終了してもエラーが発生しても行われます.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionFilter#invoke(org.t2framework.confeito.action.ActionInvokingContext,
	 *      org.t2framework.t2.contexts.PageComponent,
	 *      org.t2framework.confeito.action.ActionFilterChain)
	 * @param invokingContext
	 * @param pageDesc
	 * @param chain
	 */
	@Override
	public void invoke(ActionInvokingContext invokingContext,
			PageComponent pageDesc, ActionFilterChain chain)
			throws ActionMethodInvocationException, RuntimeException {
		final ActionContext actionContext = invokingContext.getActionContext();
		final Method actionMethod = actionContext.getTargetMethodDesc();
		final Object page = invokingContext.getPage();
		final Object[] args = invokingContext.getActionArguments();
		final PluginProcessor pluginProcessor = invokingContext
				.getPluginProcessor();

		saveRequestParameters(actionContext.getRequest());
		Navigation ret = null;
		try {
			ret = pluginProcessor.beforeActionInvoke(actionContext,
					actionMethod, page, args);
			if (NavigationUtil.isNavigateImmediately(ret)) {
				invokingContext.setResultNavigation(ret);
				return;
			}
			ret = invokeAction(actionContext, page, actionMethod, args);
		} finally {
			Navigation n = pluginProcessor.afterActionInvoke(actionContext,
					actionMethod, page, args, ret);
			if (NavigationUtil.isNavigateImmediately(n)) {
				ret = n;
			}
		}
		if (ret != null) {
			invokingContext.setResultNavigation(ret);
		}
		chain.invokeChain(invokingContext, pageDesc);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Invoke action internally.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param actionContext
	 * @param page
	 * @param methodDesc
	 * @param args
	 * @return
	 * @throws ActionMethodInvocationException
	 * @throws RuntimeException
	 */
	protected Navigation invokeAction(final ActionContext actionContext,
			final Object page, final Method methodDesc, Object[] args)
			throws ActionMethodInvocationException, RuntimeException {
		final String requestURI = actionContext.getRequest().getRequestURI();
		logInvokeAction("DTDT0047", requestURI, methodDesc, args);
		try {
			return (Navigation) methodDesc.invoke(page, args);
		} catch (Throwable t) {
			logInvokeAction("DTDT0049", requestURI, methodDesc, args, t);
			throw unwrapMethodInvocationRuntimeException(t);
		} finally {
			logInvokeAction("DTDT0048", requestURI, methodDesc, args);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Unwrap {@link InvocationTargetRuntimeException} or
	 * {@link IllegalAccessRuntimeException} and return
	 * {@link ActionMethodInvocationException}. If {@link RuntimeException}
	 * comes, just return this exception.If checked exception, return
	 * {@link ActionMethodInvocationException}.
	 * </p>
	 * <#else>
	 * <p>
	 * 引数の例外が{@link InvocationTargetRuntimeException}もしくは
	 * {@link IllegalAccessRuntimeException}でラップされていた場合にそれを外し、
	 * {@link ActionMethodInvocationException}として例外を返します. 引数の例外が
	 * {@link RuntimeException}の場合は、そのままその例外を返します. また引数の例外がそれ以外の宣言例外の場合、
	 * {@link ActionMethodInvocationException}を返します.
	 * </p>
	 * </#if>
	 * 
	 * @param t
	 * @return runtime exception to be thrown
	 * @throws ActionMethodInvocationException
	 * @throws RuntimeException
	 */
	protected RuntimeException unwrapMethodInvocationRuntimeException(
			Throwable t) throws ActionMethodInvocationException,
			RuntimeException {
		if (t instanceof InvocationTargetRuntimeException
				|| t instanceof IllegalAccessRuntimeException) {
			return new ActionMethodInvocationException(t.getCause());
		} else if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new ActionMethodInvocationException(t);
		}
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Logging invoke action information such as requested URI, requested
	 * method, and its args and so on.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param messageCode
	 * @param requestURI
	 * @param methodDesc
	 * @param methodArgs
	 * @param t
	 */
	protected void logInvokeAction(String messageCode, String requestURI,
			Method methodDesc, Object[] methodArgs, Throwable t) {
		final String methodName = methodDesc.getDeclaringClass()
				.getSimpleName()
				+ "#" + methodDesc.getName();
		final String argsString = PrintableUtil.toPrintableString(methodArgs);
		logger.log(messageCode, new Object[] { requestURI, methodName,
				argsString, t });
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Loggin invoke action information.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param messageCode
	 * @param requestURI
	 * @param methodDesc
	 * @param args
	 */
	protected void logInvokeAction(String messageCode, String requestURI,
			Method methodDesc, Object[] args) {
		logInvokeAction(messageCode, requestURI, methodDesc, args, null);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Save request parameters as request attributes.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param request
	 */
	protected void saveRequestParameters(Request request) {
		for (String paramKey : request.getParameterNames()) {
			request.setAttribute(paramKey, request.getParameter(paramKey));
		}
	}

}
