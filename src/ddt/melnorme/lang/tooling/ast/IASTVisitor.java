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


/**
 * Visitor class for {@link ASTNode}
 */
public interface IASTVisitor {
	
	/** Visit a node and return a boolean to indicate if children should be visited or not. */
	public boolean preVisit(ASTNode node);
	
	/** Visit a node after children have (potentially) been visited. */
	public void postVisit(ASTNode node);
	
}