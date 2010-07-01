package org.t2framework.confeito.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.util.JavaBeansUtil;

public class Component<T> {

	protected static Set<Ignore> ignores = new HashSet<Ignore>();

	static {
		init();
	}

	protected Class<? super T> componentClass;

	protected T instance;

	protected Map<String, Property> propertyMap = new HashMap<String, Property>();

	protected List<Method> methods = new ArrayList<Method>();

	public Component(Class<? super T> componentClass) {
		this.componentClass = componentClass;
		analyzePropertiesAndMethods(componentClass);
	}

	@SuppressWarnings("unchecked")
	public Component(T o) {
		this((Class<? super T>) o.getClass());
		this.instance = o;
	}

	protected void analyzePropertiesAndMethods(
			final Class<? super T> componentClass) {
		for (Method m : componentClass.getMethods()) {
			if (isIgnorableMethod(m)) {
				continue;
			}
			final String methodName = m.getName();
			final int len = methodName.length();
			if (JavaBeansUtil.isGetMethod(methodName, m)
					&& Constants.GET_LENGTH < len) {
				String propertyName = JavaBeansUtil.decapitalize(methodName
						.substring(Constants.GET_LENGTH));
				setReadMethod(m, propertyName);
			} else if (JavaBeansUtil.isIsMethod(methodName, m)
					&& Constants.SET_LENGTH < len) {
				String propertyName = JavaBeansUtil.decapitalize(methodName
						.substring(Constants.IS_LENGTH));
				setReadMethod(m, propertyName);
			} else if (JavaBeansUtil.isSetMethod(methodName, m)
					&& Constants.IS_LENGTH < len) {
				String propertyName = JavaBeansUtil.decapitalize(methodName
						.substring(Constants.SET_LENGTH));
				setWriteMethod(m, propertyName);
			} else {
				setupMethod(m);
			}
		}
	}

	protected void setupMethod(Method method) {
		methods.add(method);
	}

	protected void setReadMethod(Method readMethod, String propertyName) {
		Property property = getProperty(propertyName);
		property.readMethod = readMethod;
		property.propertyType = readMethod.getReturnType();
		propertyMap.put(propertyName, property);
	}

	protected void setWriteMethod(Method writeMethod, String propertyName) {
		Property property = getProperty(propertyName);
		property.writeMethod = writeMethod;
		property.propertyType = writeMethod.getParameterTypes()[0];
		propertyMap.put(propertyName, property);
	}

	private Property getProperty(String propertyName) {
		if (propertyMap.containsKey(propertyName) == false) {
			return propertyMap.get(propertyName);
		} else {
			return new Property();
		}
	}

	public boolean isIgnorableMethod(final Method m) {
		if (m.isBridge() || m.isSynthetic()) {
			return true;
		}
		for (Ignore i : ignores) {
			final String name = m.getName();
			if (name.equals(i.methodName) == false) {
				continue;
			}
			final Class<?>[] parameterTypes = m.getParameterTypes();
			final Class<?> returnType = m.getReturnType();
			if (i.returnType.equals(returnType)
					&& parameterEquals(i.args, parameterTypes)) {
				return true;
			}
		}
		return false;
	}

	private boolean parameterEquals(Class<?>[] args, Class<?>[] parameterTypes) {
		if (args.length != parameterTypes.length) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != parameterTypes[i]) {
				return false;
			}
		}
		return true;
	}

	private static void init() {
		ignores.add(new Ignore("wait", Constants.EMPTY_CLASS_ARRAY, Void.TYPE));
		ignores.add(new Ignore("wait",
				new Class<?>[] { Long.TYPE, Integer.TYPE }, Void.TYPE));
		ignores
				.add(new Ignore("wait", new Class<?>[] { Long.TYPE }, Void.TYPE));
		ignores.add(new Ignore("hashCode", Constants.EMPTY_CLASS_ARRAY,
				Integer.TYPE));
		ignores.add(new Ignore("getClass", Constants.EMPTY_CLASS_ARRAY,
				Class.class));
		ignores.add(new Ignore("equals", new Class<?>[] { Object.class },
				Boolean.TYPE));
		ignores.add(new Ignore("toString", Constants.EMPTY_CLASS_ARRAY,
				String.class));
		ignores
				.add(new Ignore("notify", Constants.EMPTY_CLASS_ARRAY,
						Void.TYPE));
		ignores.add(new Ignore("notifyAll", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));

		ignores.add(new Ignore("finalize", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));
		ignores.add(new Ignore("clone", Constants.EMPTY_CLASS_ARRAY,
				Object.class));
		ignores.add(new Ignore("registerNatives", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));
	}

	public String getName() {
		return this.componentClass.getName();
	}

	public Collection<Property> getProperties() {
		return this.propertyMap.values();
	}

	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	public Class<? super T> getComponentClass() {
		return componentClass;
	}

	public List<Method> getMethods() {
		return methods;
	}

}
