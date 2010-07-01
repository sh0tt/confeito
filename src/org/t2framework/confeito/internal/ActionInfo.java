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
package org.t2framework.confeito.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.StringUtil;
import org.t2framework.confeito.util.Reflections.MethodUtil;

/**
 * <#if locale="en">
 * <p>
 * Internal action info class.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class ActionInfo {

	/**
	 * The logger for this class.
	 */
	protected static Logger logger = Logger.getLogger(ActionInfo.class);

	/**
	 * The target {@link MethodDesc}.
	 */
	public Method methodDesc;

	/**
	 * The target method name.
	 */
	public String methodName;

	/**
	 * Efficient annotation counts.
	 */
	public int annotationCounts;

	/**
	 * The template path
	 */
	public String templatePath;

	/**
	 * Default action or not.
	 */
	public boolean defaultAction;

	protected Set<Class<? extends Annotation>> actionAnnotationSet;

	public ActionInfo(Method methodDesc,
			Set<Class<? extends Annotation>> actionAnnotationSet) {
		this(methodDesc.getName(), methodDesc, actionAnnotationSet, false);
	}

	public ActionInfo(String methodName, Method methodDesc,
			Set<Class<? extends Annotation>> actionAnnotationSet) {
		this(methodName, methodDesc, actionAnnotationSet, false);
	}

	public ActionInfo(String methodName, Method methodDesc,
			Set<Class<? extends Annotation>> actionAnnotationSet,
			boolean defaultAction) {
		this.methodDesc = methodDesc;
		this.methodName = methodName;
		this.actionAnnotationSet = actionAnnotationSet;
		this.defaultAction = defaultAction;
		init(methodDesc);
	}

	public ActionInfo(Method methodDesc,
			Set<Class<? extends Annotation>> actionAnnotationSet,
			boolean defaultAction) {
		this(methodDesc.getName(), methodDesc, actionAnnotationSet,
				defaultAction);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Initialize this {@link ActionInfo}.
	 * 
	 * With given {@link MethodDesc}, find template path from T2 framework
	 * annotations which is for action method, and counts how many annotations
	 * exist.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param methodDesc
	 */
	protected void init(Method methodDesc) {
		int count = 0;
		List<String> paths = new ArrayList<String>();
		for (Class<? extends Annotation> c : actionAnnotationSet) {
			Annotation annotation = methodDesc.getAnnotation(c);
			if (annotation == null) {
				continue;
			}
			Method annotationMethod = getTemplatePathReturnMethod(c);
			if (isApplicableAnnotationMethod(annotationMethod)) {
				try {
					String templatePath = (String) annotationMethod.invoke(
							annotation, Constants.EMPTY_ARRAY);
					paths.add(templatePath);
				} catch (Throwable ignore) {
					logger.debug(ignore.getMessage());
				}
			}
			count++;
		}
		Collections.sort(paths, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final String pageTemplatePath2 = s2.replaceAll("\\{.*\\}", "");
				final String pageTemplatePath1 = s1.replaceAll("\\{.*\\}", "");
				return pageTemplatePath2.length() - pageTemplatePath1.length();
			}
		});
		if (paths.isEmpty()) {
			this.templatePath = this.methodName;
		} else {
			String path = paths.get(0);
			this.templatePath = (StringUtil.isEmpty(path) == false) ? path
					: this.methodName;
		}
		this.annotationCounts = count;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if the given annotation method is applicable for resolving template
	 * path.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param annotationMethod
	 * @return
	 */
	protected boolean isApplicableAnnotationMethod(Method annotationMethod) {
		return annotationMethod != null
				&& annotationMethod.getReturnType() == String.class;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get template path from the annotation class.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param c
	 * @return
	 */
	protected Method getTemplatePathReturnMethod(Class<? extends Annotation> c) {
		return MethodUtil.getDeclaredMethodNoException(c, "value",
				Constants.EMPTY_CLASS_ARRAY);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName()).append("@").append(
				this.hashCode()).append("[");
		builder.append("methodName=").append(this.methodName).append(",");
		builder.append("templatePath=").append(this.templatePath).append(",");
		builder.append("annotationCounts=").append(this.annotationCounts);
		builder.append("]");
		return new String(builder);
	}
}
