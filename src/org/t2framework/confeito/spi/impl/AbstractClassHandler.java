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
package org.t2framework.confeito.spi.impl;

import java.lang.reflect.Modifier;

import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.Reflections.ClassUtil;
import org.t2framework.confeito.util.Traversal.ClassHandler;

/**
 * {@link ClassHandler} base class.This class provides basic checks whether
 * given package name and short class name is eligible to page class.
 * 
 * @author shot
 * 
 */
public abstract class AbstractClassHandler implements ClassHandler {

	/**
	 * The logger for this class.
	 */
	protected static Logger logger = Logger
			.getLogger(AbstractClassHandler.class);

	/**
	 * Verify class.Acceptable one will be the class which is public and is not
	 * abstract or interface.
	 */
	@Override
	public void processClass(String packageName, String shortClassName) {
		logger.log("ITDT0028", new Object[] { packageName, shortClassName });
		if (packageName == null || shortClassName == null) {
			logger
					.log("ITDT0029",
							new Object[] { packageName, shortClassName });
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(packageName.replace('/', '.'));
		builder.append(".");
		builder.append(shortClassName);
		final String classname = new String(builder);
		Class<Object> c = ClassUtil.forNameNoException(classname);
		if (c == null) {
			logger
					.log("ITDT0037",
							new Object[] { packageName, shortClassName });
			return;
		}
		if (c.isAnnotation() || c.isEnum()) {
			logger
					.log("ITDT0030",
							new Object[] { packageName, shortClassName });
			return;
		}
		final int mod = c.getModifiers();
		if (Modifier.isInterface(mod) || Modifier.isAbstract(mod)
				|| Modifier.isPublic(mod) == false) {
			logger
					.log("ITDT0031",
							new Object[] { packageName, shortClassName });
			return;
		}
		processPage(c);
	}

	/**
	 * Process further Page class check.And store to page repository.
	 * 
	 * @param cd
	 */
	protected abstract void processPage(Class<?> cd);

}
