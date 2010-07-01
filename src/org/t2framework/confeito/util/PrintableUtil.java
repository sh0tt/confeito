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

/**
 * <#if locale="en">
 * <p>
 * Just pretty print utility.
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
public class PrintableUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Print pretty with array.
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
	 * @return
	 */
	public static <T> String toPrintableString(T[] array) {
		if (array == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (T t : array) {
			builder.append(t);
			builder.append(", ");
		}
		return toPrintableString0(builder);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Print pretty with {@link Iterable}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> String toPrintableString(Iterable<T> iterable) {
		if (iterable == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (T t : iterable) {
			builder.append(t);
			builder.append(", ");
		}
		return toPrintableString0(builder);
	}

	private static String toPrintableString0(StringBuilder builder) {
		final int len = builder.length();
		if (1 < len) {
			builder.setLength(len - 2);
		}
		return new String(builder);
	}
}
