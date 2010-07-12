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
package org.t2framework.confeito.mock;

import org.t2framework.confeito.contexts.impl.RequestImpl;

/**
 * 
 * <#if locale="en">
 * <p>
 * Mock implementation of {@link RequestImpl}.
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
public class MockRequestImpl extends RequestImpl implements MockRequest {

	protected MockHttpServletRequest mockRequest;

	public MockRequestImpl(String requestPath) {
		this(null, requestPath);
	}

	public MockRequestImpl(String contextPath, String requestPath) {
		this(new MockHttpServletRequestImpl(new MockServletContextImpl(
				contextPath), requestPath));
	}

	protected MockRequestImpl(MockHttpServletRequest request) {
		super(request, new MockHttpServletResponseImpl(request));
		this.mockRequest = request;
	}

	@Override
	public void addParameter(String name, String value) {
		mockRequest.addParameter(name, value);
	}

}
