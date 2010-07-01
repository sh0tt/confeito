package org.t2framework.confeito.util;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorEnumeration<T> implements Enumeration<T> {

	protected Iterator<T> iterator;

	public IteratorEnumeration(Iterator<T> iterator) {
		this.iterator = Assertion.notNull(iterator);
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	@Override
	public T nextElement() {
		return iterator.next();
	}

}
