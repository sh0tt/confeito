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

import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <#if locale="en">
 * <p>
 * {@link DecimalFormatSymbols} utility.
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
public class DecimalFormatUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Special character for initialization.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static Character[] SPECIAL_CURRENCY_SYMBOLS = new Character[] {
			'\\', '$' };

	protected static boolean initialized = false;

	/**
	 * <#if locale="en">
	 * <p>
	 * Normalize as decimal format.
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
	public static String normalize(String s) {
		return normalize(s, Locale.getDefault());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Normalize as decimal format with locale.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @param locale
	 * @return
	 */
	public static String normalize(String s, Locale locale) {
		if (StringUtil.isEmpty(s)) {
			return null;
		}
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		char groupingSeparator = symbols.getGroupingSeparator();
		char decimalSeparator = symbols.getDecimalSeparator();
		final StringBuilder builder = new StringBuilder(20);
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if (c == groupingSeparator) {
				continue;
			} else if (c == decimalSeparator) {
				c = '.';
			} else if (ArrayUtil.contains(SPECIAL_CURRENCY_SYMBOLS, Character
					.valueOf(c))) {
				continue;
			}
			builder.append(c);
		}
		return builder.toString();
	}

	public static void addSpecialCharacterSymbol(char c) {
		ArrayUtil.add(SPECIAL_CURRENCY_SYMBOLS, c);
	}
}
