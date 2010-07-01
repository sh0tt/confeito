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

import java.util.HashMap;
import java.util.Map;

import org.t2framework.confeito.Constants;

/**
 * <#if locale="en">
 * <p>
 * Java auto boxing may be caused NullPointerException when converts wrapper to
 * primitive. This utility class helps to convert with return default value when
 * wrapper is null.
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
public class AutoboxingUtil {

	private static Map<Class<?>, Object> primitiveToDefaultValueMap = new HashMap<Class<?>, Object>();
	static {
		primitiveToDefaultValueMap.put(void.class, null);
		primitiveToDefaultValueMap.put(Character.TYPE,
				Constants.CHAR_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Byte.TYPE, Constants.BYTE_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Short.TYPE,
				Constants.SHORT_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Integer.TYPE,
				Constants.INT_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Long.TYPE, Constants.LONG_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Double.TYPE,
				Constants.DOUBLE_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Float.TYPE,
				Constants.FLOAT_DEFAULT_VALUE);
		primitiveToDefaultValueMap.put(Boolean.TYPE,
				Constants.BOOLEAN_DEFAULT_VALUE);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert to {@link Integer} to int.If the {@link Integer} value is null,
	 * return 0.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param i
	 * @return
	 */
	public static int toPrimitiveInt(Integer i) {
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert to {@link Double} to double.If the {@link Double} value is null,
	 * return 0d.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param d
	 * @return
	 */
	public static double toPrimitiveDouble(Double d) {
		if (d == null) {
			return 0d;
		}
		return d.doubleValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert {@link Float} to float.If the {@link Float} value is null, return
	 * 0f.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param f
	 * @return
	 */
	public static float toPrimitiveFloat(Float f) {
		if (f == null) {
			return 0f;
		}
		return f.floatValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert {@link Long} to long.If the {@link Long} value is null, return
	 * 0L.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param l
	 * @return
	 */
	public static long toPrimitiveLong(Long l) {
		if (l == null) {
			return 0L;
		}
		return l.longValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert {@link Short} to short.If the {@link Short} value is null, return
	 * 0.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @return
	 */
	public static short toPrimitiveShort(Short s) {
		if (s == null) {
			return 0;
		}
		return s.shortValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert {@link Character} to char.If the {@link Character} value is null,
	 * return Character.valueOf((char) 0).
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param c
	 * @return
	 */
	public static char toPrimitiveCharacter(Character c) {
		if (c == null) {
			return Constants.CHAR_DEFAULT_VALUE;
		}
		return c.charValue();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert int[] to Integer[].
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param array
	 * @return
	 */
	public static Integer[] toIntegerArray(int[] array) {
		if (array == null || array.length == 0) {
			return Constants.EMPTY_INT_ARRAY;
		}
		Integer[] ret = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = Integer.valueOf(array[i]);
		}
		return ret;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Convert double[] to Double[].
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param array
	 * @return
	 */
	public static Double[] toDoubleArray(double[] array) {
		if (array == null || array.length == 0) {
			return Constants.EMPTY_DOUBLE_ARRAY;
		}
		Double[] ret = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = Double.valueOf(array[i]);
		}
		return ret;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get default value if the primitive type and value is null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object getDefaultValueIfNull(Class<?> type, Object value) {
		if (value != null) {
			return value;
		}
		if (type == int.class) {
			return Constants.INT_DEFAULT_VALUE;
		} else if (type == double.class) {
			return Constants.DOUBLE_DEFAULT_VALUE;
		} else if (type == long.class) {
			return Constants.LONG_DEFAULT_VALUE;
		} else if (type == float.class) {
			return Constants.FLOAT_DEFAULT_VALUE;
		} else if (type == short.class) {
			return Constants.SHORT_DEFAULT_VALUE;
		} else if (type == boolean.class) {
			return Constants.BOOLEAN_DEFAULT_VALUE;
		} else if (type == byte.class) {
			return Constants.BYTE_DEFAULT_VALUE;
		}
		return value;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get default value if the {@link Class} is primitive type.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object getDefaultPrimitiveValue(Class<?> clazz) {
		if (clazz == null || !clazz.isPrimitive()) {
			return null;
		}
		return primitiveToDefaultValueMap.get(clazz);
	}

}
