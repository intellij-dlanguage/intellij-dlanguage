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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class StatementIfVar extends Statement {
	
	public final VariableDefWithInit conditionVar;
	public final IStatement thenBody;
	public final IStatement elseBody;
	
	public StatementIfVar(VariableDefWithInit conditionVar, IStatement thenBody, IStatement elseBody) {
		this.conditionVar = parentize(assertNotNull(conditionVar));
		this.thenBody = parentize(thenBody);
		this.elseBody = parentize(elseBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_IF_VAR;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, conditionVar);
		acceptVisitor(visitor, thenBody);
		acceptVisitor(visitor, elseBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("if ");
		cp.append("(", conditionVar, ") ");
		cp.append(thenBody, " ");
		cp.append("else ", elseBody);
	}
	
}