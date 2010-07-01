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
package org.t2framework.confeito.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.annotation.Upload;
import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.UploadFile;
import org.t2framework.confeito.exception.UploadFileNotFoundException;
import org.t2framework.confeito.exception.UploadSettingsRuntimeException;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * UploadFileParameterResolver is a resolver class that injects uploaded file as
 * UploadFile from user requests.
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
public class UploadFileParameterResolver extends AbstractParameterResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this instance with annotation {@link Upload}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public UploadFileParameterResolver() {
		setTargetAnnotationClass(Upload.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return if UploadParameterResolver.resolve() should be invoked or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if this parameter should be handled by this resolver,
	 *         otherwise false
	 */
	public boolean isTargetParameter(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		return isUploadFile(paramClass);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Returns single UploadFile or multiple UploadFiles if applicable.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return UploadFile or UploadFiles[]
	 * @throws UploadSettingsRuntimeException
	 *             if {@link Multipart} is not found for some unknown reason
	 * @throws UploadFileNotFoundException
	 *             if {@link UploadFile} is not found in {@link Multipart}.
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		assertUploadFile(paramClass);
		Upload upload = findTargetAnnotation(paramAnnotations,
				targetAnnotationClass);
		UploadFile[] files = getUploadFiles(upload, actionContext.getRequest());
		if (paramClass.isArray()) {
			return files;
		} else {
			return getSingleUploadFile(files);
		}
	}

	protected UploadFile getSingleUploadFile(UploadFile[] files) {
		if (files.length == 0) {
			throw new IllegalStateException();
		}
		return files[0];
	}

	protected static void assertUploadFile(Class<?> paramClass) {
		if (!isUploadFile(paramClass)) {
			throw new IllegalStateException();
		}
	}

	protected static boolean isUploadFile(Class<?> paramClass) {
		final Class<?> target;
		if (paramClass.isArray()) {
			target = paramClass.getComponentType();
		} else {
			target = paramClass;
		}
		return UploadFile.class.isAssignableFrom(target);
	}

	protected UploadFile[] getUploadFiles(final Upload upload,
			final Request request) {
		final Multipart multiPart = request.getMultipart();
		if (multiPart == null) {
			throw new UploadSettingsRuntimeException();
		}
		UploadFile[] files;
		if (upload == null) {
			List<UploadFile> list = multiPart.getUploadList();
			files = list.toArray(new UploadFile[] {});
			return isFilesExist(files) ? files : new UploadFile[0];
		} else {
			final String uploadFileName = upload.value();
			files = multiPart.getUploadMap().get(uploadFileName);
			if (isFilesExist(files)) {
				return files;
			} else {
				throw new UploadFileNotFoundException(uploadFileName);
			}
		}
	}

	private boolean isFilesExist(UploadFile[] files) {
		return (files != null && files.length > 0);
	}

}
