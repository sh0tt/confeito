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
package org.t2framework.confeito.adapter;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.t2framework.confeito.exception.ContainerAdapterStartupException;
import org.t2framework.confeito.handler.ExceptionHandler;
import org.t2framework.confeito.handler.GlobalExceptionHandler;
import org.t2framework.confeito.internal.ConfigurationUtil;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * Simple {@link ContainerAdapter} implementation. This class is expected to use
 * when you absolutely do not need DI container.
 * 
 * With {@link SimpleContainerAdapter}, you can just use T2 without any other
 * container framework.
 * </p>
 * <#else>
 * <p>
 * シンプルな {@link ContainerAdapter} 実装です.このクラスは、DIコンテナを使わない場合に使用します.
 * {@link SimpleContainerAdapter}を使用することで、T2をDIコンテナなしで動作させることが出来ます.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class SimpleContainerAdapter extends
		AbstractContainerAdapter<Map<Class<?>, Object>> {

	private List<Class<?>> unacceptableClasses = new ArrayList<Class<?>>();
	{
		unacceptableClasses.add(Cloneable.class);
		unacceptableClasses.add(Comparable.class);
		unacceptableClasses.add(Serializable.class);
		unacceptableClasses.add(Externalizable.class);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * The container for managing objects.
	 * </p>
	 * <#else>
	 * <p>
	 * シンプルなコンテナオブジェクトです.
	 * </p>
	 * </#if>
	 */
	protected Map<Class<?>, Object> container;

	/**
	 * <#if locale="en">
	 * <p>
	 * Construct SimpleContainerAdapter.
	 * </p>
	 * <#else>
	 * <p>
	 * SimpleContainerAdapterを構築します.
	 * </p>
	 * </#if>
	 */
	public SimpleContainerAdapter() {
		setEagerLoad(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		this.container = Collections
				.synchronizedMap(new LinkedHashMap<Class<?>, Object>());
		for (Class<?> extensionClass : ConfigurationUtil
				.getExtensionClasses(config)) {
			register(extensionClass);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(String configPath) {
		init();
	}

	/**
	 * {@inheritDoc} <#if locale="en">
	 * <p>
	 * If thie instance is not initalized, throw
	 * {@link ContainerAdapterStartupException}.
	 * </p>
	 * <#else>
	 * <p>
	 * このインスタンスが初期化されていない場合、{@link ContainerAdapterStartupException}が発生します.
	 * </p>
	 * </#if>
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@Override
	public <T> Component<T> getBeanDesc(Class<? super T> componentClass) {
		Assertion.notNull(componentClass);
		assertInited();
		if (getContainer().containsKey(componentClass)) {
			return new Component<T>(componentClass);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getComponent(Class<? super T> componentClass) {
		Assertion.notNull(componentClass);
		assertInited();
		return (T) getContainer().get(componentClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getComponents(Class<? super T> componentClass) {
		assertInited();
		Map<Class<?>, Object> components = getContainer();
		if (components.containsKey(componentClass)) {
			List<T> ret = new ArrayList<T>();
			for (Iterator<Class<?>> itr = components.keySet().iterator(); itr
					.hasNext();) {
				final Class<?> c = itr.next();
				final T instance = (T) components.get(c);
				if (componentClass.isAssignableFrom(c)
						&& ret.contains(instance) == false) {
					ret.add(instance);
				}
			}
			return ret;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@Override
	public <T> boolean hasComponent(Class<T> componentClass) {
		assertInited();
		return getContainer().containsKey(componentClass);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Unsupported this method for {@link SimpleContainerAdapter}.
	 * </p>
	 * <#else>
	 * <p>
	 * このメソッドは{@link SimpleContainerAdapter}ではサポートされません.
	 * </p>
	 * </#if>
	 * 
	 * @throws UnsupportedOperationException
	 *             <#if locale="en">
	 *             <p>
	 *             throw always if this method is invoked
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このメソッドでは常に発生します
	 *             </p>
	 *             </#if>
	 */
	@Override
	public <T> T injectDependency(T t) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@Override
	public synchronized <T> void register(Class<? extends T> clazz) {
		Assertion.notNull(clazz);
		assertInited();
		final T t = ClassUtil.newInstance(clazz);
		register(t);
	}

	/**
	 * {@inheritDoc} <#if locale="en">
	 * <p>
	 * This method is thread-safe.
	 * </p>
	 * <#else>
	 * <p>
	 * このメソッドは同期化されています.
	 * </p>
	 * </#if>
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T> void register(T component) {
		Assertion.notNull(component);
		assertInited();
		Class<T> clazz = (Class<T>) component.getClass();
		final List<Class<?>> dependencyClassList = analyzeClassDependency(clazz);
		for (Class<?> c : dependencyClassList) {
			getContainer().put(c, component);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Class<?>, Object> getContainer() {
		return this.container;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * This method is not supported, and returns empty list.
	 * </p>
	 * <#else>
	 * <p>
	 * このメソッドはサポートされていません.常に空のリストを返します.
	 * </p>
	 * </#if>
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@Override
	public List<ExceptionHandler<Throwable, Exception>> createExceptionHandlers() {
		assertInited();
		List<ExceptionHandler<Throwable, Exception>> handlers = getComponents(ExceptionHandler.class);
		if (handlers != null && handlers.isEmpty() == false) {
			return handlers;
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 */
	@Override
	public GlobalExceptionHandler createGlobalExceptionHandler() {
		assertInited();
		return new GlobalExceptionHandler();
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Check if this instance is initialized correctly.
	 * </p>
	 * <#else>
	 * <p>
	 * このメソッドが初期化されているかどうかを検査します.
	 * </p>
	 * </#if>
	 * 
	 * @throws ContainerAdapterStartupException
	 *             <#if locale="en">
	 *             <p>
	 *             In case of thie instance is not initalized.
	 *             </p>
	 *             <#else>
	 *             <p>
	 *             このインスタンスが初期化されていない場合
	 *             </p>
	 *             </#if>
	 * 
	 */
	protected void assertInited() {
		if (getContainer() == null) {
			throw new ContainerAdapterStartupException(this.getClass());
		}
	}

	protected List<Class<?>> analyzeClassDependency(Class<?> targetClass) {
		List<Class<?>> dependencyList = new ArrayList<Class<?>>();
		for (Class<?> c = targetClass; c != null && c != Object.class; c = c
				.getSuperclass()) {
			analyzeByInterface(dependencyList, c);
		}
		return dependencyList;
	}

	protected void analyzeByInterface(List<Class<?>> dependencyList,
			final Class<?> c) {
		if (unacceptableClasses.contains(c)) {
			return;
		}
		dependencyList.add(c);
		final Class<?>[] interfaces = c.getInterfaces();
		for (Class<?> cl : interfaces) {
			analyzeByInterface(dependencyList, cl);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		container.clear();
	}
}
