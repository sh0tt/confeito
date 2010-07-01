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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * Reader utility.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @author c9katayama
 */
public class ReaderUtil {

	private static final int BUF_SIZE = 8192;

	public static String readText(Reader reader) {
		BufferedReader in = new BufferedReader(reader);
		StringBuilder out = new StringBuilder(100);
		try {
			try {
				char[] buf = new char[BUF_SIZE];
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.append(buf, 0, n);
				}
			} finally {
				CloseableUtil.close(in);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		return out.toString();
	}

}
