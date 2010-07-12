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
import java.util.Set;

import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.model.ActionMethod;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.model.Ignore;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * {@link ActionMethod} utility.It is internal utility.
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
public class ActionMethodUtil {

	/**
	 * Create {@link ActionMethod} by {@link BeanDesc} and action annotation
	 * set.
	 * 
	 * @param pbd
	 * @param actionAnnotationSet
	 * @return
	 */
	public static ActionMethod createActionMethod(Component pbd,
			Set<Class<? extends Annotation>> actionAnnotationSet) {
		Assertion.notNull(pbd);
		Assertion.notNull(actionAnnotationSet);
		ActionMethod amd = new ActionMethod(actionAnnotationSet);
		for (Method md : pbd.getMethods()) {
			if (Ignore.isIgnorableMethod(md)) {
				continue;
			}
			setupActionMethodDesc(md, amd, actionAnnotationSet);
		}
		return amd;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Setting up {@link ActionMethod}.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param md
	 * @param amd
	 * @param actionAnnotationSet
	 */
	protected static void setupActionMethodDesc(Method md, ActionMethod amd,
			Set<Class<? extends Annotation>> actionAnnotationSet) {
		for (Class<? extends Annotation> annotationClass : actionAnnotationSet) {
			if (md.getAnnotation(annotationClass) != null) {
				amd.addTargetMethod(md);
				return;
			}
		}
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Find default method which is annotated by {@link Default} from given
	 * {@link BeanDesc}.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param beanDesc
	 * @return
	 */
	public static Method resolveDefaultMethod(Component component) {
		Assertion.notNull(component);
		for (Method md : component.getMethods()) {
			if (Ignore.isIgnorableMethod(md)) {
				continue;
			}
			if (md.getAnnotation(Default.class) != null) {
				return md;
			}
		}
		return null;
	}
}
