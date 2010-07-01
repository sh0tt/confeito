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
package org.t2framework.confeito.navigation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.StreamUtil;

/**
 * <#if locale="en">
 * <p>
 * Base class of stream type response.
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
public abstract class StreamNavigation<T extends StreamNavigation<T>>
		implements Navigation {

	/**
	 * <#if locale="en">
	 * <p>
	 * Bytes to write.Better not to use because of performance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected byte[] bytes;

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link InputStream} to write.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected InputStream is;

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link File} to write.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected File file;

	/**
	 * <#if locale="en">
	 * <p>
	 * Content type.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected String contentType;

	public StreamNavigation(File file) {
		this.file = Assertion.notNull(file);
	}

	public StreamNavigation(InputStream is) {
		this.is = Assertion.notNull(is);
	}

	public StreamNavigation(byte[] bytes) {
		this.bytes = Assertion.notNull(bytes);
	}

	public byte[] getBytes() {
		return bytes;
	}

	protected void writeTo(HttpServletResponse res, ServletOutputStream sos)
			throws IOException {
		if (this.contentType != null) {
			res.setContentType(this.contentType);
		}
		if (is != null) {
			StreamUtil.copy(is, sos);
		} else if (file != null) {
			StreamUtil
					.copy(StreamUtil.createBufferedFileInputStream(file), sos);
		} else {
			final byte[] bytes = getBytes();
			res.setContentLength(bytes.length);
			sos.write(bytes);
		}
	}

	@SuppressWarnings("unchecked")
	public T setContentType(String contentType) {
		Assertion.notNull(contentType);
		this.contentType = contentType;
		return (T) this;
	}
}
