package de.back2heaven.jbus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class InterruptableLinkedBlockingQueue<T> implements Queue<T>,
		Serializable {

	private static final long serialVersionUID = 7260269318397064235L;

	final private LinkedList<T> v = new LinkedList<>();

	public void interrupt() {
		synchronized (v) {
			v.notify();
		}
	}

	public T take() {
		synchronized (v) {
			if (isEmpty()) {
				try {
					v.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		if (isEmpty()) {
			return null;
		}
		return remove();
	}

	@Override
	public int size() {
		return v.size();
	}

	@Override
	public boolean isEmpty() {
		return v.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return v.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		// TODO interrupt hier aufrufen
		return new NoRemoveIterator<>(v.iterator());
	}

	@Override
	public Object[] toArray() {
		return v.toArray();
	}

	@Override
	public <V> V[] toArray(V[] a) {
		return v.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		boolean b = v.remove(o);
		interrupt();
		return b;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return v.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean b = v.addAll(c);
		interrupt();
		return b;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean b = v.removeAll(c);
		interrupt();
		return b;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean b = v.retainAll(c);
		interrupt();
		return b;
	}

	@Override
	public void clear() {
		v.clear();
		interrupt();
	}

	@Override
	public boolean add(T e) {
		boolean b = v.add(e);
		interrupt();
		return b;
	}

	@Override
	public boolean offer(T e) {
		boolean b = v.add(e);
		interrupt();
		return b;
	}

	@Override
	public T remove() {
		T z = v.remove();
		interrupt();
		return z;
	}

	@Override
	public T poll() {
		T z = v.poll();
		interrupt();
		return z;
	}

	@Override
	public T element() {
		return v.element();
	}

	@Override
	public T peek() {
		return v.peek();
	}
}
