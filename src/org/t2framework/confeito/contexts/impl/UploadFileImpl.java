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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.t2framework.confeito.apache.commons.fileupload.FileItem;
import org.t2framework.confeito.contexts.UploadFile;

/**
 * <#if locale="en">
 * <p>
 * UploadFileImpl is a wrapper class of
 * {@link org.t2framework.confeito.apache.commons.fileupload.FileItem}. This
 * class depends on Apache commons fileupload.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.apache.commons.fileupload.FileItem
 */
public class UploadFileImpl implements UploadFile {

	protected final FileItem fileItem;

	public UploadFileImpl(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	public byte[] get() {
		return fileItem.get();
	}

	public String getContentType() {
		return fileItem.getContentType();
	}

	public InputStream getInputStream() throws IOException {
		return fileItem.getInputStream();
	}

	public String getName() {
		return fileItem.getName();
	}

	public long getSize() {
		return fileItem.getSize();
	}

	public String getString() {
		return fileItem.getString();
	}

	public String getString(String encoding)
			throws UnsupportedEncodingException {
		return fileItem.getString(encoding);
	}

	public String toString() {
		return getName();
	}

	@Override
	public void close() {
	}

}
