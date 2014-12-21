package ddt.dtool.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Sentinal class for ArrayList.
 * Allows creating special object instances that cannot be used directly, 
 * except for their identity, which can be used to mark special values.
 */
@SuppressWarnings("serial")
public class SentinelArrayList<E> extends ArrayList<E> {
	
	public SentinelArrayList() {
		super(0);
	}
	
	@Override
	public final int size() {
		throw assertFail();
	}
	
	@Override
	public final boolean isEmpty() {
		throw assertFail();
	}
	
	@Override
	public final E get(int index) {
		throw assertFail();
	}
	
	@Override
	public final boolean contains(Object o) {
		throw assertFail();
	}
	
	@Override
	public final boolean containsAll(Collection<?> c) {
		throw assertFail();
	}
	
	@Override
	public final Iterator<E> iterator() {
		throw assertFail();
	}
	
	@Override
	public final boolean add(E e) {
		throw assertFail();
	}
	
	@Override
	public final boolean remove(Object o) {
		throw assertFail();
	}
	
	@Override
	public final boolean addAll(Collection<? extends E> c) {
		throw assertFail();
	}
	
	@Override
	public final boolean removeAll(Collection<?> c) {
		throw assertFail();
	}
	
	@Override
	public final  boolean retainAll(Collection<?> c) {
		throw assertFail();
	}
	
	@Override
	public final void clear() {
		assertFail();
	}
	
	@Override
	public final Object[] toArray() {
		throw assertFail();
	}
	
	@Override
	public final <T> T[] toArray(T[] a) {
		throw assertFail();
	}
	
}