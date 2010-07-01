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
package org.t2framework.confeito.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.urltemplate.UrlTemplateFactory;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * A base class of {@link ActionMethodResolver}.
 * 
 * </p>
 * <#else>
 * <p>
 * {@link ActionMethodResolver}のベースクラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class AbstractActionMethodResolver implements ActionMethodResolver {

	protected UrlTemplateFactory factory = new UrlTemplateFactory();

	/**
	 * <#if locale="en">
	 * <p>
	 * Set {@link UrlTemplateFactory}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link UrlTemplateFactory}をセットします.
	 * </p>
	 * </#if>
	 * 
	 * @param urlTemplateFactory
	 */
	public void setUrlTemplateFactory(UrlTemplateFactory factory) {
		this.factory = factory;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link UrlTemplateFactory}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link UrlTemplateFactory}を返します.
	 * </p>
	 * </#if>
	 * 
	 * @return url template factory
	 */
	public UrlTemplateFactory getUrlTemplateFactory() {
		return factory;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Base implementation of isMatch method, just return false.
	 * </p>
	 * <#else>
	 * <p>
	 * falseを返すisMatch()の実装.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isMatch(ActionContext actionContext, Annotation annotation,
			Method targetMethod) {
		return false;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty implementation of postResolve method.
	 * </p>
	 * <#else>
	 * <p>
	 * postResolveメソッドの実装.処理を行いません.
	 * </p>
	 * </#if>
	 */
	@Override
	public void postResolve(ActionContext actionContext, Annotation annotation,
			Method targetMethod) {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty implementation of preResolve method.
	 * </p>
	 * <#else>
	 * <p>
	 * preResolveメソッドの実装.処理を行いません.
	 * </p>
	 * </#if>
	 */
	@Override
	public void preResolve(ActionContext actionContext, Annotation annotation,
			Method targetMethod) {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty implementation of resolve method.
	 * </p>
	 * <#else>
	 * <p>
	 * resolveメソッドの実装.処理を行いません.
	 * </p>
	 * </#if>
	 */
	@Override
	public void resolve(ActionContext actionContext, Annotation annotation,
			Method targetMethod) {
	}

	protected UrlTemplate createTemplate(final String templatePath) {
		Assertion.notNull(templatePath);
		return factory.getUrlTemplate(templatePath);
	}

}
