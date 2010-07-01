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
package org.t2framework.confeito.contexts.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.t2framework.confeito.contexts.UploadFile;
import org.t2framework.confeito.util.CloseableUtil;
import org.t2framework.confeito.util.StreamUtil;

/**
 * <#if locale="en">
 * <p>
 * This class depends on Apache commons fileupload.
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
public class StreamUploadFileImpl implements UploadFile {

	protected String contentType;

	protected String name;

	protected InputStream is;

	public StreamUploadFileImpl(InputStream is, String contentType, String name)
			throws IOException {
		this.is = is;
		this.contentType = contentType;
		this.name = name;
	}

	public byte[] get() {
		byte[] ret = new byte[0];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			StreamUtil.copy(this.is, baos);
			ret = baos.toByteArray();
		} finally {
			CloseableUtil.close(baos);
		}
		return ret;
	}

	public String getContentType() {
		return this.contentType;
	}

	public InputStream getInputStream() {
		return this.is;
	}

	public String getName() {
		return this.name;
	}

	public long getSize() {
		return get().length;
	}

	public String getString() throws UnsupportedEncodingException {
		return getString("UTF-8");
	}

	public String getString(String encoding)
			throws UnsupportedEncodingException {
		return new String(get(), encoding);
	}

	public String toString() {
		return getName();
	}

	protected String getBaseFileName(String filePath) {
		// First, ask the JDK for the base file name.
		String fileName = new File(filePath).getName();

		// Now check for a Windows file name parsed incorrectly.
		int colonIndex = fileName.indexOf(":");
		if (colonIndex == -1) {
			// Check for a Windows SMB file path.
			colonIndex = fileName.indexOf("\\\\");
		}
		int backslashIndex = fileName.lastIndexOf("\\");

		if (-1 < colonIndex && -1 < backslashIndex) {
			// Consider this filename to be a full Windows path, and parse
			// it
			// accordingly to retrieve just the base file name.
			fileName = fileName.substring(backslashIndex + 1);
		}

		return fileName;
	}

	@Override
	public void close() {
		if (this.is != null) {
			CloseableUtil.close(is);
		}
	}

}
