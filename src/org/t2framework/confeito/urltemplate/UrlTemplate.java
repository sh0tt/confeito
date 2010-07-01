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
package org.t2framework.confeito.urltemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.internal.VariablesUtil;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.PatternUtil;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * UrlTemplate is a template class to parse the expression of url for T2.
 * </p>
 * <#else>
 * <p>
 * UrlTemplateはリクエストされたURLとT2の簡易式言語で書かれたURLをマッチングしたり、
 * パースして式言語の記述該当部分を取り出したりするためのインタフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class UrlTemplate {

	/**
	 * <#if locale="en">
	 * <p>
	 * Template expression pattern which {@code UrlTemplate} implementation
	 * use.Basically, expression will be {key} format.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link UrlTemplate}の実装クラスが使う式言語のパターンです.原則{key}のような形式です.
	 * </p>
	 * </#if>
	 */
	Pattern TEMPLATE_PATTERN = PatternUtil.getPattern("([{]([^}]+)[}])([^{]*)");

	/**
	 * <#if locale="en">
	 * <p>
	 * Acceptable strings of expression.
	 * </p>
	 * <#else>
	 * <p>
	 * 式言語のキーとして許可される文字列のパターンです.
	 * </p>
	 * </#if>
	 */
	String ACCEPTABLE_STR = "([-a-zA-Z0-9!@%|_^$.*+'(),]+)";

	/**
	 * <#if locale="en">
	 * <p>
	 * The original path of this {@link UrlTemplateImpl} instance.
	 * </p>
	 * <#else>
	 * <p>
	 * この{@link UrlTemplateImpl}のオリジナルのテンプレートパス
	 * </p>
	 * </#if>
	 */
	protected final String templatePath;

	/**
	 * <#if locale="en">
	 * <p>
	 * The patterns which extract from the template path.
	 * </p>
	 * <#else>
	 * <p>
	 * テンプレートパスから抽出されたパターン
	 * </p>
	 * </#if>
	 */
	protected List<Pattern> patterns = new ArrayList<Pattern>();

	/**
	 * <#if locale="en">
	 * <p>
	 * The expression names which extract from the template path, like aaa from
	 * {aaa}.
	 * </p>
	 * <#else>
	 * <p>
	 * テンプレートパスから抽出された式の変数名.
	 * </p>
	 * </#if>
	 */
	protected List<String> expressionNames = new ArrayList<String>();

	/**
	 * <#if locale="en">
	 * <p>
	 * True if the template path contain an expression, otherwise false.
	 * </p>
	 * <#else>
	 * <p>
	 * テンプレートパスに式が含まれている場合trueを、それ以外false
	 * </p>
	 * </#if>
	 */
	protected boolean hasExpression;

	public UrlTemplate(final String templatePath) {
		this(templatePath, false);
	}

	public UrlTemplate(final String templatePath, boolean partialMatch) {
		this.templatePath = templatePath;
		init(templatePath, partialMatch);
	}

	protected void init(final String templatePath, boolean partialMatch) {
		String s = VariablesUtil.escapeSpecialChar(templatePath);
		s = PathUtil.appendStartSlashIfNeed(s);
		Matcher matcher = createMatcher(s);
		while (matcher.find()) {
			final String name = matcher.group(2);
			final String expression = matcher.group(1);
			final String[] names = name.split(",", 2);
			if (names.length == 1) {
				s = StringUtil.replace(s, expression, ACCEPTABLE_STR);
				addExpressionName(name);
			} else {
				for (String str : names) {
					addExpressionName(str);
				}
				s = StringUtil.replace(s, expression, ACCEPTABLE_STR);
			}
		}
		if (partialMatch) {
			if (s.endsWith("/")) {
				s += "(.*)";
			} else {
				s += "(/.*|)";
			}
		}
		patterns.add(PatternUtil.getPattern(s));
	}

	protected void addExpressionName(final String name) {
		if (expressionNames.contains(name) == false) {
			expressionNames.add(name);
			hasExpression = true;
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return true if the given url and template match, otherwise return false.
	 * 
	 * The testing code using this method is below:
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}&quot;);
	 * assertTrue(template.match(&quot;/hoge/aaaa&quot;));
	 * </pre>
	 * 
	 * <#else>
	 * <p>
	 * この{@link UrlTemplate}とリクエストされたURLが同一構成の場合、trueを返します. 以下のようなコードで試す事が出来ます.
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}&quot;);
	 * assertTrue(template.match(&quot;/hoge/aaaa&quot;));
	 * </pre>
	 * 
	 * </#if>
	 * 
	 * @param urlPath
	 * @return true if the given path is matched by this template, otherwise
	 *         false
	 */

	public boolean match(String urlPath) {
		Assertion.notNull(urlPath);
		for (Pattern p : patterns) {
			if (p.matcher(urlPath).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Parse given parameter and return map of template-key and actual
	 * value.This method may return null.
	 * </p>
	 * <#else>
	 * <p>
	 * 与えられたURLとテンプレートを使ってパースし、式言語のキーと実際のURLの値をマップとして返します. このメソッドはnullを返す事があります.
	 * </p>
	 * </#if>
	 * 
	 * @param urlPath
	 * @return parse result as map
	 */
	public Map<String, String> parseUrl(String urlPath) {
		Assertion.notNull(urlPath);
		String path = PathUtil.appendStartSlashIfNeed(urlPath);
		if (match(path) == false) {
			return null;
		}
		String[] templateStrs = StringUtil.split(templatePath, "/");
		String[] pathStrs = StringUtil.split(path, "/");

		Map<String, String> retMap = new HashMap<String, String>();
		for (int i = 0; i < templateStrs.length; i++) {
			String key = templateStrs[i];
			final int startIndex = key.indexOf("{");
			final int lastIndex = key.lastIndexOf("}");
			if (startIndex < 0 || lastIndex <= 0) {
				continue;
			}
			String value = pathStrs[i];
			if (startIndex == 0 && lastIndex == key.length() - 1) {
				retMap.put(key, value);
			} else {
				final String choppedKey = key.substring(startIndex,
						lastIndex + 1);
				if (startIndex != 0) {
					value = value.substring(startIndex);
				}
				if (lastIndex < key.length() - 1) {
					final String postfix = key.substring(lastIndex + 1);
					int postfixPos = value.lastIndexOf(postfix);
					if (0 < postfixPos) {
						int len = 0;
						for (int j = value.length() - 1, k = postfix.length() - 1; -1 < j
								&& -1 < k; j--, k--) {
							char ch = value.charAt(j);
							if (ch == postfix.charAt(k)) {
								len++;
							}
						}
						value = value.substring(0, value.length() - len);
					}
				}
				retMap.put(choppedKey, value);
			}
		}
		return retMap;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return list of url patterns.This method must not return null, and return
	 * empty list instead.
	 * </p>
	 * <#else>
	 * <p>
	 * このテンプレートで保持するURLのリストを返します.このメソッドはnullを返しません．代わりに空のリストを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return list of url pattens
	 */
	public List<Pattern> getUrlPattern() {
		return patterns;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Bind and create url by given parameter map.
	 * 
	 * Look at the unit testing code below:
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}/{bar}&quot;);
	 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
	 * map.put(&quot;foo&quot;, &quot;A&quot;);
	 * map.put(&quot;bar&quot;, &quot;B&quot;);
	 * String s = template.bindByName(map);
	 * assertEquals(&quot;/hoge/A/B&quot;, s);
	 * 
	 * </pre>
	 * 
	 * <#else>
	 * <p>
	 * 与えられたマップとテンプレートをバインドして、URLの文字列を返します.
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}/{bar}&quot;);
	 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
	 * map.put(&quot;foo&quot;, &quot;A&quot;);
	 * map.put(&quot;bar&quot;, &quot;B&quot;);
	 * String s = template.bindByName(map);
	 * assertEquals(&quot;/hoge/A/B&quot;, s);
	 * 
	 * </pre>
	 * 
	 * </#if>
	 * 
	 * @param parameters
	 * @return bound url
	 */
	public String bindByName(Map<String, String> parameters) {
		Assertion.notNull(parameters);
		String s = this.templatePath;
		Matcher matcher = createMatcher(s);
		while (matcher.find()) {
			final String name = matcher.group(2);
			final String[] names = name.split(",", 2);
			if (names.length == 1) {
				s = StringUtil.replace(s, matcher.group(1), parameters
						.get(name));
			} else {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < names.length; ++i) {
					final String value = parameters.get(names[i]);
					builder.append(value);
					builder.append(",");
				}
				builder.setLength(builder.length() - 1);
				s = StringUtil.replace(s, matcher.group(1), builder.toString());
			}
		}
		return s;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Bind given parameters by order. Look at the unit testing code below:
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}/{bar}&quot;);
	 * String s = template.bindByPosition(&quot;A&quot;, &quot;B&quot;);
	 * assertEquals(&quot;/hoge/A/B&quot;, s);
	 * </pre>
	 * 
	 * <#else>
	 * <p>
	 * テンプレートと与えられた文字列をバインドして、URLの文字列を返します.バインドはテンプレートの式言語部分に対して、順番に適用されます.
	 * テストコードは下記のようになります.
	 * </p>
	 * 
	 * <pre>
	 * UrlTemplateImpl template = new UrlTemplateImpl(&quot;/hoge/{foo}/{bar}&quot;);
	 * String s = template.bindByPosition(&quot;A&quot;, &quot;B&quot;);
	 * assertEquals(&quot;/hoge/A/B&quot;, s);
	 * </pre>
	 * 
	 * </#if>
	 * 
	 * @param values
	 * @return bound result.
	 */
	public String bindByPosition(String... values) {
		String s = this.templatePath;
		Matcher matcher = createMatcher(s);
		if (matcher.groupCount() - 1 > values.length) {
			throw new IllegalStateException();
		}
		int index = 0;
		while (matcher.find()) {
			final String name = matcher.group(2);
			final String[] names = name.split(",", 2);
			if (names.length == 1) {
				s = StringUtil.replace(s, matcher.group(1), values[index]);
			} else {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < names.length; ++i) {
					builder.append(values[i]);
					builder.append(",");
				}
				builder.setLength(builder.length() - 1);
				s = StringUtil.replace(s, matcher.group(1), builder.toString());
			}
			index++;
		}
		return s;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get list of expression names.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return list of expression names
	 */
	public List<String> getExpressionNames() {
		return expressionNames;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get template path.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return template path
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * True if there is an expression.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if expression exists, otherwise false
	 */
	public boolean hasExpression() {
		return hasExpression;
	}

	protected Matcher createMatcher(String s) {
		return TEMPLATE_PATTERN.matcher(s);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("@");
		builder.append(this.hashCode());
		builder.append("[");
		builder.append("templatePath=").append(templatePath).append(", ");
		builder.append("patterns=").append(patterns).append(", ");
		builder.append("expressionNames=").append(expressionNames).append(", ");
		builder.append("hasExpression=").append(hasExpression).append(", ");
		builder.append("]");
		return new String(builder);
	}

}
