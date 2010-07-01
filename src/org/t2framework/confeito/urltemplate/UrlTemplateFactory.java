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

import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.StringUtil;

/**
 * <#if locale="en">
 * <p>
 * A factory class for {@link UrlTemplate}. It creates {@link UrlTemplate} from
 * just template path or template path with prefix.
 * </p>
 * <#else>
 * <p>
 * {@link UrlTemplate}のファクトリです.テンプレートのURL文字列から{@link UrlTemplate}を生成します.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class UrlTemplateFactory {

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link UrlTemplate} by path.
	 * </p>
	 * <#else>
	 * <p>
	 * テンプレートのURL文字列から{@link UrlTemplate}を取得します.
	 * </p>
	 * </#if>
	 * 
	 * @param templatePath
	 * @return url template
	 */
	public UrlTemplate getUrlTemplate(final String templatePath) {
		Assertion.notNull(templatePath);
		return createTemplate(templatePath);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link UrlTemplate} by prefix and template path.Prefix and template
	 * path concat with slash like aaa/bbb.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * プレフィクスとテンプレートのURL文字列から{@link UrlTemplate}
	 * を取得します.プレフィクスとURL文字列はスラッシュで文字列連結されます.
	 * </p>
	 * </#if>
	 * 
	 * @param prefix
	 * @param templatePath
	 * @return url template
	 */
	public UrlTemplate getUrlTemplate(final String prefix,
			final String templatePath) {
		Assertion.notNull(prefix);
		Assertion.notNull(templatePath);
		String tempPath = PathUtil.removalJSessionId(templatePath);
		String pref = prefix;
		if (StringUtil.isEmpty(pref) == false
				&& prefix.lastIndexOf('/') == prefix.length() - 1) {
			pref = pref.substring(0, pref.length() - 1);
		}
		if (tempPath.indexOf('/') == 0) {
			tempPath = tempPath.substring(1, tempPath.length());
		}
		String path = pref + "/" + tempPath;
		return createTemplate(path);
	}

	protected UrlTemplate createTemplate(final String templatePath) {
		return new UrlTemplate(templatePath);
	}

}
