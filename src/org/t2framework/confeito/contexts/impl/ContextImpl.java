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

import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Chain;
import org.t2framework.confeito.contexts.FrameworkComponent;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Default context class for T2.
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
public class ContextImpl extends WebContext implements FrameworkComponent {

	protected Request request;

	protected Response response;

	protected Application application;

	protected Session session;

	protected Chain chain;

	public ContextImpl(final Request request, final Response response,
			final Application application, final Session session, Chain chain) {
		this.request = Assertion.notNull(request);
		this.response = Assertion.notNull(response);
		this.application = Assertion.notNull(application);
		this.session = Assertion.notNull(session);
		this.chain = chain;
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
	public Application getApplication() {
		return application;
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public Chain getChain() {
		return chain;
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

}
