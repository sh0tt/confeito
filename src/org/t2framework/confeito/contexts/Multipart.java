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
package org.t2framework.confeito.contexts;

import java.util.List;
import java.util.Map;

/**
 * <#if locale="en">
 * <p>
 * Multipart is an interface to hold UploadFiles. This instance should be set to
 * HttpServletRequest.setAttribute() with a specified key.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see org.t2framework.confeito.filter.MultiPartRequestFilter
 */
public interface Multipart {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get map of name and UploadFile[].
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return upload file map
	 */
	Map<String, UploadFile[]> getUploadMap();

	/**
	 * <#if locale="en">
	 * <p>
	 * Get list of UploadFile.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return list of upload file
	 */
	List<UploadFile> getUploadList();

	/**
	 * <#if locale="en">
	 * <p>
	 * Add UploadFile.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @param uploadFile
	 */
	void addUploadFile(String key, UploadFile uploadFile);
}
