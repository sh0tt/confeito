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
import java.net.URL;
import java.util.zip.ZipFile;

/**
 * <#if locale="en">
 * <p>
 * {@link ZipFileUtil} is an utility class for using {@link ZipFile}.
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
public class ZipFileUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * no instantiation.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	private ZipFileUtil() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert to {@link URL} to zip file path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param zipUrl
	 * @return
	 */
	public static String toZipFilePath(final URL zipUrl) {
		Assertion.notNull(zipUrl, "zipUrl");
		final String urlString = zipUrl.getPath();
		final int pos = urlString.lastIndexOf('!');
		final String zipFilePath = urlString.substring(0, pos);
		final File zipFile = new File(URLUtil.decode(zipFilePath, "UTF8"));
		return FileUtil.getCanonicalPath(zipFile);
	}
}
