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

import javax.servlet.http.Cookie;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.exception.InvalidRequestParameterTypeRuntimeException;
import org.t2framework.confeito.parameter.AbstractParameterResolver;
import org.t2framework.confeito.parameter.ParameterResolver;

/**
 * <#if locale="en">
 * <p>
 * CookieParameterResolver is a concrete class of {@link ParameterResolver}, and
 * is responsible for returning a Cookie.
 * </p>
 * <#else>
 * <p>
 * CookieParameterResolverは、Cookieから値を取り出して返す{@link ParameterResolver}です.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class CookieParameterResolver extends AbstractParameterResolver {

	public CookieParameterResolver() {
		setTargetClass(Cookie.class);
	}

	/**
	 * <#if locale="en">
	 * 
	 * <pre>
	 * Get {@code Cookie} or {@code Cookie}[] if available.
	 * 
	 * [Features of return value]
	 *  The return value of the method must be {@code Cookie} or {@code Cookie}[]. 
	 *  
	 * [In case of fail]
	 *  If there is no {@code Cookie}, simplly return null.
	 *  Or if paramClass is not {@code Cookie}.class or {@code Cookie}[].class, 
	 *  throw InvalidRequestParameterTypeRuntimeException.
	 * 
	 * </pre>
	 * 
	 * <#else>
	 * 
	 * <pre>
	 * {@code Cookie}もしくは{@code Cookie}[]がある場合、取り出します.
	 * 
	 * [戻り値について]
	 *  このメソッドの戻り値は{@code Cookie}もしくは{@code Cookie}[]です. 
	 *  
	 * [値の取得が失敗した場合]
	 *  もし{@code Cookie}が無い場合,nullを返します.
	 *  パラメータのクラスが{@code Cookie}.classもしくは{@code Cookie}[].classで無い場合, 
	 *  InvalidRequestParameterTypeRuntimeExceptionをスローします。
	 * 
	 * </pre>
	 * 
	 * </#if>
	 * 
	 * @param actionContext
	 *            : ActionContext, context instance.
	 * @param md
	 *            : MethodDesc, the target method meta-class instance.
	 * @param paramIndex
	 *            : Index of method parameter.
	 * @param paramAnnotations
	 *            : Parameter annotations for the target parameter.
	 * @param paramClass
	 *            : Parameter class.
	 * @return object : Cookie or Cookie[].
	 * @throws InvalidRequestParameterTypeRuntimeException
	 *             in case of paramClass is not {@code Cookie}.class or {@code
	 *             Cookie}[].class.
	 * @see javax.servlet.http.Cookie
	 */
	@Override
	public Object resolve(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		Cookie[] cookies = actionContext.getRequest().getNativeResource()
				.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		} else if (paramClass == Cookie.class) {
			return cookies[0];
		} else if (paramClass.isArray()
				&& paramClass.getComponentType() == Cookie.class) {
			return cookies;
		}
		throw new InvalidRequestParameterTypeRuntimeException();
	}

}
