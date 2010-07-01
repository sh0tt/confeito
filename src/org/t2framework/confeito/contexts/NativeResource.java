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
package org.t2framework.confeito.contexts;

import javax.servlet.http.HttpServletRequest;

/**
 * <#if locale="en">
 * <p>
 * NativeResource is wrapper mechanism of some Servlet specification resources
 * like {@link HttpServletRequest} using parameterized T.
 * 
 * </p>
 * <#else>
 * <p>
 * NativeResourceはServlet仕様のオブジェクトをそのラッパークラスから取得するためのインタフェースです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 * @param <T>
 */
public interface NativeResource<T> {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get native resource.
	 * </p>
	 * <#else>
	 * <p>
	 * Tで指定されたリソースを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return native resource as parameterized T
	 */
	T getNativeResource();
}
