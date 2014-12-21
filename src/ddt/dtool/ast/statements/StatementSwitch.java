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
import ddt.dtool.ast.expressions.MissingParenthesesExpression;

public class StatementSwitch extends Statement {
	
	public final boolean isFinal;
	public final Expression exp;
	public final IStatement body;
	
	public StatementSwitch(boolean isFinal, Expression exp, IStatement body) {
		this.isFinal = isFinal;
		this.exp = parentize(exp);
		this.body = parentize(body);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_SWITCH;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, exp);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isFinal, "final ");
		cp.append("switch ");
		MissingParenthesesExpression.appendParenthesesExp(cp, exp);
		cp.append(body);
	}
	
}