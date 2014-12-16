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
package ddt.dtool.engine.analysis.templates;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.TypeAliasSemantics;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.dtool.ast.definitions.DefSymbol;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.expressions.Resolvable;

public class TypeAliasElement extends InstantiatedDefUnit {
	
	public final Resolvable target;
	
	public TypeAliasElement(DefSymbol defname, Resolvable target) {
		super(defname);
		this.target = target;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.TEMPLATE_TYPE_PARAM__INSTANCE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode_instantiatedDefUnit(ASTCodePrinter cp) {
		cp.append(defname);
		cp.append(" = ", target);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeAliasSemantics(this, pickedElement) {
		
			@Override
			protected IConcreteNamedElement doResolveConcreteElement() {
				return super.doResolveConcreteElement(); // TODO write test case
			}
			
			@Override
			protected Resolvable getAliasTarget() {
				return target;
			}
			
		};
	}
	
}