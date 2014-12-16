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
import ddt.dtool.ast.definitions.Symbol;

public class StatementGoto extends Statement {
	
	public final Symbol label;
	
	public StatementGoto(Symbol label) {
		this.label = parentize(assertNotNull(label));
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_GOTO;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, label);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("goto ");
		cp.append(label);
		cp.append(";");
	}
	
}