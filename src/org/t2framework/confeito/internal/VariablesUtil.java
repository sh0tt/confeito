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
package org.t2framework.confeito.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.t2framework.confeito.util.PatternUtil;

/**
 * <#if locale="en">
 * <p>
 * Utility class of collecting value from url template path.
 * 
 * This utility helps
 * {@link org.t2framework.confeito.parameter.ParameterResolver} to resolve
 * expressions in the annotation which T2 should handle, for example it might be
 * {@literal @Page} or {@literal @ActionPath}.
 * </p>
 * <p>
 * The usage of this utility is described below:
 * </p>
 * 
 * <#else>
 * <p>
 * テンプレートURL文字列から、値を取得するためのユーティリティクラスです.
 * 
 * このユーティリティは主に{@link org.t2framework.confeito.parameter.ParameterResolver}
 * でT2で扱うべき{@literal @Page}や{@literal @ActionPath}の式言語部分を解釈するのに使用します.
 * </p>
 * <p>
 * このユーティリティの主な使い方は下記のようになります.
 * </p>
 * 
 * </#if>
 * 
 * <pre>
 * Map&lt;String, String&gt; map = VariablesUtil.createVarMap(&quot;{foo}/{bar}?key={hoge}&quot;,
 * 		&quot;testpath/value1/value2?key=ttt&quot;);
 * assertEquals(&quot;testpath&quot;, map.get(&quot;{foo}&quot;));
 * assertEquals(&quot;value1/value2&quot;, map.get(&quot;{bar}&quot;));
 * assertEquals(&quot;ttt&quot;, map.get(&quot;{hoge}&quot;));
 * </pre>
 * 
 * @author c9katayama
 * @author shot
 * @since 0.6.0
 */
public class VariablesUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * Escape character patterns.
	 * </p>
	 * <#else>
	 * <p>
	 * エスケープすべき文字列のパターンです.
	 * </p>
	 * </#if>
	 */
	protected static final String ESCAPED_CHARS = "\\[].^$?*+()|,:<>=";

	/**
	 * <#if locale="en">
	 * <p>
	 * T2 variables pattern.
	 * </p>
	 * <#else>
	 * <p>
	 * T2の評価式のパターンです.
	 * </p>
	 * </#if>
	 */
	protected static final Pattern VAR_PATTERN = Pattern
			.compile("(\\{([^}]*)\\})");

	/**
	 * <#if locale="en">
	 * <p>
	 * Minimum pattern :at least one or more length we need.
	 * </p>
	 * <#else>
	 * <p>
	 * 式言語内でマッチすべき1文字以上の正規表現です.
	 * </p>
	 * </#if>
	 */
	private static final String VAR_REGEX_PATTERN = "(.+?)";

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Pattern} from possible variable string.
	 * </p>
	 * <#else>
	 * <p>
	 * 式言語の文字列表現から、{@link Pattern}に変換します.
	 * </p>
	 * </#if>
	 * 
	 * @param varPattern
	 * @return pattern instance
	 */
	public static Pattern toPattern(String varPattern) {
		Matcher m = VAR_PATTERN.matcher(varPattern);
		String regex = escapeSpecialChar(varPattern);
		while (m.find()) {
			String var = m.group(1);
			String pat = escapeVarPrefixSuffix(var);
			regex = regex.replaceFirst(pat, VAR_REGEX_PATTERN);
		}
		return PatternUtil.getPattern(regex);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create variable map from pattern and actual value.
	 * </p>
	 * <p>
	 * The key of the map is {key} format and value must not be null. This
	 * method must not return null, and return empty map instead.
	 * </p>
	 * <#else>
	 * <p>
	 * 式言語のキーとURLの該当する文字列を値とするマップを返します.
	 * </p>
	 * <p>
	 * このマップはキーの形式は{key}のようになり、キーがある場合、値がnullになることはありません.
	 * また、このメソッドがnullを返しません.代わりに空のマップを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param pattern
	 * @param value
	 * @return pattern map(key:variable name, value:value)
	 */
	public static Map<String, String> createVarMap(String pattern, String value) {
		Matcher m = VAR_PATTERN.matcher(pattern);
		String regex = escapeSpecialChar(pattern);

		List<String> varNameList = new ArrayList<String>();
		while (m.find()) {
			String var = m.group(1);
			String pat = escapeVarPrefixSuffix(var);
			regex = regex.replaceFirst(pat, VAR_REGEX_PATTERN);
			varNameList.add(var);
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		Pattern p = PatternUtil.getPattern(regex);
		m = p.matcher(value);
		if (m.matches()) {
			int groupCount = m.groupCount();
			for (int i = 0; i < groupCount; i++) {
				resultMap.put(varNameList.get(i), m.group(i + 1));
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Escape variable key.
	 * </p>
	 * <#else>
	 * <p>
	 * キーをエスケープします.
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @return
	 */
	private static String escapeVarPrefixSuffix(String s) {
		s = s.substring(1);
		s = s.substring(0, s.length() - 1);
		s = escapeSpecialChar(s);
		return "\\{" + s + "\\}";
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Escapape special chatacter.
	 * </p>
	 * <#else>
	 * <p>
	 * 特殊なキャラクタ({@link VariablesUtil#ESCAPED_CHARS})をエスケープします.
	 * </p>
	 * </#if>
	 * 
	 * @param s
	 * @return escaped string
	 */
	public static String escapeSpecialChar(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ESCAPED_CHARS.indexOf(ch) >= 0) {
				sb.append('\\');
			}
			sb.append(ch);
		}
		return sb.toString();
	}
}
