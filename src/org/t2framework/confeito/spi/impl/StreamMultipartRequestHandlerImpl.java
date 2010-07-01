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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.apache.commons.fileupload.FileItemIterator;
import org.t2framework.confeito.apache.commons.fileupload.FileItemStream;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadException;
import org.t2framework.confeito.apache.commons.fileupload.servlet.ServletFileUpload;
import org.t2framework.confeito.apache.commons.fileupload.util.Streams;
import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.UploadFile;
import org.t2framework.confeito.exception.IORuntimeException;
import org.t2framework.confeito.filter.MultiPartRequestFilter;
import org.t2framework.confeito.util.CloseableUtil;
import org.t2framework.confeito.util.Logger;

/**
 * <#if locale="en">
 * <p>
 * Stream based {@code MultipartRequestHandler}. This class depends on Apache
 * commons fileupload.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.apache.commons.fileupload.FileItemIterator
 * @see org.t2framework.confeito.apache.commons.fileupload.FileItemStream
 * @see org.t2framework.confeito.apache.commons.fileupload.FileUploadException
 * @see org.t2framework.confeito.apache.commons.fileupload.servlet.ServletFileUpload
 * @see org.t2framework.confeito.apache.commons.fileupload.util.Streams
 * 
 */
public class StreamMultipartRequestHandlerImpl extends
		AbstractMultipartRequestHandler {

	private static final Logger LOG = Logger
			.getLogger(StreamMultipartRequestHandlerImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> handle(HttpServletRequest request) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>(request
				.getParameterMap());
		final String enc = request.getCharacterEncoding();
		final ServletFileUpload upload = new ServletFileUpload();
		upload.setHeaderEncoding(enc);
		upload.setFileSizeMax(MultiPartRequestFilter.maxFileSize);
		upload.setSizeMax(MultiPartRequestFilter.maxSize);
		try {
			final Multipart multiPart = createMultipart();
			final FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				final FileItemStream itemStream = (FileItemStream) iterator
						.next();
				final String contentType = itemStream.getContentType();
				final String fieldName = itemStream.getFieldName();
				InputStream is = itemStream.openStream();
				if (itemStream.isFormField()) {
					try {
						final String value = Streams.asString(is, enc);
						final String[] paramValue = add(
								paramMap.get(fieldName), value, String.class);
						paramMap.put(fieldName, paramValue);
					} catch (FileItemStream.ItemSkippedException e) {
						LOG
								.debug("FileItem is already closed but we continue all of input streams: "
										+ e.getMessage());
					} finally {
						CloseableUtil.close(is);
					}
				} else {
					final String name = itemStream.getName();
					final UploadFile uploadFile = createUploadFile(is,
							contentType, name);
					multiPart.addUploadFile(fieldName, uploadFile);
				}
			}
			storeMultipart(request, multiPart);
		} catch (FileUploadException e) {
			logUploadError(e, request);
			paramMap = request.getParameterMap();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		return Collections.unmodifiableMap(paramMap);
	}

}
