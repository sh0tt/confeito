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
package org.t2framework.confeito.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.internal.ActionUtil;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.method.AbstractActionMethodResolver;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.StringUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * ActionPathResolver is a concrete class of ActionMethodResolver. It handles
 * &#064;ActionPath.
 * 
 * {@code ActionPathAnnotationResolver} does not handle ajax request for
 * anytime.
 * </p>
 * <#else>
 * <p>
 * {@code ActionPathResolver} は&#064;ActionPathを処理するリゾルバです．
 * isMatch()で該当URLにマッチした場合、&#064;ActionPathかメソッド名を使用して、
 * 該当URLでのリクエストが来た場合対象メソッドを起動します．
 * 
 * {@code ActionPathResolver}ではAjaxリクエストは扱いません．
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionPathResolver extends AbstractActionMethodResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * True if requested url and {@link ActionPath#value()} match but ajax
	 * request is exceptional.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストされたURLと{@link ActionPath#value()} がマッチした場合に、trueを返します.例外的にAjaxリクエスト(
	 * {@link ActionUtil#isAjaxRequest(ActionContext, MethodDesc)}
	 * にマッチするリクエスト)は取り扱いません.
	 * </p>
	 * </#if>
	 * 
	 * @see ActionUtil#isAjaxRequest(ActionContext, MethodDesc)
	 */
	@Override
	public boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc) {
		if (ActionUtil.isAjaxRequest(actionContext, targetMethodDesc)) {
			return false;
		}
		final String actionPath = getActionPath(annotation, targetMethodDesc);
		final UrlTemplate template = createTemplate(actionPath);
		final Request request = actionContext.getRequest();
		final String pageTemplatePath = actionContext.getPageTemplatePath();
		String testPath = PathUtil.getActionPath(request, pageTemplatePath);
		if (template.match(testPath)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Resolver method by url matching.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストされたURLとテンプレートをあわせ、マッチした場合このテンプレートの情報を保管し、 アクションメソッドの呼び出しに備えます.
	 * </p>
	 * </#if>
	 */
	@Override
	public void resolve(ActionContext actionContext, Annotation annotation,
			Method targetMethodDesc) {
		final String actionPath = getActionPath((ActionPath) annotation,
				targetMethodDesc);
		final UrlTemplate template = createTemplate(actionPath);
		final Request request = actionContext.getRequest();
		final String pageTemplatePath = actionContext.getPageTemplatePath();
		String testPath = PathUtil.getActionPath(request, pageTemplatePath);
		resolveVariables(actionContext, template, testPath);
	}

	protected void resolveVariables(ActionContext actionContext,
			UrlTemplate template, String testPath) {
		Map<String, String> varMap = template.parseUrl(testPath);
		if (varMap != null) {
			for (Entry<String, String> entry : varMap.entrySet()) {
				final String key = entry.getKey();
				final String value = entry.getValue();
				actionContext.addVariables(key, value);
			}
		}
	}

	protected String getActionPath(Annotation annotation,
			Method targetMethodDesc) {
		String actionPath = ((ActionPath) annotation).value();
		actionPath = StringUtil.isEmpty(actionPath) ? targetMethodDesc
				.getName() : actionPath;
		actionPath = PathUtil.appendStartSlashIfNeed(actionPath);
		actionPath = PathUtil.removeEndSlashIfNeed(actionPath);
		return actionPath;
	}
}
