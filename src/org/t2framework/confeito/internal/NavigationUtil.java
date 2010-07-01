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
package org.t2framework.confeito.internal;

import org.t2framework.confeito.plugin.AbstractPlugin.PluginDefaultNavigation;
import org.t2framework.confeito.spi.Navigation;

/**
 * <#if locale="en">
 * <p>
 * Utility for {@link Navigation}.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class NavigationUtil {

	/**
	 * <#if locale="en">
	 * <p>
	 * True if {@link Navigation} should execute immediately if the navigation
	 * is {@link PluginDefaultNavigation}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param navigation
	 * @return true if this navigation should immediately be executed, otherwise
	 *         false
	 */
	public static boolean isNavigateImmediately(Navigation navigation) {
		return navigation != null
				&& navigation.equals(PluginDefaultNavigation.INSTANCE) == false;
	}

}
