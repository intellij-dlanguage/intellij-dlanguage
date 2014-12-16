/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.utilbox.collections;

import java.util.Iterator;


public class ChainedIterable<E> implements Iterable<E> {
	
	public static <E> ChainedIterable<E> create(Iterable<E> firstIter, Iterable<E> secondIter) {
		return new ChainedIterable<E>(firstIter, secondIter);
	}
	
	protected final Iterable<E> first;
	protected final Iterable<E> second;
	
	public ChainedIterable(Iterable<E> first, Iterable<E> second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new SimpleChainedIterator<>(first.iterator(), second.iterator());
	}
	
	/** Cast the type parameter to something. This operation is only safe if the underlying collection 
	 * is used only for reading (for a type parameter upcast) or writing (for a type parameter upcast).*/
	@SuppressWarnings("unchecked")
	public <T> ChainedIterable<T> castTypeParam() {
		return (ChainedIterable<T>) this;
	}
	
}