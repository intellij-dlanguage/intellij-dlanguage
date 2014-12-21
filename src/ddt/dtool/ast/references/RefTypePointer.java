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
package ddt.dtool.ast.references;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.TypeReferenceSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;

public class RefTypePointer extends CommonNativeTypeReference {
	
	public final Reference elemType;
	
	public RefTypePointer(Reference elemType) {
		this.elemType = parentize(elemType);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_TYPE_POINTER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, elemType);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(elemType, "*");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected TypeReferenceSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeReferenceSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return Resolvable.wrapResult(DeeLanguageIntrinsics.D2_063_intrinsics.pointerType);
			}
			
		};
	}
	
}