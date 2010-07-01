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
package org.t2framework.confeito.action.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.t2framework.confeito.action.PageDescFinder;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.internal.PathUtil;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.urltemplate.UrlTemplate;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link PageDescFinder}.{@link PageComponent} will find
 * by sorted list of {@link PageComponent} which is done at
 * {@link PageDescFinderImpl#initialize(Map)}.More specifically, the list is
 * sorted by following rule:
 * <ul>
 * <li>Sort to longer url comes before shorter one</li>
 * <li>variables(things like {aaa}) is completely removed at evaluation time</li>
 * </ul>
 * </p>
 * <#else>
 * <p>
 * {@link PageDescFinder}の実装クラス.{@link PageComponent}は、{@link PageComponent}
 * のリストから検索します. このリストは、{@link PageDescFinderImpl#initialize(Map)}時にソートされます.
 * リストのソートは、次のルールで行われます:
 * <ul>
 * <li>urlが長いものから順に並びます</li>
 * <li>url中の変数（{aaa}のような部分)については、その長さはurlの長さには含めません</li>
 * </ul>
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class PageDescFinderImpl implements PageDescFinder {

	/**
	 * <#if locale="en">
	 * <p>
	 * Sorted list of {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * ソート済みの{@link PageComponent}のリスト.
	 * </p>
	 * </#if>
	 */
	protected List<PageComponent> pageDescList;

	/**
	 * <#if locale="en">
	 * <p>
	 * Create sorted list of {@link PageComponent} from given {@link Map}.
	 * </p>
	 * <#else>
	 * <p>
	 * 引数の{@link Map}から、ソート済みの{@link PageComponent}のリストを生成します.
	 * </p>
	 * </#if>
	 */
	@Override
	public void initialize(Map<String, PageComponent> pageDescMap) {
		List<PageComponent> list = new ArrayList<PageComponent>();
		list.addAll(pageDescMap.values());
		Collections.sort(list, new Comparator<PageComponent>() {
			@Override
			public int compare(PageComponent o1, PageComponent o2) {
				final String pageTemplatePath2 = o2.getPageTemplatePath()
						.replaceAll("\\{.*\\}", "");
				final String pageTemplatePath1 = o1.getPageTemplatePath()
						.replaceAll("\\{.*\\}", "");
				return pageTemplatePath2.length() - pageTemplatePath1.length();
			}
		});
		this.pageDescList = list;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Return list of {@link PageComponent} if requested path, which is
	 * calculated by {@link UrlTemplate}, is matched.
	 * </p>
	 * <#else>
	 * <p>
	 * pageDescList内の{@link PageComponent}のうち、リクエストされたパスと{@link UrlTemplate}
	 * がマッチするものを返します.
	 * </p>
	 * </#if>
	 * 
	 * @see org.t2framework.confeito.internal.PathUtil
	 */
	@Override
	public List<PageComponent> find(final WebContext context) {
		final List<PageComponent> ret = new ArrayList<PageComponent>();
		final HttpServletRequest req = context.getRequest().getNativeResource();
		final String path = PathUtil.getPagePath(req);
		for (PageComponent pd : this.pageDescList) {
			if (pd.getUrlTemplate().match(path)) {
				ret.add(pd);
			}
		}
		return ret;
	}
}
