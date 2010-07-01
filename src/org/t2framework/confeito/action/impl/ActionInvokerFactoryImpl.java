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

import java.util.Arrays;
import java.util.List;

import org.t2framework.confeito.action.ActionInvoker;
import org.t2framework.confeito.action.ActionInvokerFactory;
import org.t2framework.confeito.contexts.WebConfiguration;

/**
 * 
 * <#if locale="en">
 * <p>
 * Normal {@code ActionInvokerFactory}.This factory does not create {@code
 * ActionInvoker} which handle AMF or SOAP request.
 * </p>
 * <#else>
 * <p>
 * 通常利用される{@code ActionInvokerFactory}.このファクトリーはAMFやSOAPリクエストを扱う{@code
 * ActionInvoker}は生成しません.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionInvokerFactoryImpl implements ActionInvokerFactory {

	public static ActionInvokerFactoryImpl INSTANCE = new ActionInvokerFactoryImpl();

	/**
	 * <#if locale="en">
	 * <p>
	 * Create list of {@link ActionInvoker}, particurally including
	 * {@link ActionInvokerImpl}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.action.ActionInvokerFactory#createActionInvoker(org.t2framework.confeito.contexts.WebConfiguration)
	 * @param webConfig
	 * @return List<? extends ActionInvoker>
	 */
	@Override
	public List<ActionInvoker> createActionInvoker(WebConfiguration webConfig) {
		return Arrays.<ActionInvoker> asList(new ActionInvokerImpl());
	}
}
