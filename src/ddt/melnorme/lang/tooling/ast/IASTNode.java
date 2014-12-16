/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast;

import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.utilbox.tree.IElement;
import ddt.melnorme.utilbox.tree.IVisitable;

/**
 * Interface for {@link ASTNode} objects. No other class can implement. 
 */
public interface IASTNode extends ISourceElement, IElement, IVisitable<IASTVisitor>, ILanguageElement {
	
	public ASTNode asNode();
	
	/** Returns the parent of this node, or <code>null</code> if none. */
	@Override
	public ASTNode getParent();
	
	public void setParent(ASTNode newParent);
	
	/**
	 * Returns whether this element has one or more immediate children. This is
	 * a convenience method, and may be more efficient than testing whether
	 * <code>getChildren</code> is an empty array.
	 */
	@Override
	boolean hasChildren();
	
	/** Returns the node's children. */
	@Override
	public IASTNode[] getChildren(); // Redefined to refine the type of children
	
}