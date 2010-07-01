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
package org.t2framework.confeito.util;

import java.beans.Introspector;
import java.lang.reflect.Method;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.util.Reflections.MethodUtil;

/**
 * <#if locale="en">
 * <p>
 * JavaBeans utility.
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
public class JavaBeansUtil {

	protected JavaBeansUtil() {
	}

	public static String capitalize(String name) {
		if (StringUtil.isEmpty(name)) {
			return name;
		}
		char chars[] = name.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static String decapitalize(String name) {
		return Introspector.decapitalize(name);
	}

	public static String getPropertyName(String methodName) {
		if (methodName == null) {
			return null;
		}
		final int length = methodName.length();
		if (methodName.startsWith(Constants.GET)
				|| methodName.startsWith(Constants.SET)) {
			String s = methodName.substring(Constants.GET_LENGTH, length);
			return decapitalize(s);
		} else if (methodName.startsWith(Constants.IS)) {
			String s = methodName.substring(Constants.IS_LENGTH, length);
			return decapitalize(s);
		} else {
			return null;
		}
	}

	public static <T> Method getReadMethodFromWriteMethod(Class<T> c,
			Method writeMethod) {
		Assertion.notNull(c);
		Assertion.notNull(writeMethod);
		final String methodName = writeMethod.getName();
		String propertyName = getPropertyName(methodName);
		Class<?>[] params = writeMethod.getParameterTypes();
		Class<?> param = params[0];
		String prefix = (params.length == 1 && (param == Boolean.class || param == Boolean.TYPE)) ? Constants.IS
				: Constants.GET;
		String readMethodName = prefix + capitalize(propertyName);
		return MethodUtil.getDeclaredMethodNoException(c, readMethodName,
				Constants.EMPTY_CLASS_ARRAY);
	}

	public static <T> Method getWriteMethodFromReadMethod(Class<T> c,
			Method readMethod) {
		Assertion.notNull(c);
		Assertion.notNull(readMethod);
		final String methodName = readMethod.getName();
		String propertyName = getPropertyName(methodName);
		String writeMethodName = Constants.SET + capitalize(propertyName);
		return MethodUtil.getDeclaredMethodNoException(c, writeMethodName,
				new Class<?>[] { readMethod.getReturnType() });
	}

	public static boolean isGetMethod(String methodName, Method m) {
		if (methodName.startsWith(Constants.GET) == false) {
			return false;
		}
		final Class<?>[] paramTypes = m.getParameterTypes();
		final Class<?> returnType = m.getReturnType();
		return (paramTypes.length == 0 && returnType != Void.TYPE && methodName
				.equals("getClass") == false);
	}

	public static boolean isSetMethod(String methodName, Method m) {
		if (methodName.startsWith(Constants.SET) == false) {
			return false;
		}
		final Class<?> declaringClass = m.getDeclaringClass();
		final Class<?>[] paramTypes = m.getParameterTypes();
		final Class<?> returnType = m.getReturnType();
		return (paramTypes.length == 1
				&& (returnType == Void.TYPE || returnType == declaringClass) && methodName
				.equals("setClass") == false);
	}

	public static boolean isIsMethod(String methodName, Method m) {
		if (methodName.startsWith(Constants.IS) == false) {
			return false;
		}
		final Class<?>[] paramTypes = m.getParameterTypes();
		final Class<?> returnType = m.getReturnType();
		return (paramTypes.length == 0 && returnType.equals(Boolean.TYPE));
	}

	public static boolean isSetMethod(String methodName) {
		if (StringUtil.isEmpty(methodName)) {
			return false;
		}
		if (!methodName.startsWith(Constants.SET)) {
			return false;
		}
		int len = Constants.SET_LENGTH + 1;
		return isAppliedJavaBeanMethod(methodName, len);
	}

	public static boolean isIsMethod(String methodName) {
		if (StringUtil.isEmpty(methodName)) {
			return false;
		}
		if (methodName.startsWith(Constants.IS) == false) {
			return false;
		}
		int len = Constants.IS_LENGTH + 1;
		return isAppliedJavaBeanMethod(methodName, len);
	}

	private static boolean isAppliedJavaBeanMethod(String methodName, int len) {
		if (methodName.length() > len) {
			return Character.isUpperCase(methodName.charAt(len - 1));
		} else {
			return false;
		}
	}

	public static Class<?> findPropertyTypeFromWriteMethod(Method writeMethod) {
		Assertion.notNull(writeMethod);
		Class<?>[] parameterTypes = writeMethod.getParameterTypes();
		if (parameterTypes.length > 0) {
			return parameterTypes[0];
		}
		throw new IllegalStateException(
				"can not find property from write method : "
						+ writeMethod.getName());
	}

}
