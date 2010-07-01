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

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.WebContext;

/**
 * <#if locale="en">
 * <p>
 * {@code SimpleText} is simple text output {@code Navigation}. The response
 * content type will be {@literal text/html}.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class SimpleText extends TextNavigation {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Factory to create {@link SimpleText}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param text
	 * @return created SimpleText
	 */
	public static SimpleText out(String text) {
		return new SimpleText(text);
	}

	public SimpleText(String text) {
		super(text);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Execute to write text message to response.The content type sets
	 * {@literal text/plain} and encoding sets
	 * {@link Request#getCharacterEncoding()}.
	 * 
	 * Response is immediately flushed.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		final Response response = context.getResponse();
		final String encoding = context.getRequest().getCharacterEncoding();
		String contentType = Constants.TEXT_CONTENT_TYPE;
		if (encoding != null) {
			contentType += "; charset=" + encoding;
		}
		response.setContentType(contentType);
		response.writeAndFlush(getText());
	}

}
