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
import ddt.melnorme.utilbox.collections.ArrayView;

public class StatementTry extends Statement {
	
	public final IStatement body;
	public final ArrayView<CatchClause> catches;
	public final IStatement finallyBody;
	
	public StatementTry(IStatement body, ArrayView<CatchClause> catches, IStatement finallyBody) {
		this.body = parentize(body);
		this.catches = parentize(catches);
		this.finallyBody = parentize(finallyBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_TRY;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, body);
		acceptVisitor(visitor, catches);
		acceptVisitor(visitor, finallyBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("try ");
		cp.append(body);
		cp.appendList(catches, "\n");
		cp.append("finally ", finallyBody);
	}
	
}