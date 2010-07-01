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

import java.lang.annotation.Annotation;

/**
 * <#if locale="en">
 * <p>
 * HttpMethod is enum of http method.
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
public enum HttpMethod {

	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP GET
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	GET {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.GET.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP POST
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	POST {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.POST.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP PUT
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	PUT {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.PUT.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP DELETE
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	DELETE {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.DELETE.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP HEAD
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	HEAD {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.HEAD.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP OPTIONS
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	OPTIONS {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.OPTIONS.class;
		}

	},
	/**
	 * <#if locale="en">
	 * <p>
	 * HTTP TRACE
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	TRACE {
		public Class<? extends Annotation> getAnnotation() {
			return org.t2framework.confeito.annotation.TRACE.class;
		}

	};

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get {@link HttpMethod} from string http method name.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param method
	 * @return http method enum
	 */
	public static HttpMethod getMethodType(String method) {
		if (method == null) {
			return null;
		}
		final String s = method.toUpperCase();
		if ("GET".equals(s)) {
			return GET;
		} else if ("POST".equals(s)) {
			return POST;
		} else if ("PUT".equals(s)) {
			return PUT;
		} else if ("DELETE".equals(s)) {
			return DELETE;
		} else if ("HEAD".equals(s)) {
			return HEAD;
		} else if ("OPTIONS".equals(s)) {
			return OPTIONS;
		} else if ("TRACE".equals(s)) {
			return TRACE;
		} else {
			return null;
		}
	}

	public String toString() {
		return this.name().toUpperCase();
	}

	public abstract Class<? extends Annotation> getAnnotation();
}
