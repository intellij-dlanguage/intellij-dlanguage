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
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * Declaration of a template mixin with an associated identifier:
 * http://dlang.org/template-mixin.html#TemplateMixinDeclaration (with MixinIdentifier)
 */
public class DefinitionMixinInstance extends CommonDefinition implements IStatement, IConcreteNamedElement {
	
	public final Reference templateInstance;
	
	public DefinitionMixinInstance(Token[] comments, ProtoDefSymbol defId, Reference templateInstance) {
		super(comments, defId);
		this.templateInstance = parentize(templateInstance);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_MIXIN_INSTANCE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, templateInstance);
		acceptVisitor(visitor, defname);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("mixin ");
		cp.append(templateInstance, " ");
		cp.append(defname);
		cp.append(";");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Mixin;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeSemantics(this, pickedElement) {
		
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				if(templateInstance != null) {
					// TODO: add fake element for missing syntax
					
					INamedElement result = templateInstance.getSemantics(context).resolveTargetElement().result;
					search.evaluateInMembersScope(result);
				}
			}
			
		};
	}
	
}