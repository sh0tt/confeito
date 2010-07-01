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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.t2framework.confeito.exception.ClassNotFoundRuntimeException;
import org.t2framework.confeito.exception.IllegalAccessRuntimeException;
import org.t2framework.confeito.exception.InstantiationRuntimeException;
import org.t2framework.confeito.exception.InvocationTargetRuntimeException;
import org.t2framework.confeito.exception.NoSuchMethodRuntimeException;

/**
 * <#if locale="en">
 * <p>
 * Reflections is an utility class group to do some reflection. There are :
 * </p>
 * 
 * <pre>
 *  ClassLoaderUtil
 *  PackageUtil
 *  ClassUtil
 *  ConstructorUtil
 *  FieldUtil
 *  MethodUtil
 *  GenericsUtil
 * </pre>
 * 
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class Reflections {

	public static class PackageUtil {

		private PackageUtil() {
		}

		public static String getPackageName(final Class<?> c) {
			Assertion.notNull(c);
			final Package p = c.getPackage();
			if (p == null) {
				final String name = c.getName();
				return name.substring(0, name.lastIndexOf("."));
			}
			return p.getName();
		}

	}

	public static class ClassUtil {

		private static Map<Class<?>, Class<?>> wrapperToPrimitiveMap = new HashMap<Class<?>, Class<?>>();

		private static Map<Class<?>, Class<?>> primitiveToWrapperMap = new HashMap<Class<?>, Class<?>>();

		static {
			wrapperToPrimitiveMap.put(Character.class, Character.TYPE);
			wrapperToPrimitiveMap.put(Byte.class, Byte.TYPE);
			wrapperToPrimitiveMap.put(Short.class, Short.TYPE);
			wrapperToPrimitiveMap.put(Integer.class, Integer.TYPE);
			wrapperToPrimitiveMap.put(Long.class, Long.TYPE);
			wrapperToPrimitiveMap.put(Double.class, Double.TYPE);
			wrapperToPrimitiveMap.put(Float.class, Float.TYPE);
			wrapperToPrimitiveMap.put(Boolean.class, Boolean.TYPE);

			primitiveToWrapperMap.put(Character.TYPE, Character.class);
			primitiveToWrapperMap.put(Byte.TYPE, Byte.class);
			primitiveToWrapperMap.put(Short.TYPE, Short.class);
			primitiveToWrapperMap.put(Integer.TYPE, Integer.class);
			primitiveToWrapperMap.put(Long.TYPE, Long.class);
			primitiveToWrapperMap.put(Double.TYPE, Double.class);
			primitiveToWrapperMap.put(Float.TYPE, Float.class);
			primitiveToWrapperMap.put(Boolean.TYPE, Boolean.class);
		}

		public static <T> Class<T> forName(final String className) {
			final String name = Assertion.notNull(className);
			final ClassLoader loader = Assertion.notNull(Thread.currentThread()
					.getContextClassLoader());
			return forName(name, loader);
		}

		@SuppressWarnings("unchecked")
		public static <T> Class<T> forName(final String className,
				final ClassLoader loader) {
			try {
				return (Class<T>) Class.forName(className, true, loader);
			} catch (final ClassNotFoundException e) {
				throw new ClassNotFoundRuntimeException(className, e);
			}
		}

		public static <T> Class<T> forNameNoException(final String className) {
			final String name = Assertion.notNull(className);
			final ClassLoader loader = Assertion.notNull(Thread.currentThread()
					.getContextClassLoader());
			return forNameNoException(name, loader);
		}

		@SuppressWarnings("unchecked")
		public static <T> Class<T> forNameNoException(final String className,
				final ClassLoader loader) {
			try {
				return (Class<T>) Class.forName(className, true, loader);
			} catch (final Throwable ignore) {
				return null;
			}
		}

		public static <T> T newInstanceByName(final String className) {
			Assertion.notNull(className);
			Class<T> c = forName(className);
			return (T) newInstance(c);
		}

		public static <T> T newInstanceByNameNoException(final String className) {
			Class<T> clazz = forNameNoException(className);
			try {
				return clazz.newInstance();
			} catch (final Throwable ignore) {
				return null;
			}
		}

		public static <T> T newInstance(final Class<T> clazz)
				throws InstantiationRuntimeException,
				IllegalAccessRuntimeException {
			try {
				return clazz.newInstance();
			} catch (final InstantiationException e) {
				throw new InstantiationRuntimeException(clazz, e);
			} catch (final IllegalAccessException e) {
				throw new IllegalAccessRuntimeException(clazz, e);
			}
		}

		@SuppressWarnings("unchecked")
		public static boolean isAssignableFrom(Class from, Class to) {
			final boolean fromPrimitive = from.isPrimitive();
			if (to == Object.class && !fromPrimitive) {
				return true;
			}
			final boolean toPrimitive = to.isPrimitive();
			if (!fromPrimitive && toPrimitive || fromPrimitive && !toPrimitive) {
				return false;
			}
			return to.isAssignableFrom(from);
		}

		public static Class<?> toWrapperClass(Class<?> clazz) {
			Class<?> c = primitiveToWrapperMap.get(clazz);
			if (c != null) {
				return c;
			} else {
				return clazz;
			}
		}

		public static String getClassName(Class<?> clazz) {
			Assertion.notNull(clazz);
			if (clazz.isArray()) {
				return clazz.getName() + "[]";
			} else {
				return clazz.getName();
			}
		}

		public static String getSimpleClassName(Class<?> clazz) {
			if (clazz == null) {
				return null;
			}
			String className = getClassName(clazz);
			return className.substring(className.lastIndexOf('.') + 1,
					className.length());
		}

		public static String getShortClassName(Class<?> clazz) {
			return getShortClassName(clazz.getCanonicalName());
		}

		public static String getShortClassName(String className) {
			int i = className.lastIndexOf('.');
			if (i > 0) {
				return className.substring(i + 1);
			}
			return className;
		}

		public static List<Class<?>> getAllInterfaces(final Class<?> clazz) {
			List<Class<?>> allInterfaceList = new ArrayList<Class<?>>();
			getAllInterfaces0(allInterfaceList, clazz);
			return allInterfaceList;
		}

		private static void getAllInterfaces0(List<Class<?>> allInterfaceList,
				final Class<?> clazz) {
			if (clazz == Object.class) {
				return;
			}
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> interfase : interfaces) {
				if (allInterfaceList.contains(interfase) == false) {
					allInterfaceList.add(interfase);
				}
				getAllInterfaces0(allInterfaceList, interfase);
			}
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				getAllInterfaces0(allInterfaceList, superClass);
			}
		}
	}

	public static class MethodUtil {

		public static Method getDeclaredMethod(Class<?> clazz,
				String methodName, Class<?>[] parameterTypes) {
			return getDeclaredMethod(clazz, methodName, parameterTypes, false);
		}

		public static Method getDeclaredMethod(Class<?> clazz,
				String methodName, Class<?>[] parameterTypes,
				boolean lookupSuperClass) {
			Assertion.notNulls(clazz, methodName);
			try {
				return clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (SecurityException e) {
				throw e;
			} catch (NoSuchMethodException e) {
				if (lookupSuperClass) {
					clazz = clazz.getSuperclass();
					if (clazz != null) {
						return getDeclaredMethod(clazz, methodName,
								parameterTypes, true);
					}
				}
				throw new NoSuchMethodRuntimeException(clazz, methodName,
						parameterTypes, e);
			}
		}

		public static Method getDeclaredMethodNoException(Class<?> clazz,
				String methodName, Class<?>[] parameterTypes) {
			Assertion.notNulls(clazz, methodName);
			try {
				return clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (SecurityException e) {
				return null;
			} catch (NoSuchMethodException e) {
				return null;
			}
		}

		public static Method getMethodNoException(Class<?> clazz,
				String methodName, Class<?>[] parameterTypes) {
			Assertion.notNulls(clazz, methodName);
			try {
				return clazz.getMethod(methodName, parameterTypes);
			} catch (SecurityException e) {
				return null;
			} catch (NoSuchMethodException e) {
				return null;
			}
		}

		public static Method getMethod(Class<?> clazz, String methodName,
				Class<?>[] argTypes) {
			try {
				return clazz.getMethod(methodName, argTypes);
			} catch (NoSuchMethodException ex) {
				throw new NoSuchMethodRuntimeException(clazz, methodName,
						argTypes, ex);
			}
		}

		@SuppressWarnings("unchecked")
		public static <T> T invokeWithVarargs(Method method, Object target,
				Object[] args) {
			Assertion.notNull(method);
			Object ret = null;
			if (method.isVarArgs() == false) {
				ret = invoke(method, target, args);
			} else {
				ret = invoke(method, target, new Object[] { args });
			}
			return (T) ret;
		}

		@SuppressWarnings("unchecked")
		public static <T> T invoke(Method method, Object target, Object[] args)
				throws InstantiationRuntimeException,
				IllegalAccessRuntimeException {
			Assertion.notNull(method);
			try {
				return (T) method.invoke(target, args);
			} catch (InvocationTargetException ex) {
				Throwable t = ex.getCause();
				if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				}
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new InvocationTargetRuntimeException(method, ex);
			} catch (IllegalAccessException ex) {
				throw new IllegalAccessRuntimeException(method
						.getDeclaringClass(), method.getReturnType(), ex);
			}
		}

		public static boolean isAbstract(Method method) {
			int mod = method.getModifiers();
			return Modifier.isAbstract(mod);
		}

		public static String getSignature(String methodName, Object[] methodArgs) {
			StringBuffer buf = new StringBuffer(100);
			buf.append(methodName);
			buf.append("(");
			if (methodArgs != null) {
				for (int i = 0; i < methodArgs.length; ++i) {
					if (i > 0) {
						buf.append(", ");
					}
					if (methodArgs[i] != null) {
						buf.append(methodArgs[i].getClass().getName());
					} else {
						buf.append("null");
					}
				}
			}
			buf.append(")");
			return buf.toString();
		}

		public static boolean isEqualsMethod(Method method) {
			return method != null && method.getName().equals("equals")
					&& method.getReturnType() == boolean.class
					&& method.getParameterTypes().length == 1
					&& method.getParameterTypes()[0] == Object.class;
		}

		public static boolean isHashCodeMethod(Method method) {
			return method != null && method.getName().equals("hashCode")
					&& method.getReturnType() == int.class
					&& method.getParameterTypes().length == 0;
		}

		public static boolean isToStringMethod(Method method) {
			return method != null && method.getName().equals("toString")
					&& method.getReturnType() == String.class
					&& method.getParameterTypes().length == 0;
		}

	}

}
