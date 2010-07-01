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

import java.util.HashMap;
import java.util.Map;

import org.t2framework.confeito.action.ErrorInfo;

/**
 * 
 * <#if locale="en">
 * <p>
 * An implementation of {@link ErrorInfo}.
 * </p>
 * <#else>
 * <p>
 * {@link ErrorInfo}の実装クラス.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ErrorInfoImpl implements ErrorInfo {

	private static final long serialVersionUID = 1L;

	protected Map<String, Throwable> errors = new HashMap<String, Throwable>();

	/**
	 * <#if locale="en">
	 * <p>
	 * Add error info.
	 * </p>
	 * <#else>
	 * <p>
	 * エラー情報を追加します.
	 * </p>
	 * </#if>
	 */
	@Override
	public void addErrorInfo(String key, Throwable t) {
		errors.put(key, t);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * True if there is an error.
	 * </p>
	 * <#else>
	 * <p>
	 * エラーがある場合、trueを返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean hasError() {
		return errors.isEmpty() == false;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get all errors as map.
	 * </p>
	 * <#else>
	 * <p>
	 * すべてのエラー情報をMapに入れて返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public Map<String, Throwable> getErrorsAsMap() {
		return errors;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get all errors as array.
	 * </p>
	 * <#else>
	 * <p>
	 * すべてのエラー情報を配列に入れて返します.
	 * </p>
	 * </#if>
	 * 
	 */
	@Override
	public Throwable[] getErrors() {
		return errors.values().toArray(new Throwable[0]);
	}

}
