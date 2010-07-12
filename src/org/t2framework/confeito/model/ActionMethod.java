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
package org.t2framework.confeito.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.t2framework.confeito.internal.ActionInfo;
import org.t2framework.confeito.internal.ActionInfoComparator;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Logger;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link ActionMethod}.
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
public class ActionMethod implements Iterable<Method> {

	protected static Logger logger = Logger.getLogger(ActionMethod.class);

	protected final Set<Class<? extends Annotation>> actionAnnotationSet;

	protected List<ActionInfo> actionInfoList = new ArrayList<ActionInfo>();

	protected List<String> methodNameList = new ArrayList<String>();

	protected List<ActionInfo> unmodifiedList;

	protected ActionInfoComparator comparator;

	public ActionMethod(Set<Class<? extends Annotation>> actionAnnotationSet) {
		this(actionAnnotationSet, new ActionInfoComparator());
	}

	public ActionMethod(Set<Class<? extends Annotation>> actionAnnotationSet,
			ActionInfoComparator comparator) {
		this.actionAnnotationSet = actionAnnotationSet;
		this.comparator = comparator;
	}

	public void addTargetMethod(Method method) {
		Assertion.notNull(method);
		final String methodName = method.getName();
		addTargetMethod(methodName, method);
	}

	public void addTargetMethod(String alias, Method method) {
		Assertion.notNull(alias);
		Assertion.notNull(method);
		if (actionInfoList.contains(method) == false) {
			ActionInfo info = new ActionInfo(alias, method, actionAnnotationSet);
			actionInfoList.add(info);
			addMethodName(alias);
		}
	}

	protected void addMethodName(String alias) {
		if (methodNameList.contains(alias) == false) {
			methodNameList.add(alias);
		} else {
			logger.log("DTDT0051", new Object[] { alias });
		}
	}

	public Method getMethod(String methodName) {
		Assertion.notNull(methodName);
		for (ActionInfo info : this.actionInfoList) {
			if (info.methodName.endsWith(methodName)) {
				return info.methodDesc;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return actionInfoList.isEmpty();
	}

	public int getMethodSize() {
		return actionInfoList.size();
	}

	public Set<String> getMethodNames() {
		return new HashSet<String>(methodNameList);
	}

	public void addDefaultMethod(Method defaultMethodDesc) {
		Assertion.notNull(defaultMethodDesc);
		if (actionInfoList.contains(defaultMethodDesc) == false) {
			actionInfoList.add(new ActionInfo(defaultMethodDesc,
					actionAnnotationSet, true));
			String defaultMethodName = defaultMethodDesc.getName();
			methodNameList.add(defaultMethodName);
		}
	}

	@Override
	public Iterator<Method> iterator() {
		return new Iterator<Method>() {

			protected int i = 0;

			@Override
			public boolean hasNext() {
				if (unmodifiedList.isEmpty()) {
					return false;
				}
				return i < unmodifiedList.size();
			}

			@Override
			public Method next() {
				return unmodifiedList.get(i++).methodDesc;
			}

			@Override
			public void remove() {
			}

		};
	}

	public boolean freeze() {
		this.unmodifiedList = setup(this.actionInfoList);
		return this.unmodifiedList != null;
	}

	protected List<ActionInfo> setup(List<ActionInfo> list) {
		Collections.sort(list, this.comparator);
		return Collections.unmodifiableList(list);
	}

	public Method getMethod(int index) {
		ActionInfo actionInfo = actionInfoList.get(index);
		if (actionInfo != null) {
			return actionInfo.methodDesc;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("@" + this.hashCode());
		builder.append("[");
		for (String methodName : methodNameList) {
			builder.append(methodName);
			builder.append(", ");
		}
		if (methodNameList.isEmpty() == false) {
			builder.setLength(builder.length() - 2);
		}
		builder.append("]");
		return new String(builder);
	}

}
