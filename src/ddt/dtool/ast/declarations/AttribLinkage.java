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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class AttribLinkage extends Attribute {
	
	public final String linkageName;
	public final Linkage linkage;
	
	public AttribLinkage(String linkageName) {
		this.linkageName = linkageName;
		this.linkage = Linkage.fromString(linkageName);
		if(linkage == Linkage.CPP) {
			assertTrue(this instanceof AttribCppLinkage);
		}
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.ATTRIB_LINKAGE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("extern");
		if(linkageName != null) {
			cp.appendStrings("(", linkageName, ")");
		}
	}
	
	public enum Linkage {
	    D("D"),
	    C("C"),
	    CPP("C++"),
	    WINDOWS("Windows"),
	    PASCAL("Pascal"),
	    SYSTEM("System");
	    
	    public String name;
	    
	    private Linkage(String name) {
			this.name = name;
	    }
	    
	    public static Linkage fromString(String str) {
	    	Linkage[] values = Linkage.values();
	    	for (Linkage linkage : values) {
				if(areEqual(linkage.name, str)) {
					return linkage;
				}
			}
	    	return null;
	    }
	    
	}
	
}