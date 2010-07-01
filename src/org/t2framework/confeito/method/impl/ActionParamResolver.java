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
import java.util.regex.Pattern;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.internal.ActionUtil;
import org.t2framework.confeito.internal.VariablesUtil;
import org.t2framework.confeito.method.AbstractActionMethodResolver;
import org.t2framework.confeito.util.StringUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * ActionParamResolver is a concrete class of ActionMethodResolver. This class
 * handles &#064;ActionParam.
 * </p>
 * <#else>
 * <p>
 * {@code ActionParamResolver} は&#064;ActionParamを処理するリゾルバです．
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.method.ActionMethodResolver
 * 
 */
public class ActionParamResolver extends AbstractActionMethodResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * True if request parameter contains template key.Ajax request never
	 * reaches true.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストパラメータがテンプレートのキーを持つかチェックします.例外的にAjaxリクエスト(
	 * {@link ActionUtil#isAjaxRequest(ActionContext, MethodDesc)}
	 * にマッチするリクエスト)は取り扱いません.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isMatch(final ActionContext actionContext,
			final Annotation annotation, final Method targetMethodDesc) {
		if (ActionUtil.isAjaxRequest(actionContext, targetMethodDesc)) {
			return false;
		}
		final String pair = getKeyValuePair(annotation, targetMethodDesc);
		final String keyPart = getKeyPart(pair);
		final String valuePart = getValuePart(pair);
		final Pattern keyPattern = VariablesUtil.toPattern(keyPart);
		final Pattern valuePattern = (valuePart == null) ? null : VariablesUtil
				.toPattern(valuePart);
		final Request request = actionContext.getRequest();
		for (String paramKey : request.getParameterNames()) {
			if (keyPattern.matcher(paramKey).matches() == false) {
				continue;
			}
			String[] values = request.getParameters(paramKey);
			if (valuePattern != null) {
				for (int i = 0; i < values.length; i++) {
					if (valuePattern.matcher(values[i]).matches()) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Resolve action method by request parameter.
	 * </p>
	 * <#else>
	 * <p>
	 * POSTされたリクエストパラメータとアノテーションで指定されたテンプレートとマッチングを行い、マッチした場合このテンプレートの情報を保管し、
	 * アクションメソッドの呼び出しに備えます.
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void resolve(ActionContext actionContext, Annotation annotation,
			Method targetMethodDesc) {
		final String keyValuePair = getKeyValuePair(annotation,
				targetMethodDesc);
		final String keyPart = getKeyPart(keyValuePair);
		final String valuePart = getValuePart(keyValuePair);
		resolveActionParameters(actionContext, keyPart, valuePart);
	}

	protected void resolveActionParameters(ActionContext actionContext,
			String keyPart, String valuePart) {
		final Request request = actionContext.getRequest();
		final Pattern keyPattern = VariablesUtil.toPattern(keyPart);
		final Pattern valuePattern = (valuePart == null) ? null : VariablesUtil
				.toPattern(valuePart);
		for (String paramKey : request.getParameterNames()) {
			if (keyPattern.matcher(paramKey).matches() == false) {
				continue;
			}
			String[] values = request.getParameters(paramKey);
			appendRequestParameterVariables(actionContext, paramKey, keyPart,
					valuePart, valuePattern, values);
		}
	}

	protected void appendRequestParameterVariables(ActionContext actionContext,
			String paramKey, String keyPart, String valuePart,
			Pattern valuePattern, String[] values) {
		if (valuePattern != null) {
			for (int i = 0; i < values.length; i++) {
				final String s = values[i];
				if (valuePattern.matcher(s).matches()) {
					final Map<String, String> keyVariables = VariablesUtil
							.createVarMap(keyPart, paramKey);
					appendVariables(actionContext, keyVariables);

					final Map<String, String> valueVariables = VariablesUtil
							.createVarMap(valuePart, s);
					appendVariables(actionContext, valueVariables);
					return;
				}
			}
		} else {
			final Map<String, String> keyVariables = VariablesUtil
					.createVarMap(keyPart, paramKey);
			appendVariables(actionContext, keyVariables);
		}
	}

	protected String getKeyValuePair(Annotation annotation,
			Method targetMethodDesc) {
		final ActionParam actionParam = (ActionParam) annotation;
		final String pair = actionParam.value();
		return (StringUtil.isEmpty(pair) == false) ? pair : targetMethodDesc
				.getName();
	}

	protected String getKeyPart(String value) {
		int eq = value.indexOf("=");
		if (eq != -1) {
			value = value.substring(0, eq);
		}
		return value.trim();
	}

	protected String getValuePart(String value) {
		int eq = value.indexOf("=");
		if (eq != -1) {
			return value.substring(eq + 1).trim();
		} else {
			return null;
		}
	}

	protected void appendVariables(ActionContext actionContext,
			Map<String, String> varMap) {
		for (Entry<String, String> entrySet : varMap.entrySet()) {
			actionContext.addVariables(entrySet.getKey(), entrySet.getValue());
		}
	}

}
