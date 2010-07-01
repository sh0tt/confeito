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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.apache.commons.fileupload.FileItem;
import org.t2framework.confeito.apache.commons.fileupload.FileUploadException;
import org.t2framework.confeito.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.t2framework.confeito.apache.commons.fileupload.servlet.ServletFileUpload;
import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.UploadFile;
import org.t2framework.confeito.filter.MultiPartRequestFilter;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.CloseableUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.StreamUtil;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * Classic way of multipart requese handler.
 * 
 * Default implementation of {@code MultipartRequestHandler}. This class depends
 * on Apache commons fileupload.
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
 * @see org.t2framework.confeito.apache.commons.fileupload.disk.DiskFileItemFactory
 * @see org.t2framework.confeito.apache.commons.fileupload.servlet.ServletFileUpload
 * 
 */
public class MultipartRequestHandlerImpl extends
		AbstractMultipartRequestHandler {

	private static final Logger LOG = Logger
			.getLogger(MultipartRequestHandlerImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> handle(HttpServletRequest request) {
		Assertion.notNull(request);
		final String encoding = request.getCharacterEncoding();
		Map<String, String[]> paramMap = Collections.emptyMap();
		final DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MultiPartRequestFilter.thresholdSize);
		if (StringUtil.isEmpty(MultiPartRequestFilter.repositoryPath) == false) {
			factory.setRepository(new File(
					MultiPartRequestFilter.repositoryPath));
		}
		final ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(encoding);
		upload.setFileSizeMax(MultiPartRequestFilter.maxFileSize);
		upload.setSizeMax(MultiPartRequestFilter.maxSize);
		try {
			List<FileItem> requestList = upload.parseRequest(request);
			paramMap = prepareParameters(request, requestList, encoding);
		} catch (FileUploadException e) {
			logUploadError(e, request);
			outToLast(request);
			paramMap = request.getParameterMap();
		}
		return paramMap;
	}

	private void outToLast(HttpServletRequest request) {
		InputStream is = null;
		try {
			is = request.getInputStream();
			// write nothing.
			StreamUtil.copy(is, new OutputStream() {

				public void write(byte[] b, int off, int len) {
				}

				public void write(int b) {
				}

				public void write(byte[] b) throws IOException {
				}
			});
		} catch (Throwable ignore) {
			LOG.debug("Error occurring winding requested upload input to last:"
					+ ignore.getMessage());
		} finally {
			CloseableUtil.close(is);
		}
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String[]> prepareParameters(
			HttpServletRequest request, List<FileItem> fileItemList,
			String encoding) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>(request
				.getParameterMap());
		final Multipart multiPart = createMultipart();
		for (Iterator<FileItem> itr = fileItemList.iterator(); itr.hasNext();) {
			FileItem fileItem = itr.next();
			String fieldName = fileItem.getFieldName();
			fileItem.delete();
			if (fileItem.isFormField()) {
				String value;
				try {
					value = fileItem.getString(encoding);
				} catch (UnsupportedEncodingException ex) {
					throw new RuntimeException();
				}
				paramMap.put(fieldName, add(paramMap.get(fieldName), value,
						String.class));
			} else {
				final UploadFile value = createUploadFile(fileItem);
				multiPart.addUploadFile(fieldName, value);
			}
		}
		storeMultipart(request, multiPart);
		return Collections.unmodifiableMap(paramMap);
	}

	@Override
	public void close(HttpServletRequest request) {
	}

}
