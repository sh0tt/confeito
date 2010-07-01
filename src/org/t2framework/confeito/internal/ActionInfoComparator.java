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

import java.util.Comparator;

import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * {@link Comparator} for {@link ActionInfo}.
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
public class ActionInfoComparator implements Comparator<ActionInfo> {

	/**
	 * <#if locale="en">
	 * <p>
	 * Compare and sort by these orders:
	 * <ul>
	 * <li>the comparing paths do not contain any variables(representing like
	 * {aaa}), simply check each path length and longer one comes first.</li>
	 * <li>If either template path contain variable, remove variable then check
	 * path length</li>
	 * <li>done all of that but the result is even, it's time to check
	 * annotation counts</li>
	 * </ul>
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public int compare(ActionInfo o1, ActionInfo o2) {
		Assertion.notNull(o1);
		Assertion.notNull(o2);
		if (o1.defaultAction) {
			return 1;
		} else if (o2.defaultAction) {
			return -1;
		}
		String templatePath1 = o1.templatePath;
		String templatePath2 = o2.templatePath;
		final boolean v1 = containsVariable(templatePath1);
		final boolean v2 = containsVariable(templatePath2);
		int len = templatePath2.length() - templatePath1.length();
		// If every path do not contain variables, skip these processes.
		if (v1 || v2) {
			// Ok, we have some variables here so we can remove it and then
			// compare.
			if (v1) {
				templatePath1 = templatePath1.replaceAll("\\{.*\\}", "");
			}
			if (v2) {
				templatePath2 = templatePath2.replaceAll("\\{.*\\}", "");
			}
			len = templatePath2.length() - templatePath1.length();
		}
		// After all, everything same, so it is the time to check how many
		// annotations exist.
		if (len == 0) {
			len = o2.annotationCounts - o1.annotationCounts;
		}
		return len;
	}

	protected boolean containsVariable(String templatePath) {
		int pos1 = templatePath.indexOf("{");
		int pos2 = templatePath.indexOf("}");
		return 0 < pos1 && 0 < pos2 && pos1 < pos2;
	}

}
