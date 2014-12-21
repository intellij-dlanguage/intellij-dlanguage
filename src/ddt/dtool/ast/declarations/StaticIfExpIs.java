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

import static ddt.dtool.util.NewUtils.assertCast;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.TypeAliasSemantics;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.definitions.ITemplateParameter;
import ddt.dtool.ast.expressions.ExpIs;
import ddt.dtool.ast.expressions.ExpIs.ExpIsSpecialization;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;

public class StaticIfExpIs extends Expression {
	
	public final Reference typeRef;
	public final StaticIfExpIsDefUnit isExpDefUnit;
	public final ExpIsSpecialization specKind;
	public final Reference specTypeRef;
	public final ArrayView<ITemplateParameter> tplParams;
	
	public StaticIfExpIs(Reference typeRef, StaticIfExpIsDefUnit isExpDefUnit, ExpIsSpecialization specKind, 
		Reference specTypeRef, ArrayView<ITemplateParameter> tplParams) {
		this.typeRef = parentize(assertNotNull(typeRef));
		this.isExpDefUnit = parentize(isExpDefUnit);
		this.specKind = specKind;
		this.specTypeRef = parentize(specTypeRef);
		assertTrue((specTypeRef == null) ==
			(specKind != ExpIsSpecialization.TYPE_SUBTYPE && specKind != ExpIsSpecialization.TYPE_EXACT)); 
		this.tplParams = parentize(tplParams);
		assertTrue((tplParams == null) ? true :
			(specKind == ExpIsSpecialization.TYPE_SUBTYPE || specKind == ExpIsSpecialization.TYPE_EXACT)); 
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.STATIC_IF_EXP_IS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, typeRef);
		acceptVisitor(visitor, isExpDefUnit);
		acceptVisitor(visitor, specTypeRef);
		acceptVisitor(visitor, tplParams);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("is(");
		cp.append(typeRef, " ");
		cp.append(isExpDefUnit);
		ExpIs.toStringAsCode_isExpSpecKind(cp, specKind, specTypeRef);
		cp.appendList(", ", tplParams, ", ", "");
		cp.append(")");
	}
	
	@Override
	public void doNodeSimpleAnalysis() {
		if(!(getParent() instanceof DeclarationStaticIf)) {
			// TODO add error
		}
	}
	
	public static class StaticIfExpIsDefUnit extends DefUnit {
		
		public StaticIfExpIsDefUnit(ProtoDefSymbol defIdTuple) {
			super(defIdTuple);
		}
		
		@Override
		protected StaticIfExpIs getParent_Concrete() {
			return assertCast(parent, StaticIfExpIs.class);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.STATIC_IF_EXP_IS_DEF_UNIT;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, defname);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(defname);
		}
		
		@Override
		public EArcheType getArcheType() {
			return EArcheType.Alias;
		}
		
		/* -----------------  ----------------- */
		
		@Override
		protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
			return new TypeAliasSemantics(this, pickedElement) {
			
			@Override
			protected Resolvable getAliasTarget() {
				return getParent_Concrete().typeRef;
			}
			
		};
		}
		
	}
	
}