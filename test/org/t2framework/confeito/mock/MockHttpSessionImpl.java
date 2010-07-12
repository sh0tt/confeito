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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.t2framework.confeito.util.IteratorEnumeration;

/**
 * <#if locale="en">
 * <p>
 * An imeplementation class of {@link MockHttpSession}.
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
public class MockHttpSessionImpl implements MockHttpSession, Serializable {

	private static final long serialVersionUID = 2182279632419560836L;

	protected final long creationTime = System.currentTimeMillis();

	protected long lastAccessedTime = creationTime;

	protected ServletContext servletContext;

	protected String id;

	protected boolean new_ = true;

	protected boolean valid = true;

	protected int maxInactiveInterval = -1;

	protected Map<String, Object> attributes = new HashMap<String, Object>();

	protected MockHttpServletRequest request;

	public MockHttpSessionImpl(final ServletContext servletContext,
			final MockHttpServletRequest request) {
		this.servletContext = servletContext;
		this.id = UUID.randomUUID().toString();
		this.request = request;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public String getId() {
		return id;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void access() {
		new_ = false;
		lastAccessedTime = System.currentTimeMillis();
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	@Deprecated
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return null;
	}

	@Deprecated
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return new IteratorEnumeration<String>(attributes.keySet().iterator());
	}

	@Deprecated
	public String[] getValueNames() {
		return (String[]) attributes.keySet().toArray(
				new String[attributes.size()]);
	}

	@Deprecated
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Deprecated
	public void removeValue(String name) {
		removeAttribute(name);
	}

	public void invalidate() {
		if (!valid) {
			return;
		}
		attributes.clear();
		valid = false;
		request.clearSession();
	}

	public boolean isNew() {
		return new_;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
