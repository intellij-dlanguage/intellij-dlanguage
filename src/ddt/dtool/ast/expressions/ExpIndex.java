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
package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

/**
 * Index expression.
 * Note: arguments can be empty (as in `foo[]`).
 */
public class ExpIndex extends Expression {
	
	public final Expression indexee;
	public final NodeListView<Expression> args;
	
	public ExpIndex(Expression indexee, NodeListView<Expression> args) {
		this.indexee = parentize(indexee);
		this.args = parentize(args);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_INDEX;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, indexee);
		acceptVisitor(visitor, args);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(indexee);
		cp.appendNodeList("[", args, ", " , "]");
	}
	
}