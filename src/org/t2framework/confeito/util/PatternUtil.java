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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <#if locale="en">
 * <p>
 * Pattern utility.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @see java.util.regex.Pattern
 * @see java.util.regex.Matcher
 */
public class PatternUtil {

	private static final Map<String, Pattern> patternCache = new HashMap<String, Pattern>();

	protected PatternUtil() {
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Pattern} by regex.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param regex
	 * @return
	 */
	public static Pattern getPattern(String regex) {
		return getPattern(regex, 0);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Pattern} by regex and match flags.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param regex
	 * @param flag
	 * @return
	 */
	public static Pattern getPattern(String regex, int flag) {
		Assertion.notNull(regex);
		Pattern pattern = (Pattern) patternCache.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex, flag);
			patternCache.put(regex, pattern);
		}
		return pattern;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Match if regex and char sequence.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param regex
	 * @param value
	 * @return
	 */
	public static boolean matches(String regex, CharSequence value) {
		Assertion.notNull(regex);
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Clear pattern cache.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static void clearPatternCache() {
		patternCache.clear();
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Pattern} list.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param regexes
	 * @return
	 */
	public static List<Pattern> getPatterns(String[] regexes) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		for (String regex : regexes) {
			if (regex == null) {
				continue;
			}
			patterns.add(getPattern(regex));
		}
		return patterns;
	}
}
