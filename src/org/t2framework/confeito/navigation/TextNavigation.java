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
package org.t2framework.confeito.navigation;

import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Base text type of navigation class.
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
public abstract class TextNavigation implements Navigation {

	protected final Object object;

	protected final String text;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this TextNavigation with object which must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param object
	 */
	public TextNavigation(Object object) {
		this.object = Assertion.notNull(object);
		this.text = null;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct this TextNavigation with string which must not be null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param text
	 */
	public TextNavigation(String text) {
		this.object = null;
		this.text = Assertion.notNull(text);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get object.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return object to be text
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Get text.Can be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return string to be text
	 */
	public String getText() {
		return text;
	}

}
