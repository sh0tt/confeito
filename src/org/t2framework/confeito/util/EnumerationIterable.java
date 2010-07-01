package org.t2framework.confeito.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterable<T> implements Iterable<T> {

	protected Enumeration<T> e;

	public EnumerationIterable(Enumeration<T> e) {
		this.e = Assertion.notNull(e);
	}

	@Override
	public Iterator<T> iterator() {
		return new EnumerationIterator<T>(e);
	}

	public static class EnumerationIterator<E> implements Iterator<E> {

		private Enumeration<E> enumeration = null;

		public EnumerationIterator(final Enumeration<E> e) {
			this.enumeration = Assertion.notNull(e);
		}

		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		public boolean hasNext() {
			return enumeration.hasMoreElements();
		}

		@Override
		public E next() {
			return enumeration.nextElement();
		}

	}

}
