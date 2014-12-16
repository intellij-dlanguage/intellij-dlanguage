/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.references.Reference;

public class AttribCppLinkage extends AttribLinkage {
	
	public final Reference qualifiedId; // Should an identifier list only, but we parse a full ref for simplication
	
	public AttribCppLinkage(String linkageName, Reference qualifiedId) {
		super(linkageName);
		this.qualifiedId = parentize(qualifiedId);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.ATTRIB_CPP_LINKAGE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, qualifiedId);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("extern");
		if(linkageName != null) {
			cp.appendStrings("(", linkageName, ",");
			cp.append(qualifiedId);
			cp.append(")");
		}
	}
	
}