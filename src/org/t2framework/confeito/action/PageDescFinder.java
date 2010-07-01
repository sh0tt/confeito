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
package org.t2framework.confeito.action;

import java.util.List;
import java.util.Map;

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.model.PageComponent;

/**
 * <#if locale="en">
 * <p>
 * PageDescFinder is a finder interface for PageDesc. The concrete class of this
 * interface should be able to check and provide an appropriate PageDesc to its
 * user.
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
public interface PageDescFinder {
	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * PageDescFinderの初期化を行います。このメソッドはインスタンス化後に１度だけ呼ばれます.
	 * 引数のpageDescMapはunmodifiableMapなので、Mapを変更したい場合は、Mapをコピーして使用してください.
	 * </p>
	 * </#if>
	 * 
	 * @param pageDescMap
	 */
	void initialize(Map<String, PageComponent> pageDescMap);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Return applicable candidates of {@link PageComponent} for this
	 * request.This method must not return null, instead of that return empty
	 * list.
	 * </p>
	 * <#else>
	 * <p>
	 * リクエストを処理できる可能性のある{@link PageComponent}のリストを返します.
	 * </p>
	 * </#if>
	 * 
	 * @param context
	 * @return unmodifiable list of {@link PageComponent}.
	 */
	List<PageComponent> find(final WebContext context);
}
