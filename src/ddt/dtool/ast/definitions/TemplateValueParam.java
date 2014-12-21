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
package ddt.dtool.ast.definitions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.VarSemantics;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.templates.AliasElement;

public class TemplateValueParam extends DefUnit implements IConcreteNamedElement, ITemplateParameter {
	
	public final Reference type;
	public final Expression specializationValue;
	public final Expression defaultValue;
	
	public TemplateValueParam(Reference type, ProtoDefSymbol defId, Expression specializationValue, 
		Expression defaultValue) {
		super(defId);
		this.type = parentize(assertNotNull(type));
		this.specializationValue = parentize(specializationValue);
		this.defaultValue = parentize(defaultValue);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.TEMPLATE_VALUE_PARAM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, specializationValue);
		acceptVisitor(visitor, defaultValue);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(type, " ");
		cp.append(defname);
		cp.append(" : ", specializationValue);
		cp.append(" = ", defaultValue);
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
			}
			
		};
	}
	
	@Override
	public AliasElement createTemplateArgument(Resolvable argument) {
		return new AliasElement(defname, null); // TODO: correct instantiation
	}
	
}