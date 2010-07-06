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
package org.t2framework.confeito.spi;

import org.t2framework.confeito.action.ErrorInfo;
import org.t2framework.confeito.annotation.Form;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.Component;

/**
 * <#if locale="en">
 * <p>
 * {@code FormResolver} interface is to resolve mapping between request and
 * object.
 * 
 * Since 0.6.3, API change for some reason from parameter Object to
 * BeanDesc<Object>.
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 */
public interface FormResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * Resolve mapping from request to object.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param form
	 *            annotation
	 * @param context
	 *            object with request
	 * @param target
	 *            object instance
	 * @param errorInfo
	 */
	void resolve(Form form, WebContext context, Component target,
			ErrorInfo errorInfo);
}
