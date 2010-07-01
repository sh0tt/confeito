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

import java.util.logging.Level;

import org.t2framework.confeito.Constants;

/**
 * 
 * <#if locale="en">
 * <p>
 * Simple logger with {@link java.util.logging.Logger}.
 * </p>
 * <#else>
 * <p>
 * ロギングクラスです．{@link org.slf4j.Logger}を内部的に使っています．
 * </p>
 * </#if>
 * 
 * @author shot
 */
public class Logger {

	/**
	 * <#if locale="en">
	 * <p>
	 * the original {@link org.slf4j.Logger}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected java.util.logging.Logger logger_;

	protected Logger(final String name) {
		logger_ = java.util.logging.Logger.getLogger(name);
	}

	protected Logger(final Class<?> clazz) {
		logger_ = java.util.logging.Logger.getLogger(clazz.getName());
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Logger} by name.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Logger}を名前をキーに取得します．
	 * </p>
	 * </#if>
	 * 
	 * @param name
	 * @return
	 */
	public static Logger getLogger(final String name) {
		return new Logger(Assertion.notNull(name));
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get {@link Logger} by class.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link Logger}をClassをキーに取得します．
	 * </p>
	 * </#if>
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(final Class<?> clazz) {
		return new Logger(Assertion.notNull(clazz));
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Debug logging.Only works if debug mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @return
	 */
	public Logger debug(String message) {
		if (isDebugEnabled()) {
			logger_.config(message);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Debug logging with arguments.Only works if debug mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public Logger debug(String message, Object[] args) {
		if (isDebugEnabled()) {
			logger_.log(Level.CONFIG, message, args);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Info logging.Only works if info mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @return
	 */
	public Logger info(String message) {
		if (isInfoEnabled()) {
			logger_.info(message);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Info logging with arguments.Only works if info mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public Logger info(String message, Object[] args) {
		if (isInfoEnabled()) {
			logger_.log(Level.INFO, message, args);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Error logging.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @return
	 */
	public Logger error(String message) {
		if (isErrorEnabled()) {
			logger_.severe(message);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Error logging with arguments.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public Logger error(String message, Object[] args) {
		if (isErrorEnabled()) {
			logger_.log(Level.SEVERE, message, args);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Warning logging.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @return
	 */
	public Logger warn(String message) {
		if (isWarnEnabled()) {
			logger_.warning(message);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Warning logging with arguments.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public Logger warn(String message, Object[] args) {
		if (isWarnEnabled()) {
			logger_.log(Level.WARNING, message, args);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Trace logging.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @return
	 */
	public Logger trace(String message) {
		if (isTraceEnabled()) {
			logger_.fine(message);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Trace logging with arguments.Only works if error mode is enabled.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public Logger trace(String message, Object[] args) {
		if (isTraceEnabled()) {
			logger_.log(Level.FINE, message, args);
		}
		return this;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * If debug mode is enabled or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	public boolean isDebugEnabled() {
		return logger_.isLoggable(Level.CONFIG);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * If info mode is enabled or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	public boolean isInfoEnabled() {
		return logger_.isLoggable(Level.INFO);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * If error mode is enabled or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	public boolean isErrorEnabled() {
		return logger_.isLoggable(Level.SEVERE);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * If trace mode is enabled or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	public boolean isTraceEnabled() {
		return logger_.isLoggable(Level.FINE);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * If warning mode is enabled or not.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return
	 */
	public boolean isWarnEnabled() {
		return logger_.isLoggable(Level.WARNING);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Maybe need to some clear method to LoggerFactory, like Log4j.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	public static void dispose() {
	}

	public void log(String messageCode) {
		log(messageCode, Constants.EMPTY_ARRAY);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Logging message by message code and its arguments.This method is used
	 * when logging mode is set with the first character of message code.
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param messageCode
	 * @param args
	 */
	public void log(String messageCode, Object[] args) {
		log(messageCode, null, args);
	}

	public void log(String messageCode, Throwable e) {
		log(messageCode, e, Constants.EMPTY_ARRAY);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Logging message by message code, {@link Throwable}, and its
	 * arguments.This method is used when logging mode is set with the first
	 * character of message code. The meanings of the first character of message
	 * code are:
	 * 
	 * <pre>
	 * D : debug mode
	 * I : info mode
	 * E : error mode
	 * T : trace mode
	 * W : warn mode
	 * </pre>
	 * 
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @param messageCode
	 * @param e
	 * @param args
	 */
	public void log(String messageCode, Throwable e, Object[] args) {
		Assertion.notNull(messageCode);
		if (messageCode.length() < 1) {
			throw new IllegalStateException(
					"messageCode must not be empty string.");
		}
		final char messageType = messageCode.charAt(0);
		final String message = MessageFormatter.getSimpleMessage(messageCode,
				args);
		switch (messageType) {
		case 'D':
			logger_.log(Level.CONFIG, message, e);
			break;
		case 'I':
			logger_.log(Level.INFO, message, e);
			break;
		case 'E':
			logger_.log(Level.SEVERE, message, e);
			break;
		case 'T':
			logger_.log(Level.FINE, message, e);
			break;
		case 'W':
			logger_.log(Level.WARNING, message, e);
			break;
		default:
			break;
		}
	}

}
