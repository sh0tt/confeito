package org.t2framework.confeito.model;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.AutoboxingUtil;
import org.t2framework.confeito.util.ConverterUtil;

public class Property {
	public String propertyName;

	public Method writeMethod;

	public Method readMethod;

	public Class<?> propertyType;

	public Object[] convertArgs(final Object value) {
		Object[] convertedArgs = null;
		if (value == null || Assertion.isAllNull(value)) {
			convertedArgs = convertNullToPrimitiveValueArgs(writeMethod);
		} else {
			final Class<?> clazz = value.getClass();
			if (clazz.isArray()) {
				Class<?> type = clazz.getComponentType();
				convertedArgs = new Object[] { convertArray(value, type
						.isPrimitive()) };
			} else {
				convertedArgs = new Object[] { convert0(value) };
			}
		}
		return convertedArgs;
	}

	protected Object convertArray(Object args, boolean primitive) {
		final boolean isNotPrimitive = primitive == false;
		final Object[] array = isNotPrimitive ? ((Object[]) args) : null;
		final int length = isNotPrimitive ? array.length : Array
				.getLength(args);
		Object ret = Array.newInstance(propertyType.getComponentType(), length);
		for (int i = 0; i < length; i++) {
			Object o = isNotPrimitive ? array[i] : Array.get(args, i);
			if (o == null) {
				continue;
			}
			Class<?> clazz = o.getClass();
			if (clazz.isArray()) {
				Class<?> type = clazz.getComponentType();
				Array.set(ret, i, convertArray(o, type.isPrimitive()));
			} else {
				Array.set(ret, i, convert0(o));
			}
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object convert0(Object o) {
		Class<?> c;
		if (propertyType.isArray() == false) {
			c = this.propertyType;
		} else {
			c = this.propertyType.getComponentType();
		}
		if (c.isPrimitive()) {
			return ConverterUtil.convert(o, c);
		} else if (Number.class.isAssignableFrom(c)) {
			return ConverterUtil.convert(o, c);
		} else if (Date.class.isAssignableFrom(c)) {
			return ConverterUtil.convertAsDate(o);
		} else if (Boolean.class == c) {
			return ConverterUtil.convertAsBoolean(o);
		} else if (Calendar.class.isAssignableFrom(c)) {
			return ConverterUtil.convertAsCalendar(o);
		} else if (String.class.isAssignableFrom(c)) {
			return ConverterUtil.convertAsString(o);
		} else if (Enum.class.isAssignableFrom(c)) {
			Class<? extends Enum> clazz = (Class<Enum>) c;
			return ConverterUtil.convertAsEnum(o, clazz);
		}
		return o;
	}

	protected Object[] convertNullToPrimitiveValueArgs(final Method wmd) {
		final Class<?>[] paramTypes = wmd.getParameterTypes();
		final int len = paramTypes.length;
		Object[] ret = new Object[len];
		for (int i = 0; i < len; i++) {
			Class<?> c = paramTypes[i];
			if (c.isPrimitive()) {
				ret[i] = AutoboxingUtil.getDefaultPrimitiveValue(c);
			} else {
				ret[i] = null;
			}
		}
		return ret;
	}
}
