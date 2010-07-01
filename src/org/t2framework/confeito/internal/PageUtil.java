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

import javax.rmi.CORBA.ClassDesc;

import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.StringUtil;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * This utility class is for what is page class.
 * 
 * Internal only, so we will change this utility any time.Application developer
 * is expected not to use this class directly.
 * 
 * @author shot
 * 
 */
public class PageUtil {

	/**
	 * True if the class is page class of T2 framework.
	 * 
	 * @param clazz
	 * @return true if the class is page class.
	 */
	public static boolean isPageClass(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		final Page page = clazz.getAnnotation(Page.class);
		return page != null;
	}

	/**
	 * Convert {@link ClassDesc} to {@link UrlTemplate}.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return UrlTemplate created by {@link Page} annotation value.
	 */
	public static <T> UrlTemplate toUrlTemplate(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		final Page page = clazz.getAnnotation(Page.class);
		if (page == null) {
			return null;
		}
		String path = page.value();
		if (Page.DEFAULT_STR.equals(path)) {
			String name = ClassUtil.getShortClassName(clazz);
			path = StringUtil.decapitalize(name);
		}
		return new UrlTemplate(path, true);
	}
}
