package org.t2framework.confeito.model;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.t2framework.confeito.Constants;

public class Ignore {

	String methodName;

	Class<?>[] args;

	Class<?> returnType;

	protected static Set<Ignore> DEFAULT_IGNORES = new HashSet<Ignore>();

	public Ignore(String methodName, Class<?>[] args, Class<?> returnType) {
		this.methodName = methodName;
		this.args = args;
		this.returnType = returnType;
	}

	private static void init() {
		DEFAULT_IGNORES.add(new Ignore("wait", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));
		DEFAULT_IGNORES.add(new Ignore("wait", new Class<?>[] { Long.TYPE,
				Integer.TYPE }, Void.TYPE));
		DEFAULT_IGNORES.add(new Ignore("wait", new Class<?>[] { Long.TYPE },
				Void.TYPE));
		DEFAULT_IGNORES.add(new Ignore("hashCode", Constants.EMPTY_CLASS_ARRAY,
				Integer.TYPE));
		DEFAULT_IGNORES.add(new Ignore("getClass", Constants.EMPTY_CLASS_ARRAY,
				Class.class));
		DEFAULT_IGNORES.add(new Ignore("equals",
				new Class<?>[] { Object.class }, Boolean.TYPE));
		DEFAULT_IGNORES.add(new Ignore("toString", Constants.EMPTY_CLASS_ARRAY,
				String.class));
		DEFAULT_IGNORES.add(new Ignore("notify", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));
		DEFAULT_IGNORES.add(new Ignore("notifyAll",
				Constants.EMPTY_CLASS_ARRAY, Void.TYPE));

		DEFAULT_IGNORES.add(new Ignore("finalize", Constants.EMPTY_CLASS_ARRAY,
				Void.TYPE));
		DEFAULT_IGNORES.add(new Ignore("clone", Constants.EMPTY_CLASS_ARRAY,
				Object.class));
		DEFAULT_IGNORES.add(new Ignore("registerNatives",
				Constants.EMPTY_CLASS_ARRAY, Void.TYPE));

	}

	public static boolean isIgnorableMethod(final Method m) {
		if (DEFAULT_IGNORES.isEmpty()) {
			init();
		}
		if (m.isBridge() || m.isSynthetic()) {
			return true;
		}
		for (Ignore i : DEFAULT_IGNORES) {
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

	private static boolean parameterEquals(Class<?>[] args,
			Class<?>[] parameterTypes) {
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

}
