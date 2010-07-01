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

import java.lang.reflect.Array;

/**
 * <#if locale="en">
 * <p>
 * ArrayUtil is an utility class for processing array.
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
public class ArrayUtil {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Check if array is empty or null.
	 * </p>
	 * <#else>
	 * <p>
	 * 配列が、nullか空の配列の場合にtrueを返します．
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Add new value to exsisting array.The new value is indexed to the last.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param current
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] add(T[] current, T value) {
		Assertion.notNull(current);
		T[] newone = (T[]) Array.newInstance(current.getClass()
				.getComponentType(), current.length + 1);
		copyAll(current, newone);
		newone[current.length] = value;
		return newone;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Add new value, which sets as the first value, to existing array.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param current
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addFirst(T[] current, T value) {
		Assertion.notNull(current);
		T[] newone = (T[]) Array.newInstance(current.getClass()
				.getComponentType(), current.length + 1);
		copy(current, newone, 0, 1, current.length);
		newone[0] = value;
		return newone;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Add array.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addAll(T[] from, T[] to) {
		Assertion.notNulls(from, to);
		T[] newone = (T[]) Array.newInstance(
				from.getClass().getComponentType(), from.length + to.length);
		copyAll(from, newone);
		copy(to, newone, 0, from.length, to.length);
		return newone;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Copy array.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T> T[] copyAll(T[] from, T[] to) {
		System.arraycopy(from, 0, to, 0, from.length);
		return to;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Copy array with from and to position.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param from
	 * @param to
	 * @param fromPos
	 * @param toPos
	 * @param length
	 * @return
	 */
	public static <T> T[] copy(T[] from, T[] to, int fromPos, int toPos,
			int length) {
		System.arraycopy(from, fromPos, to, toPos, length);
		return to;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return true if the array contains the element.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param array
	 * @param element
	 * @return
	 */
	public static <T> boolean contains(T[] array, T element) {
		if (array == null || element == null) {
			return false;
		}
		for (T t : array) {
			if (element.equals(t)) {
				return true;
			}
		}
		return false;
	}
}
