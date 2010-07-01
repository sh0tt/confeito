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
 * ParameterAssembler is assembler class to gather objects from parent class,
 * ParameterContainer.
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
public interface ParameterAssembler extends ParameterContainer {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get parameters as map.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return parameter as map
	 */
	Map<String, String[]> getParametersAsMap();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get list of parameter names.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return list of parameter name
	 */
	List<String> getParameterNames();

}
