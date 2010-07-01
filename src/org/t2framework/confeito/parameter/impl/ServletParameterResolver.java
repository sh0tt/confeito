package org.t2framework.confeito.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

public class ServletParameterResolver extends AbstractParameterResolver {

	private static enum ServletClasses {
		REQUEST(HttpServletRequest.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getRequest().getNativeResource();
			}
		},

		RESPONSE(HttpServletResponse.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getResponse().getNativeResource();
			}
		},

		CONTEXT(ServletContext.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getApplication().getNativeResource();
			}
		},

		SESSION(HttpSession.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getSession().getNativeResource();
			}
		},

		FILTER(FilterConfig.class) {
			@Override
			public Object getResource(ActionContext actionContext) {
				return actionContext.getApplication().getFilterConfig();

			}
		};

		Class<?> targetClass;

		ServletClasses(Class<?> targetClass) {
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
		for (ServletClasses c : ServletClasses.values()) {
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
		for (ServletClasses c : ServletClasses.values()) {
			if (c.isTarget(paramClass)) {
				return c.getResource(actionContext);
			}
		}
		return null;
	}

}
