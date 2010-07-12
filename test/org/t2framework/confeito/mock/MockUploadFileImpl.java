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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.t2framework.confeito.contexts.UploadFile;

/**
 * Mock of {@link MockUploadFileImpl}.
 * 
 * @author shot
 * 
 */
public class MockUploadFileImpl implements UploadFile {

	protected byte[] bytes;

	protected String contentType;

	protected InputStream inputStream;

	protected String name;

	@Override
	public byte[] get() {
		return this.bytes;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.inputStream;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public long getSize() {
		return this.bytes.length;
	}

	@Override
	public String getString() throws UnsupportedEncodingException {
		return new String(bytes, "UTF-8");
	}

	@Override
	public String getString(String encoding)
			throws UnsupportedEncodingException {
		return new String(this.bytes, encoding);
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void close() {
	}

}
