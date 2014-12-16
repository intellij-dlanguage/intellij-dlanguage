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
package ddt.melnorme.lang.tooling.engine.scoping;

import ddt.melnorme.lang.tooling.ast.IASTNode;

/**
 * Interface for a node that potentially contains named elements visible 
 * in the same scope/namespace as the container. 
 */
public interface INonScopedContainer {
	
	/** @return an iterator for the members of this {@link INonScopedContainer}. Non-null. 
	 * Used mainly for resolving. */
	Iterable<? extends IASTNode> getMembersIterable();
	
}
