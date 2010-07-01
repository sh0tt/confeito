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

/**
 * <#if locale="en">
 * <p>
 * AttributeContainer holds attributes and have set/get/remove methods.
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
public interface AttributeContainer {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set attribute.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <V>
	 * @param key
	 * @param value
	 */
	<V> void setAttribute(String key, V value);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get attribute.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <V>
	 * @param key
	 * @return attribute value
	 */
	<V> V getAttribute(String key);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Remove attribute.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <V>
	 * @param key
	 * @return old attribute value
	 */
	<V> V removeAttribute(String key);

}
