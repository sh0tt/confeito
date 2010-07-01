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
 * ParameterContainer is a container class to keep parameters.
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
public interface ParameterContainer {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get parameter arrays.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return parameter values
	 */
	String[] getParameters(String key);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get parameter.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return parameter value
	 */
	String getParameter(String key);

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * True if there is multiple value for the key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param key
	 * @return true if there is multiple values for this key, and false if
	 *         single parameter value
	 */
	boolean hasMultipleParameters(String key);
}
