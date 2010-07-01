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
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.ContentTypeUtil;
import org.t2framework.confeito.util.ReaderUtil;

import com.google.gson.Gson;

/**
 * <#if locale="en">
 * <p>
 * JSON response.
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
public class Json extends TextNavigation {

	/**
	 * <#if locale="en">
	 * <p>
	 * {@link File} for converting JSON, almost case of unit testing.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected File file;

	protected String contentType = "text/javascript; charset=utf-8";

	protected String encoding = "UTF-8";

	protected boolean jsonPrefixing;

	public static Json convert(Object jsonObject) {
		return new Json(jsonObject);
	}

	public static Json convert(File file) {
		return new Json(file);
	}

	public Json encoding(String encoding) {
		Assertion.notNull(encoding);
		this.encoding = encoding;
		this.contentType = "text/javascript; charset=" + encoding;
		return this;
	}

	public Json contentType(String contentType) {
		Assertion.notNull(contentType);
		this.contentType = contentType;
		String encoding = ContentTypeUtil
				.getEncodingFromContentType(contentType);
		if (encoding != null) {
			this.encoding = encoding;
		}
		return this;
	}

	public Json prefixing(boolean prefixing) {
		this.jsonPrefixing = prefixing;
		return this;
	}

	public Json(Object jsonObject) {
		super(jsonObject);
	}

	public Json(File file) {
		super(file);
		this.file = file;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Execute to convert from object to JSON. The content type of response sets
	 * {@literal text/javascript} and charset sets {@literal utf-8}. The
	 * response is immediately flushed.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @see Gson
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		Response response = context.getResponse();
		response.setContentType(this.contentType);
		response.setNoCache();
		String json = null;
		if (this.file != null) {
			json = ReaderUtil.readText(new InputStreamReader(
					new FileInputStream(file), this.encoding));
		} else {
			Gson gson = new Gson();
			json = gson.toJson(getObject());
			if (jsonPrefixing) {
				json = "{}&&" + json;
			}
		}
		response.writeAndFlush(json);
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getEncoding() {
		return this.encoding;
	}

}
