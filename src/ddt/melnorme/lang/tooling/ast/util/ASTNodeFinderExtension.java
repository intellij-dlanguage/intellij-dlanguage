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
package ddt.melnorme.lang.tooling.ast.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.ASTNodeFinder;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;

/** This extension also determines the last node boundary. */
public class ASTNodeFinderExtension extends ASTNodeFinder {
	
	public int lastNodeBoundary = -1;
	
	public ASTNodeFinderExtension(ASTNode root, int offset, boolean inclusiveEnd) {
		super(root, offset, inclusiveEnd, null);
		findNodeInAST();
		assertTrue(lastNodeBoundary >= 0);
	}
	
	@Override
	public boolean preVisit(ASTNode node) {
		if(node.hasSourceRangeInfo() && node.getStartPos() <= offset ) {
			lastNodeBoundary = node.getStartPos();
		}
		return super.preVisit(node);
	}
	
	@Override
	public void postVisit(ASTNode node) {
		if(node.hasSourceRangeInfo() && node.getEndPos() <= offset ) {
			lastNodeBoundary = node.getEndPos();
		}
		super.postVisit(node);
	}
}