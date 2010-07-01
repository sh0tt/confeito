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
package org.t2framework.confeito.spi.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.apache.commons.fileupload.FileItem;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadException;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.IOFileUploadException;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.UploadFile;
import org.t2framework.confeito.contexts.impl.MultipartImpl;
import org.t2framework.confeito.contexts.impl.StreamUploadFileImpl;
import org.t2framework.confeito.contexts.impl.UploadFileImpl;
import org.t2framework.confeito.exception.IORuntimeException;
import org.t2framework.confeito.filter.MultiPartRequestFilter;
import org.t2framework.confeito.spi.MultipartRequestHandler;
import org.t2framework.confeito.util.Logger;

/**
 * <#if locale="en">
 * <p>
 * 
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
 * @see org.t2framework.confeito.apache.commons.fileupload.FileItem
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadException
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.IOFileUploadException
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException
 */
public abstract class AbstractMultipartRequestHandler implements
		MultipartRequestHandler {

	private static final Logger log = Logger
			.getLogger(AbstractMultipartRequestHandler.class);

	protected void logUploadError(final FileUploadException e,
			final HttpServletRequest request) {
		if (e instanceof InvalidContentTypeException) {
			log.log("WTDT0007", new Object[] { e, request.getContentType() });
		} else if (e instanceof IOFileUploadException) {
			log.log("WTDT0008", e, new Object[] { e.getMessage() });
		} else if (e instanceof FileSizeLimitExceededException) {
			log.log("WTDT0009", e,
					new Object[] { MultiPartRequestFilter.maxSize });
		} else if (e instanceof SizeLimitExceededException) {
			log.log("WTDT0010", e, new Object[] { request.getContentLength(),
					MultiPartRequestFilter.maxSize });
		} else {
			log.log("WTDT0011", e, new Object[] { e.toString() });
		}
	}

	protected void storeMultipart(HttpServletRequest request,
			Multipart multiPart) {
		request.setAttribute(Request.MULTIPART_ATTRIBUTE_KEY, multiPart);
	}

	protected Multipart getMultipart(HttpServletRequest request) {
		return (Multipart) request
				.getAttribute(Request.MULTIPART_ATTRIBUTE_KEY);
	}

	protected Multipart createMultipart() {
		return new MultipartImpl();
	}

	protected UploadFile createUploadFile(InputStream is, String contentType,
			String name) {
		try {
			return new StreamUploadFileImpl(is, contentType, name);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	protected UploadFile createUploadFile(FileItem fileItem) {
		return new UploadFileImpl(fileItem);
	}

	@SuppressWarnings("unchecked")
	protected <T> T[] add(T[] objs, T value, Class<T> clazz) {
		T[] newObjs;
		if (objs == null) {
			newObjs = (T[]) Array.newInstance(clazz, 1);
			newObjs[0] = value;
		} else {
			newObjs = (T[]) Array.newInstance(objs.getClass()
					.getComponentType(), objs.length + 1);
			System.arraycopy(objs, 0, newObjs, 0, objs.length);
			newObjs[objs.length] = value;
		}
		return newObjs;
	}

	protected Object getFirst(Object obj) {
		Object[] objs = (Object[]) obj;
		if (objs == null || objs.length == 0) {
			return null;
		} else {
			return objs[0];
		}
	}

	@Override
	public void close(HttpServletRequest request) {
		final Multipart multipart = getMultipart(request);
		if (multipart != null) {
			for (UploadFile file : multipart.getUploadList()) {
				file.close();
			}
		}
	}
}
