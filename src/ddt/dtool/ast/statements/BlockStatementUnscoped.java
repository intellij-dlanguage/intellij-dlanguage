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
package ddt.dtool.ast.statements;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.IteratorUtil;

public class BlockStatementUnscoped extends CommonStatementList implements INonScopedContainer {
	
	public BlockStatementUnscoped(ArrayView<IStatement> nodes) {
		super(nodes);
	}
	
	public BlockStatementUnscoped() {
		super();
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.BLOCK_STATEMENT_UNSCOPED;
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return IteratorUtil.nonNullIterable(statements_asNodes());
	}
	
}