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

import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;

public class CatchClause extends ASTNode implements IScopeElement {
	
	public final SimpleVariableDef catchParam;
	public final IStatement body;
	
	public CatchClause(SimpleVariableDef catchParam, IStatement body) {
		this.catchParam = parentize(catchParam);
		this.body = parentize(body);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.TRY_CATCH_CLAUSE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, catchParam);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("catch ");
		cp.append("(", catchParam, ") ");
		cp.append(body);
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		if(catchParam != null) {
			search.evaluateScopeNodeList(Collections.singletonList(catchParam), false);
		}
	}
	
}