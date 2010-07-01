package org.t2framework.confeito.parameter.impl;

import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.action.ErrorInfo;
import org.t2framework.confeito.annotation.JsonParam;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.parameter.AbstractParameterResolver;

import com.google.gson.Gson;

public class JsonParameterResolverImpl extends AbstractParameterResolver {

	public JsonParameterResolverImpl() {
		setTargetAnnotationClass(JsonParam.class);
	}

	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		final WebContext context = WebContext.get();
		final ErrorInfo errorInfo = actionContext.getErrorInfo();
		Gson gson = new Gson();
		try {
			final HttpServletRequest req = context.getRequest()
					.getNativeResource();
			final Reader reader = req.getReader();
			Object o = gson.fromJson(reader, paramClass);
			return o;
		} catch (Throwable t) {
			errorInfo.addErrorInfo(getClass().getName(), t);
			return null;
		}
	}

}
