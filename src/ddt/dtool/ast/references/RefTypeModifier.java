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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.parser.DeeTokens;

public class RefTypeModifier extends Reference implements IQualifierNode {
	
	public static enum TypeModifierKinds {
		CONST(DeeTokens.KW_CONST),
		IMMUTABLE(DeeTokens.KW_IMMUTABLE),
		SHARED(DeeTokens.KW_SHARED),
		INOUT(DeeTokens.KW_INOUT),
		
		VECTOR(DeeTokens.KW___VECTOR),
		;
		public final String sourceValue;
		
		TypeModifierKinds(DeeTokens token) {
			sourceValue = token.getSourceValue();
		}
	}
	
	public final TypeModifierKinds modifier;
	public final Reference ref;
	public final boolean hasParens;
	
	public RefTypeModifier(TypeModifierKinds modifier, Reference ref, boolean hasParens) {
		this.modifier = assertNotNull(modifier);
		this.ref = parentize(ref);
		this.hasParens = hasParens;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_MODIFIER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, ref);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(modifier.sourceValue);
		if(hasParens) {
			cp.append("(", ref, ")");
		} else {
			cp.append(" ", ref);
		}
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return findTargetElementsForReference(context, ref, findOneOnly);
			}
			
		};
	}
	
}