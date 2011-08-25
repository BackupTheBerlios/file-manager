package de.back2heaven.jbus;

import java.util.Iterator;

public class NoRemoveIterator<T> implements Iterator<T> {
	private Iterator<T> cascade;

	public NoRemoveIterator(Iterator<T> cascade) {
		this.cascade = cascade;
	}

	@Override
	public boolean hasNext() {
		return cascade.hasNext();
	}

	@Override
	public T next() {
		return cascade.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
