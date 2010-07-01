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

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.parameter.ParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * {@link ParameterResolver} for {@link WebContext}.
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
public class WebContextParameterResolver extends AbstractParameterResolver {

	private static enum WebContextClasses {
		WEBCONTEXT(WebContext.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return WebContext.get();
			}
		},

		REQUEST(Request.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getRequest();
			}
		},

		RESPONSE(Response.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getResponse();
			}
		},

		CONTEXT(Application.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getApplication();
			}
		},

		SESSION(Session.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getSession();
			}
		};

		Class<?> targetClass;

		WebContextClasses(Class<?> targetClass) {
			this.targetClass = targetClass;
		}

		public boolean isTarget(Class<?> paramClass) {
			return targetClass != null
					&& paramClass.isAssignableFrom(targetClass);
		}

		public abstract Object getResource(ActionContext actionContext);
	}

	@Override
	protected boolean isTargetClass(Class<?> targetClass, Class<?> paramClass) {
		boolean ret = false;
		for (WebContextClasses c : WebContextClasses.values()) {
			if (c.isTarget(paramClass)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		for (WebContextClasses c : WebContextClasses.values()) {
			if (c.isTarget(paramClass)) {
				return c.getResource(actionContext);
			}
		}
		return null;
	}

}
