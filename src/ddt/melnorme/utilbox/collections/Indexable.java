/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
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
import java.util.RandomAccess;

/**
 * interface for a read-only view random access collection
 */
public interface Indexable<E> extends Iterable<E>, RandomAccess {
	
	@Override
	Iterator<E> iterator();
	
	/** @return the number of elements in this collection */
	int size();
	
	/** @return <tt>true</tt> if this collection contains no elements */
	boolean isEmpty();
	
	/** @return <tt>true</tt> if this collection contains given other. */
	boolean contains(E other);
	
	/** @return the element at given index. */
	E get(int index);
	
}