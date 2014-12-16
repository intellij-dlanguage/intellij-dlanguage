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
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.VarSemantics;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.parser.common.LexElement;

public class ForeachVariableDef extends DefUnit implements IConcreteNamedElement {
	
	public final boolean isRef;
	public final LexElement typeMod;
	public final Reference type;
	
	public ForeachVariableDef(boolean isRef, LexElement typeMod, Reference type, ProtoDefSymbol defId) {
		super(defId);
		this.isRef = isRef;
		this.typeMod = typeMod;
		this.type = parentize(type);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.FOREACH_VARIABLE_DEF;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isRef, "ref ");
		cp.appendToken(typeMod, " ");
		cp.append(type, " ");
		cp.append(defname);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Variable;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new VarSemantics(this, pickedElement) {
			
			@Override
			protected Resolvable getTypeReference() {
				return type;
			};
			
		};
	}
	
}