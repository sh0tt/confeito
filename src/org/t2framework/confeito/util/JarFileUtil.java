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

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;

import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * JarFile utility.
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
public class JarFileUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * No instantiation.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	private JarFileUtil() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Creare {@link JarFile} from file path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 * @return
	 */
	public static JarFile create(final String file) {
		try {
			return new JarFile(file);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Creare {@link JarFile} from {@link File}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 * @return
	 */
	public static JarFile create(final File file) {
		try {
			return new JarFile(file);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert from {@link URL} to {@link JarFile}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param jarUrl
	 * @return
	 */
	public static JarFile toJarFile(final URL jarUrl) {
		final URLConnection con = URLUtil.openConnection(jarUrl);
		if (con instanceof JarURLConnection) {
			return JarURLConnectionUtil.getJarFile((JarURLConnection) con);
		}
		return create(new File(toJarFilePath(jarUrl)));
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert from {@link URL} to jar file path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param jarUrl
	 * @return
	 */
	public static String toJarFilePath(final URL jarUrl) {
		final URL nestedUrl = URLUtil.create(jarUrl.getPath());
		final String nestedUrlPath = nestedUrl.getPath();
		final int pos = nestedUrlPath.lastIndexOf('!');
		final String jarFilePath = nestedUrlPath.substring(0, pos);
		final File jarFile = new File(URLUtil.decode(jarFilePath, "UTF8"));
		return FileUtil.getCanonicalPath(jarFile);
	}

}
