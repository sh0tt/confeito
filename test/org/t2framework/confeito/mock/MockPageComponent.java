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
package org.t2framework.confeito.mock;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Ajax;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Reflections.PackageUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * Mock implementation of {@link PageDesc}.
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
public class MockPageComponent extends PageComponent {

	public static Set<Class<? extends Annotation>> actionAnnotationSet = new HashSet<Class<? extends Annotation>>();

	static {
		actionAnnotationSet.add(ActionParam.class);
		actionAnnotationSet.add(ActionPath.class);
		actionAnnotationSet.add(GET.class);
		actionAnnotationSet.add(POST.class);
		actionAnnotationSet.add(Ajax.class);
		actionAnnotationSet.add(Default.class);
	}

	public MockPageComponent(final Class<?> clazz, final String templatePath) {
		super(PackageUtil.getPackageName(Assertion.notNull(clazz)), clazz
				.getSimpleName(), new Component<Object>(clazz),
				new UrlTemplate(templatePath, true), actionAnnotationSet);
	}
}
