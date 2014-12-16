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
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;

public abstract class CommonStatementList extends Statement {
	
	public final ArrayView<IStatement> statements;
	
	public CommonStatementList(ArrayView<IStatement> statements) {
		this.statements = parentize(assertNotNull(statements));
	}
	
	/** This represents a missing block */
	public CommonStatementList() {
		this.statements = null;
	}
	
	public final ArrayView<ASTNode> statements_asNodes() {
		return CoreUtil.<ArrayView<ASTNode>>blindCast(statements);
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, statements);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		if(statements == null) {
			cp.append(" ");
			return;
		}
		cp.append("{");
		cp.appendList("\n", statements_asNodes(), "\n", "\n");
		cp.append("}");
	}
	
}