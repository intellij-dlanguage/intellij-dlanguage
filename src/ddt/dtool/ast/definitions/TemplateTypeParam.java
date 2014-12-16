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
package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.TypeAliasSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.templates.TypeAliasElement;

public class TemplateTypeParam extends DefUnit implements ITemplateParameter {
	
	public final Reference specializationType;
	public final Reference defaultType;
	
	public TemplateTypeParam(ProtoDefSymbol defId, Reference specializationType, Reference defaultType){
		super(defId);
		this.specializationType = parentize(specializationType);
		this.defaultType = parentize(defaultType);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.TEMPLATE_TYPE_PARAM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, specializationType);
		acceptVisitor(visitor, defaultType);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(defname);
		cp.append(" : ", specializationType);
		cp.append(" = ", defaultType);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.TypeParameter;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeAliasSemantics(this, pickedElement) {
			@Override
			protected Resolvable getAliasTarget() {
				return specializationType;
			}
		};
	}
	
	@Override
	public TypeAliasElement createTemplateArgument(Resolvable resolvable) {
		return new TypeAliasElement(defname, resolvable);
	}
	
}