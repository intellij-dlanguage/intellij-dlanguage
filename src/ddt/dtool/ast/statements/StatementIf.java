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

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.expressions.Expression;

public class StatementIf extends Statement {
	
	public final Expression condition;
	public final IStatement thenBody;
	public final IStatement elseBody;
	
	public StatementIf(Expression condition, IStatement thenBody, IStatement elseBody) {
		this.condition = parentize(condition);
		this.thenBody = parentize(thenBody);
		this.elseBody = parentize(elseBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_IF;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, condition);
		acceptVisitor(visitor, thenBody);
		acceptVisitor(visitor, elseBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("if ");
		cp.append("(", condition, ") ");
		cp.append(thenBody, " ");
		cp.append("else ", elseBody);
	}
	
}