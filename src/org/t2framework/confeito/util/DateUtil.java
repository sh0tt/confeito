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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * <#if locale="en">
 * <p>
 * Date utility.
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
public class DateUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link SimpleDateFormat} by pattern and locale.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param pattern
	 * @param locale
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(final String pattern,
			final Locale locale) {
		if (pattern != null) {
			return new SimpleDateFormat(pattern, locale);
		} else {
			String p = findPattern(locale);
			return new SimpleDateFormat(p, locale);
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Find pattern by locale.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param locale
	 * @return
	 */
	public static String findPattern(Locale locale) {
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance(
				DateFormat.SHORT, locale);
		String pattern = df.toPattern();
		int index = pattern.indexOf(' ');
		if (index > 0) {
			pattern = pattern.substring(0, index);
		}
		if (pattern.indexOf("yyyy") < 0) {
			pattern = StringUtil.replace(pattern, "yy", "yyyy");
		}
		if (pattern.indexOf("MM") < 0) {
			pattern = StringUtil.replace(pattern, "M", "MM");
		}
		if (pattern.indexOf("dd") < 0) {
			pattern = StringUtil.replace(pattern, "d", "dd");
		}
		return pattern;
	}

}
