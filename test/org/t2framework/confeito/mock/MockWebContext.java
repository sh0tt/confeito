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

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Chain;
import org.t2framework.confeito.contexts.FrameworkComponent;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.contexts.impl.ApplicationImpl;
import org.t2framework.confeito.contexts.impl.RequestImpl;
import org.t2framework.confeito.contexts.impl.ResponseImpl;
import org.t2framework.confeito.contexts.impl.SessionImpl;

/**
 * 
 * <#if locale="en">
 * <p>
 * Mock object of {@link WebContext}.Expected to use for unit tesing.
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
public class MockWebContext extends WebContext implements FrameworkComponent {

	protected Request request;

	protected Response response;

	protected Application application;

	protected Session session;

	protected MockHttpServletRequest mockRequest;

	protected MockHttpServletResponse mockResponse;

	protected MockServletContext mockServletContext;

	protected MockHttpSession mockSession;

	protected MockFilterConfig mockConfig;

	protected Chain chain;

	public static MockWebContext createMock(final String requestPath) {
		return createMock(null, requestPath);
	}

	public static MockWebContext createMock(final String path,
			final String requestPath) {
		MockServletContext context = new MockServletContextImpl(path);
		MockHttpServletRequest request = new MockHttpServletRequestImpl(
				context, requestPath);
		MockHttpServletResponse response = new MockHttpServletResponseImpl(
				request);
		MockHttpSession session = MockHttpSession.class.cast(request
				.getSession());
		MockFilterConfig config = new MockFilterConfigImpl(context);
		MockWebContext mock = new MockWebContext(request, response, context,
				session, config);
		WebContext.set(mock);
		return mock;
	}

	public MockWebContext(final MockHttpServletRequest req,
			final MockHttpServletResponse res,
			final MockServletContext context, final MockHttpSession ses,
			final MockFilterConfig config) {
		this.request = new RequestImpl(req, res);
		this.response = new ResponseImpl(res);
		this.application = new ApplicationImpl(context, config);
		this.session = new SessionImpl(request);
		this.mockRequest = req;
		this.mockResponse = res;
		this.mockSession = ses;
		this.mockServletContext = context;
		this.mockConfig = config;
	}

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public Response getResponse() {
		return response;
	}

	@Override
	public Session getSession() {
		return session;
	}

	public MockHttpServletRequest getMockHttpServletRequest() {
		return mockRequest;
	}

	public MockHttpServletResponse getMockHttpServletResponse() {
		return mockResponse;
	}

	public MockHttpSession getMockHttpSession() {
		return mockSession;
	}

	public MockServletContext getMockServletContext() {
		return mockServletContext;
	}

	@Override
	public Chain getChain() {
		return null;
	}

	public void createAndSetMockActionContext() {
		this.actionContext = new MockActionContextImpl(this);
	}

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void setChain(Chain chain) {
		this.chain = chain;
	}

	public <C> MockWebContext containerAdapter(
			ContainerAdapter<C> containerAdapter) {
		this.containerAdapter = containerAdapter;
		return this;
	}

	public void setMockHttpServletRequest(MockHttpServletRequest mockRequest) {
		this.mockRequest = mockRequest;
		this.request = new RequestImpl(this.mockRequest, this.mockResponse);
	}

	public void setMockHttpServletResponse(MockHttpServletResponse mockResponse) {
		this.mockResponse = mockResponse;
		this.response = new ResponseImpl(this.mockResponse);
	}

	public void setMockServletContext(MockServletContext mockServletContext) {
		this.mockServletContext = mockServletContext;
		this.application = new ApplicationImpl(this.mockServletContext,
				this.mockConfig);
	}

	public void setMockHttpSession(MockHttpSession mockSession) {
		this.mockSession = mockSession;
	}

}
