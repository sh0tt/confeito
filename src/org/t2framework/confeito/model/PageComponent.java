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
package org.t2framework.confeito.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.internal.ActionMethodUtil;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Reflections.ClassUtil;
import org.t2framework.confeito.util.Reflections.PackageUtil;

/**
 * <#if locale="en">
 * <p>
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class PageComponent {

	protected String packageName;

	protected String pageName;

	protected Method defaultMethod;

	protected Class<?> pageClass;

	protected ActionMethod actionMethodDesc;

	protected UrlTemplate template;

	protected final Set<Class<? extends Annotation>> actionAnnotationSet;

	public PageComponent(final Class<?> clazz, final UrlTemplate template,
			Set<Class<? extends Annotation>> actionAnnotationSet) {
		this(PackageUtil.getPackageName(Assertion.notNull(clazz)), ClassUtil
				.getShortClassName(clazz), new Component<Object>(clazz),
				Assertion.notNull(template), actionAnnotationSet);
	}

	public PageComponent(final String packageName, final String shortClassName,
			final Component<Object> component, final UrlTemplate template,
			final Set<Class<? extends Annotation>> actionAnnotationSet) {
		Assertion.notNulls(component, packageName, shortClassName, template);
		this.packageName = Assertion.notNull(packageName);
		this.pageName = Assertion.notNull(shortClassName);
		this.template = template;
		this.actionAnnotationSet = Assertion.notNull(actionAnnotationSet);
		initActionMethodDesc(component);
	}

	protected void initActionMethodDesc(Component<Object> component) {
		this.pageClass = component.getComponentClass();
		this.actionMethodDesc = ActionMethodUtil.createActionMethodDesc(
				component, actionAnnotationSet);
		final Method defaultMethod = ActionMethodUtil
				.resolveDefaultMethodDesc(component);
		if (defaultMethod != null) {
			this.actionMethodDesc.addDefaultMethodDesc(defaultMethod);
			this.defaultMethod = defaultMethod;
		}
		this.actionMethodDesc.freeze();
	}

	public Method getDefaultMethodDesc() {
		return defaultMethod;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getPageName() {
		return pageName;
	}

	public Class<?> getPageClass() {
		return pageClass;
	}

	public ActionMethod getActionMethodDesc() {
		return actionMethodDesc;
	}

	public boolean hasDefaultMethodDesc() {
		return defaultMethod != null;
	}

	public UrlTemplate getUrlTemplate() {
		return template;
	}

	public String getPageTemplatePath() {
		return template.getTemplatePath();
	}

	public Annotation getDefaultAnnotation() {
		return getDefaultMethodDesc().getAnnotation(Default.class);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("@" + this.hashCode());
		builder.append("[");
		builder.append("packageName = " + packageName + ", ");
		builder.append("pageName = " + pageName + ", ");
		builder.append("defaultMethod = " + defaultMethod + ", ");
		builder.append("pageClass = " + pageClass + ", ");
		builder.append("defaultMethod = " + defaultMethod + ", ");
		builder.append("template = " + template + ", ");
		builder.append("]");
		return new String(builder);
	}

}
