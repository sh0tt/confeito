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
 * Escape html entities. You can plug in HtmlEscapeStrategy and change behavior.
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
public class HtmlEscapeUtil {

	private HtmlEscapeUtil() {
	}

	private static HtmlEscapeStrategy escapeStrategy = new DefaultHtmlEscapeStrategy();

	public static HtmlEscapeStrategy getHtmlEscapeStrategy() {
		return escapeStrategy;
	}

	public static void setHtmlEscapeStrategy(
			HtmlEscapeStrategy htmlEscapeStrategy) {
		escapeStrategy = htmlEscapeStrategy;
	}

	public static String escape(final String s) {
		return escape(s, true, true);
	}

	public static String escape(final String s, final boolean quote,
			final boolean amp) {
		return escapeStrategy.escape(s, quote, amp);
	}

	public static interface HtmlEscapeStrategy {

		public String escape(final String s, final boolean quote,
				final boolean amp);

	}

	public static abstract class AbstractHtmlEscapeStrategy implements
			HtmlEscapeStrategy {

		public String escape(final String s, final boolean quote,
				final boolean amp) {
			char[] chars = s.toCharArray();
			StringBuffer sb = new StringBuffer(s.length() + 64);
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				escapeEach(sb, c, quote, amp);
			}
			return new String(sb);
		}

		protected abstract void escapeEach(final StringBuffer buf,
				final char c, final boolean quote, final boolean amp);
	}

	public static class DefaultHtmlEscapeStrategy extends
			AbstractHtmlEscapeStrategy {

		protected void escapeEach(final StringBuffer sb, final char c,
				final boolean quote, final boolean amp) {
			if ((int) c == '\u00A0') {
				sb.append("&nbsp;");
			} else if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (amp && c == '&') {
				sb.append("&amp;");
			} else if (c == '"') {
				sb.append("&quot;");
			} else if (quote && c == '\'') {
				sb.append("&#39;");
			} else if ((int) c == '\u00A5') {
				sb.append("&yen;");
			} else {
				sb.append(c);
			}
		}

	}

	public static class JapaneseHtmlEscapeStrategy extends
			AbstractHtmlEscapeStrategy {

		protected void escapeEach(final StringBuffer sb, final char c,
				final boolean quote, final boolean amp) {
			if ((int) c == '\u00A0') {
				sb.append("&nbsp;");
			} else if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (amp && c == '&') {
				sb.append("&amp;");
			} else if (c == '"') {
				sb.append("&quot;");
			} else if (quote && c == '\'') {
				sb.append("&#39;");
			} else if ((int) c == '\u00A5' || (int) c == '\u005C\') {
				sb.append("&yen;");
			} else {
				sb.append(c);
			}
		}

	}
}
