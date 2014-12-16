/*******************************************************************************
 * Copyright (c) 2012, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.ASTVisitor;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;


/**
 * Checks the AST source range contracts.
 */
public class ASTSourceRangeChecker extends ASTVisitor {
	
	protected int offsetCursor;
	protected int depth = 0;
	
	public static void checkConsistency(ASTNode elem){
		new ASTSourceRangeChecker(elem);
	}
	
	public ASTSourceRangeChecker(ASTNode elem) {
		this(elem.getStartPos());
		elem.accept(this);
	}
	
	public ASTSourceRangeChecker(int offsetCursor) {
		this.offsetCursor = offsetCursor;
	}
	
	@Override
	public boolean preVisit(ASTNode node) {
		depth++;
		
		assertTrue(node.hasSourceRangeInfo());
		if(node.getOffset() < offsetCursor) {
			handleSourceRangeStartPosBreach(node);
			return false;
		}
		offsetCursor = node.getOffset();
		return visitChildrenAfterPreVisitOk(); // Go to children
	}
	
	public boolean visitChildrenAfterPreVisitOk() {
		return true;
	}
	
	@Override
	public void postVisit(ASTNode node) {
		depth--;
		
		assertTrue(node.hasSourceRangeInfo());
		if(node.getEndPos() < offsetCursor) {
			handleSourceRangeEndPosBreach(node);
			return;
		} else {
			offsetCursor = node.getEndPos();
			return;
		}
	}
	
	@SuppressWarnings("unused") 
	protected void handleSourceRangeEndPosBreach(ASTNode elem) {
		assertFail();
	}
	
	@SuppressWarnings("unused") 
	protected void handleSourceRangeStartPosBreach(ASTNode elem) {
		throw assertFail();
	}
	
}