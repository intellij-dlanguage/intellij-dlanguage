/*******************************************************************************
 * Copyright (c) 2013, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.definitions;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.references.Reference;

/**
 * Adapter class to parse C-style postfix declarator, i.e., 
 * the array type info after the symbol name: int* foo[] 
 */
public class CStyleRootRef extends Reference {
	
	public CStyleRootRef() { }
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.CSTYLE_ROOT_REF;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return null;
			}
			
		};
	}
}