/*******************************************************************************
 * Copyright (c) 2010, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.definitions.DefinitionEnum.EnumBody;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;

public class DeclarationEnum extends ASTNode implements INonScopedContainer, IDeclaration, IStatement {
	
	public final Reference type;
	public final EnumBody body;
	
	public DeclarationEnum(Reference type, EnumBody body) {
		this.type = parentize(type);
		this.body = parentize(body);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_ENUM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("enum ");
		cp.append(": ", type);
		cp.append(body);
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		if(body == null)
			return IteratorUtil.emptyIterable();
		return IteratorUtil.nonNullIterable(body.nodeList);
	}
	
}