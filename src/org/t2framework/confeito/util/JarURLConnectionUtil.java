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

import java.io.IOException;
import java.net.JarURLConnection;
import java.util.jar.JarFile;

import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * JarURLConnection utility.
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
public class JarURLConnectionUtil {

	private JarURLConnectionUtil() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@link JarFile} from {@link JarURLConnection}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param conn
	 * @return
	 * @throws IORuntimeException
	 */
	public static JarFile getJarFile(JarURLConnection conn)
			throws IORuntimeException {
		try {
			return conn.getJarFile();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
