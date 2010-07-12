package org.t2framework.confeito.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.util.JavaBeansUtil;

public class Component {

	protected Class<?> componentClass;

	protected Object instance;

	protected Map<String, Property> propertyMap = new HashMap<String, Property>();

	protected List<Method> methods = new ArrayList<Method>();

	public static Component createByInstance(Object o) {
		Component component = createByClass(o.getClass());
		component.setInstance(o);
		return component;
	}

	public static Component createByClass(Class<?> clazz) {
		Component component = new Component(clazz);
		return component;
	}

	protected Component(Class<?> componentClass) {
		this.componentClass = componentClass;
		analyzePropertiesAndMethods(componentClass);
	}

	protected void analyzePropertiesAndMethods(final Class<?> componentClass) {
		for (Method m : componentClass.getMethods()) {
			if (Ignore.isIgnorableMethod(m)) {
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
		if (propertyMap.containsKey(propertyName)) {
			return propertyMap.get(propertyName);
		} else {
			return new Property();
		}
	}

	public String getName() {
		return this.componentClass.getName();
	}

	public Collection<Property> getProperties() {
		return this.propertyMap.values();
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Class<?> getComponentClass() {
		return componentClass;
	}

	public List<Method> getMethods() {
		return methods;
	}

}
