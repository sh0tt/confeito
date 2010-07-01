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

import java.util.List;
import java.util.Map;

/**
 * <#if locale="en">
 * <p>
 * AttributesAssembler has assembling methods for attributes, like
 * getAttributeNames().
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
public interface AttributesAssembler extends AttributeContainer {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get list of attribute name.This method must not return null, return empty
	 * list instead.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return list of attribute names
	 */
	List<String> getAttributeNames();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get map of all attributes.This method must not return null, return empty
	 * map instead.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return map of attributes
	 */
	Map<String, Object> getAttributesAsMap();
}
