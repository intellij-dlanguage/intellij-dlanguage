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
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.statements.IFunctionBody;
import ddt.dtool.parser.common.Token;

public class DefinitionConstructor extends AbstractFunctionDefinition implements IDeclaration, IConcreteNamedElement {
	
	public DefinitionConstructor(Token[] comments, ProtoDefSymbol defId, 
		ArrayView<ITemplateParameter> tplParams, ArrayView<IFunctionParameter> fnParams, 
		ArrayView<FunctionAttributes> fnAttributes, Expression tplConstraint, IFunctionBody fnBody) 
	{
		super(comments, defId, tplParams, fnParams, fnAttributes, tplConstraint, fnBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_CONSTRUCTOR;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, tplParams);
		acceptVisitor(visitor, fnParams);
		acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, fnBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		toStringAsCode_fromDefId(cp);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Constructor;
	}
	
	@Override
	public String getNameInRegularNamespace() {
		return null;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeSemantics(this, pickedElement) {
			
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				// Not applicable to constructor as it cannot be referred directly
			}
			
		};
	}
	
}