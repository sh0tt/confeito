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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.t2framework.confeito.Constants;

/**
 * <#if locale="en">
 * <p>
 * String utility.
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
public final class StringUtil {

	protected StringUtil() {
	}

	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}

	public static String[] split(String str, String delim) {
		if (isEmpty(str)) {
			return Constants.EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str, delim);
		while (st.hasMoreElements()) {
			list.add((String) st.nextElement());
		}
		return list.toArray(new String[list.size()]);
	}

	public static String replace(String text, String fromText, String toText) {
		if (Assertion.hasNull(text, fromText, toText)) {
			return text;
		}
		if (isEmpty(fromText) && isEmpty(toText)) {
			return text;
		}
		StringBuilder buf = new StringBuilder(100);
		int from = 0;
		int to = 0;
		while (true) {
			to = text.indexOf(fromText, from);
			if (to == 0) {
				buf.append(toText);
				from = fromText.length();
			} else if (to > 0) {
				buf.append(text.substring(from, to));
				buf.append(toText);
				from = to + fromText.length();
			} else {
				buf.append(text.substring(from));
				break;
			}
		}
		return buf.toString();
	}

	public static boolean endsWithIgnoreCase(String target1, String target2) {
		if (target1 == null || target2 == null) {
			return false;
		}
		int length1 = target1.length();
		int length2 = target2.length();
		if (length1 < length2) {
			return false;
		}
		String s1 = target1.substring(length1 - length2);
		return s1.equalsIgnoreCase(target2);
	}

	public static String decapitalize(String name) {
		if (isEmpty(name)) {
			return name;
		}
		char chars[] = name.toCharArray();
		if (chars.length >= 2 && Character.isUpperCase(chars[0])
				&& Character.isUpperCase(chars[1])) {
			return name;
		}
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	public static boolean isAllEmpty(String[] regexes) {
		if (regexes == null) {
			return false;
		}
		for (String regex : regexes) {
			if (!isEmpty(regex)) {
				return false;
			}
		}
		return true;
	}

}
