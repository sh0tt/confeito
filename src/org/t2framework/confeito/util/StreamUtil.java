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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.t2framework.confeito.exception.FileNotFoundRuntimeException;
import org.t2framework.confeito.exception.IORuntimeException;

/**
 * <#if locale="en">
 * <p>
 * Utility class of stream.
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
public class StreamUtil {

	public static BufferedInputStream createBufferedFileInputStream(String path)
			throws FileNotFoundRuntimeException {
		try {
			return new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundRuntimeException(e, path);
		}
	}

	public static BufferedInputStream createBufferedFileInputStream(File file)
			throws FileNotFoundRuntimeException {
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundRuntimeException(e, file.getPath());
		}
	}

	public static BufferedOutputStream createBufferedFileOutputStream(
			String path) throws FileNotFoundRuntimeException {
		try {
			return new BufferedOutputStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundRuntimeException(e, path);
		}
	}

	public static BufferedOutputStream createBufferedFileOutputStream(File file)
			throws FileNotFoundRuntimeException {
		try {
			return new BufferedOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundRuntimeException(e, file.getPath());
		}
	}

	public static FileInputStream createFileInputStream(File file)
			throws IORuntimeException {
		try {
			return new FileInputStream(file);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static FileOutputStream createFileOutputStream(File file)
			throws IORuntimeException {
		try {
			return new FileOutputStream(file);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static final void copy(InputStream is, OutputStream os)
			throws IORuntimeException {
		byte[] buf = new byte[8192];
		try {
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, n);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static int available(InputStream is) throws IORuntimeException {
		try {
			return is.available();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static void close(InputStream is) throws IORuntimeException {
		CloseableUtil.close(is);
	}

	public static void close(OutputStream os) throws IORuntimeException {
		CloseableUtil.close(os);
	}

	public static void flush(OutputStream out) throws IORuntimeException {
		if (out == null) {
			return;
		}
		try {
			out.flush();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static byte[] getBytes(final InputStream is)
			throws IORuntimeException {
		Assertion.notNull(is);
		byte[] bytes = null;
		byte[] buf = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, n);
			}
			bytes = baos.toByteArray();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			close(is);
			close(baos);
		}
		return bytes;
	}

}
