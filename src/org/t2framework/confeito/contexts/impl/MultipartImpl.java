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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.t2framework.confeito.contexts.Multipart;
import org.t2framework.confeito.contexts.UploadFile;

public class MultipartImpl implements Multipart {

	protected List<UploadFile> uploadList = new ArrayList<UploadFile>();

	protected Map<String, UploadFile[]> uploadMap = new HashMap<String, UploadFile[]>();

	@Override
	public List<UploadFile> getUploadList() {
		return Collections.unmodifiableList(uploadList);
	}

	@Override
	public Map<String, UploadFile[]> getUploadMap() {
		return Collections.unmodifiableMap(uploadMap);
	}

	public void addUploadFile(String key, UploadFile uploadFile) {
		UploadFile[] files = uploadMap.get(key);
		if (files == null) {
			files = new UploadFile[] { uploadFile };
		} else {
			UploadFile[] newFiles = new UploadFile[files.length + 1];
			System.arraycopy(files, 0, newFiles, 0, files.length);
			newFiles[files.length] = uploadFile;
			files = newFiles;
		}
		uploadMap.put(key, files);
		uploadList.add(uploadFile);
	}
}
