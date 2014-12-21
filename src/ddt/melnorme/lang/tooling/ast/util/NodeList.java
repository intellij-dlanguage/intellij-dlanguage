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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.utilbox.collections.ArrayView;

public abstract class NodeList<E extends ASTNode> extends ASTNode implements IScopeElement {
	
	public final ArrayView<E> nodes;
	
	protected NodeList(ArrayView<E> nodes) {
		this.nodes = parentize(assertNotNull(nodes));
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, nodes);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendList(nodes, "\n", true);
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(nodes, false);
	}
	
}