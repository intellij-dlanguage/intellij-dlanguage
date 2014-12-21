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

public class StatementScope extends Statement {
	
	public enum ScopeTypes {
		ON_EXIT,
		ON_SUCCESS,
		ON_FAILURE,
		;
		
		public static ScopeTypes fromIdentifier(String scopeId) {
			if("exit".equals(scopeId)) return ON_EXIT;
			if("success".equals(scopeId)) return ON_SUCCESS;
			if("failure".equals(scopeId)) return ON_FAILURE;
			return null;
		}
	}
	
	public final Symbol scopeTypeId;
	public final IStatement body;
	
	public StatementScope(Symbol scopeTypeId, IStatement body) {
		this.scopeTypeId = parentize(scopeTypeId);
		this.body = parentize(body);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATEMENT_SCOPE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, scopeTypeId);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("scope ");
		cp.append("(", scopeTypeId, ")");
		cp.append(body);
	}
	
}