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
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.exception.IORuntimeException;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.ContentTypeUtil;
import org.t2framework.confeito.util.ServletUtil;

/**
 * <#if locale="en">
 * <p>
 * Concrete class of org.t2framework.contexts.Response.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.contexts.Response
 */
public class ResponseImpl implements Response {

	protected final HttpServletResponse response;

	protected String contentType;

	public ResponseImpl(HttpServletResponse response) {
		this.response = Assertion.notNull(response);
		this.contentType = response.getContentType();
	}

	@Override
	public String getCharacterEncoding() {
		final String type = getContentType();
		String encoding = ContentTypeUtil.getEncodingFromContentType(type);
		if (encoding != null) {
			return encoding;
		}
		return response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IORuntimeException {
		try {
			return response.getOutputStream();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = Assertion.notNull(contentType);
		response.setContentType(contentType);
	}

	@Override
	public HttpServletResponse getNativeResource() {
		return response;
	}

	@Override
	public void redirect(String path) {
		Assertion.notNull(path);
		ServletUtil.redirect(response, path);
	}

	@Override
	public void setHeader(String key, String value) {
		Assertion.notNull(key);
		Assertion.notNull(value);
		getNativeResource().setHeader(key, value);
	}

	@Override
	public void setNoCache() {
		response.setHeader("Pragma", "No-cache");
		response
				.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setDateHeader("Expires", 1);
	}

	@Override
	public void writeAndFlush(String s) throws IOException {
		PrintWriter writer = getWriter();
		writer.write(s);
		writer.flush();
	}

	@Override
	public void write(String s) throws IOException {
		PrintWriter writer = getWriter();
		writer.write(s);
	}

	protected PrintWriter getWriter() throws IOException {
		return getNativeResource().getWriter();
	}

	@Override
	public void flush() throws IOException {
		PrintWriter writer = getWriter();
		writer.flush();
	}

	@Override
	public void sendError(int status) {
		try {
			getNativeResource().sendError(status);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

}
