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
package org.t2framework.confeito.util;

import static org.t2framework.confeito.Constants.*;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <#if locale="en">
 * <p>
 * {@link Traversal} collects class information and pass to the
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
public class Traversal {

	public interface ClassHandler {
		void processClass(String packageName, String shortClassName);
	}

	protected Traversal() {
	}

	public static void traverse(File rootDir, String rootPackage,
			ClassHandler handler) {
		Assertion.notNull(rootDir);
		Assertion.notNull(handler);
		traverseFromFileSystem(rootDir, rootPackage, handler);
	}

	public static void traverseFromFileSystem(File rootDir, String rootPackage,
			ClassHandler handler) {
		Assertion.notNull(rootDir);
		Assertion.notNull(handler);
		final File packageDir = getPackageDir(rootDir, rootPackage);
		if (packageDir.exists()) {
			traverseFileSystem(packageDir, rootPackage, handler);
		}
	}

	public static void traverseFromJarFile(final JarFile jarFile,
			final ClassHandler handler) {
		Assertion.notNull(jarFile);
		Assertion.notNull(handler);
		doTraverseJar(jarFile, null, handler);
	}

	protected static void doTraverseJar(final JarFile jarFile,
			final String notUse, final ClassHandler handler) {
		final boolean hasWarExtension = jarFile.getName().endsWith(WAR_SUFFIX);
		final Enumeration<JarEntry> e = jarFile.entries();
		while (e.hasMoreElements()) {
			final JarEntry entry = e.nextElement();
			final String entryName = entry.getName().replace('\\', '/');
			if (entryName.endsWith(CLASS_SUFFIX)) {
				final int startPos = (hasWarExtension && entryName
						.startsWith(WEB_INF_CLASSES_PATH)) ? WEB_INF_CLASSES_PATH
						.length()
						: 0;
				final String className = entryName.substring(startPos,
						entryName.length() - CLASS_SUFFIX.length()).replace(
						'/', '.');
				final int pos = className.lastIndexOf('.');
				final String packageName = (pos == -1) ? null : className
						.substring(0, pos);
				final String shortClassName = (pos == -1) ? className
						: className.substring(pos + 1);
				handler.processClass(packageName, shortClassName);
			}
		}
	}

	private static void traverseFileSystem(File packageDir, String packageName,
			ClassHandler handler) {
		File[] files = packageDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
			final String filename = file.getName();
			if (file.isDirectory()) {
				final String concatName = concatName(packageName, filename);
				traverseFileSystem(file, concatName, handler);
			} else if (filename.endsWith(CLASS_SUFFIX)) {
				final String shortClassName = filename.substring(0, filename
						.lastIndexOf(CLASS_SUFFIX));
				handler.processClass(packageName, shortClassName);
			}
		}
	}

	private static File getPackageDir(final File rootDir,
			final String rootPackage) {
		File packageDir = rootDir;
		if (rootPackage != null) {
			final String[] names = rootPackage.split("\\.");
			for (int i = 0; i < names.length; i++) {
				packageDir = new File(packageDir, names[i]);
			}
		}
		return packageDir;
	}

	private static String concatName(String s1, String s2) {
		if (StringUtil.isEmpty(s1)) {
			return s2;
		}
		return s1 + '.' + s2;
	}

}
