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
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.expressions.Expression;

public class RefTypeof extends Reference implements IQualifierNode {
	
	public final Expression expression;
	
	public RefTypeof(Expression exp) {
		this.expression = parentize(exp);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_TYPEOF;
	}
	
	public static class ExpRefReturn extends Expression {
		
		public ExpRefReturn() {}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.EXP_REF_RETURN;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append("return");
		}
		
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, expression);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("typeof");
		cp.append("(", expression, ")");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return expression.getSemantics(context).resolveTypeOfUnderlyingValue();
			}
			
			@Override
			public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
				return resolveToInvalidValue();
			};
			
		};
	}
	
}