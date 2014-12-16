/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.statements;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.CommonDefVarSemantics;
import ddt.dtool.engine.analysis.IVarDefinitionLike;

public class VariableDefWithInit extends DefUnit implements IVarDefinitionLike {
	
	public final Reference type;
	public final Expression defaultValue;
	
	public VariableDefWithInit(Reference type, ProtoDefSymbol defId, Expression defaultValue) {
		super(defId);
		this.type = parentize(assertNotNull(type));
		this.defaultValue = parentize(defaultValue);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.VARIABLE_DEF_WITH_INIT;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, defaultValue);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(type, " ");
		cp.append(defname);
		cp.append(" = ", defaultValue);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Variable;
	}
	
	@Override
	public Reference getDeclaredType() {
		return type;
	}
	
	@Override
	public IInitializer getDeclaredInitializer() {
		return defaultValue;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected CommonDefVarSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new CommonDefVarSemantics(this, pickedElement);
	}
	
}