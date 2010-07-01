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
package org.t2framework.confeito.util;

/**
 * <#if locale="en">
 * <p>
 * An assertion utility just for simple checks.
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
public class Assertion {

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert whether the target is not null.If null, throw
	 * {@link NullPointerException}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param target
	 * @return
	 */
	public static <T> T notNull(T target) {
		return notNull(target, null);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert whether the target is not null with the message. If null, throw
	 * {@link NullPointerException}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param target
	 * @param message
	 * @return
	 */
	public static <T> T notNull(T target, String message) {
		if (target == null) {
			throw new NullPointerException(message);
		}
		return target;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the arguments are not null. If null, throw
	 * {@link NullPointerException}.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> T[] notNulls(T... args) {
		notNull(args);
		for (int i = 0; i < args.length; i++) {
			notNull(args[i]);
		}
		return args;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the target is not null.This method returns true/false instead
	 * of exception.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> boolean isNotNull(T t) {
		return (t != null);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the target argument is not null.This method returns true/false
	 * instead of exception.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> boolean hasNotNull(T... args) {
		return !hasNull(args);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the target is null.This method returns true/false instead of
	 * exception.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> boolean isNull(T t) {
		return (t == null);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the argument contains null.This method returns true/false
	 * instead of exception.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> boolean hasNull(T... args) {
		notNull(args);
		for (T t : args) {
			if (t == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Assert if the target argument is all null.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> boolean isAllNull(T... args) {
		notNull(args);
		for (T t : args) {
			if (t != null) {
				return false;
			}
		}
		return true;
	}

	public static void positive(int i) {
		if (i < 0) {
			throw new IllegalArgumentException();
		}
	}
}
