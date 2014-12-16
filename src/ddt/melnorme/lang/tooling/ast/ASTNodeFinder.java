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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.util.NodeElementUtil;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;


/**
 * Finds the innermost element whose source range contains the offset.
 * An element is picked between element.startPos (inclusive) and element.endPos (inclusive according to inclusiveEnd).
 */
public class ASTNodeFinder extends ASTVisitor {
	
	public static ASTNode findElement(ASTNode root, int offset) {
		return findElement(root, offset, true);
	}
	
	public static ASTNode findElement(final ASTNode root, int offset, boolean inclusiveEnd) {
		if(root == null)
			return null;
		return new ASTNodeFinder(root, offset, inclusiveEnd).match;
	}
	
	public final ASTNode root;
	public final int offset; 
	public final boolean inclusiveEnd;
	public ASTNode match;
	public ASTNode matchOnLeft;
	
	public ASTNodeFinder(ASTNode root, int offset, boolean inclusiveEnd) {
		this(root, offset, inclusiveEnd, null);
		findNodeInAST();
	}
	
	/** Constructor that doesn't run visitor search */
	protected ASTNodeFinder(ASTNode root, int offset, boolean inclusiveEnd, @SuppressWarnings("unused") Object dummy) {
		assertNotNull(root);
		assertTrue(root.hasSourceRangeInfo());
		this.root = root;
		this.offset = offset;
		this.inclusiveEnd = inclusiveEnd;
		assertTrue(offset >= root.getStartPos() && offset <= root.getEndPos());
		
		this.match = null;
		this.matchOnLeft = null;
	}
	
	protected ASTNodeFinder findNodeInAST() {
		assertTrue(match == null && matchOnLeft == null);
		root.accept(this);
		return this;
	}
	
	@Override
	public boolean preVisit(ASTNode node) {
		if(!node.hasSourceRangeInfo()) {
			return false; // Shouldn't happen, but no need to assert
		}
		
		return findOnNode(node);
	}
	
	public boolean findOnNode(ASTNode node) {
		if(matchesNodeStart(node) && matchesNodeEnd(node)) {
			// This node is the match, or is parent of the match.
			ASTNode oldMatch = match;
			match = node;
			if(oldMatch != null && isAdjacent(oldMatch, match)) {
				assertTrue(offset == oldMatch.getEndPos());
				matchOnLeft = oldMatch;
			}
			return true; // Descend and search children.
		} else {
			// Match not here: don't bother descending, go forward
			return false; 
		}
	}
	
	public boolean isAdjacent(ASTNode node, ASTNode nextNode) {
		return node.getEndPos() == nextNode.getStartPos() &&
			!NodeElementUtil.isContainedIn(nextNode, node); // This check is necessary because of zero-length nodes
	}
	
	protected boolean matchesNodeStart(ASTNode node) {
		return offset >= node.getStartPos();
	}
	
	protected boolean matchesNodeEnd(ASTNode node) {
		return inclusiveEnd ? offset <= node.getEndPos() : offset < node.getEndPos();
	}
	
}