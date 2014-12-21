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
package ddt.melnorme.lang.tooling.ast.util;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.utilbox.collections.ArrayView;

/**
 * Utility class for lists of nodes.
 * Has additional info saying if parsing encountered an endingseparator or not;
 */
public class NodeListView<T extends IASTNode> extends ArrayView<T> {
	
	public final boolean hasEndingSeparator;
	
	public NodeListView(T[] array, boolean hasEndingSeparator) {
		super(array);
		this.hasEndingSeparator = hasEndingSeparator;
	}
	
}