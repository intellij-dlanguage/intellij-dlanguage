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
import ddt.dtool.ast.definitions.Symbol;

public class StatementBreak extends Statement {
	
	public Symbol id;
	
	public StatementBreak(Symbol id) {
		this.id = parentize(id);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_BREAK;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, id);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("break ");
		cp.append(id);
		cp.append(";");
	}
	
}