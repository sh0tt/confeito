package org.t2framework.confeito.model;

public class Ignore {

	String methodName;

	Class<?>[] args;

	Class<?> returnType;

	public Ignore(String methodName, Class<?>[] args, Class<?> returnType) {
		this.methodName = methodName;
		this.args = args;
		this.returnType = returnType;
	}

}
