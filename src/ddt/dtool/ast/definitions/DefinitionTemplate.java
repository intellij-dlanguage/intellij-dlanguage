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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.DeclBlock;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.MissingParenthesesExpression;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.engine.analysis.templates.DefTemplateSemantics;
import ddt.dtool.parser.common.Token;

/**
 * Definition of a template.
 * http://dlang.org/template.html#TemplateDeclaration
 * 
 * (Technically not allowed as statement, but parse so anyways.)
 */
public class DefinitionTemplate extends CommonDefinition 
	implements IScopeElement, IDeclaration, IStatement, ITemplatableElement, IConcreteNamedElement 
{
	
	public final boolean isMixin;
	public final ArrayView<ITemplateParameter> tplParams;
	public final Expression tplConstraint;
	public final DeclBlock decls;
	
	public final boolean wrapper;
	
	public DefinitionTemplate(Token[] comments, boolean isMixin, ProtoDefSymbol defId, 
			ArrayView<ITemplateParameter> tplParams, Expression tplConstraint, DeclBlock decls) {
		super(comments, defId);
		this.isMixin = isMixin;
		this.tplParams = parentize(tplParams);
		this.tplConstraint = parentize(tplConstraint);
		this.decls = parentize(decls);
		
		this.wrapper = false; // TODO: determine this
		if(wrapper) {
			assertTrue(this.decls.nodes.size() == 1);
			assertTrue(decls.nodes.get(0) instanceof DefUnit);
		}
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_TEMPLATE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, tplParams);
		acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, decls);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isMixin, "mixin ");
		cp.append("template ");
		cp.append(defname, " ");
		cp.appendList("(", tplParams, ",", ") ");
		tplConstraintToStringAsCode(cp, tplConstraint);
		cp.append(decls);
	}
	
	public static void tplConstraintToStringAsCode(ASTCodePrinter cp, Expression tplConstraint) {
		if(tplConstraint instanceof MissingParenthesesExpression) {
			cp.append("if", tplConstraint);
		} else {
			cp.append("if(", tplConstraint, ")");
		}
	}
	
	public ArrayView<ITemplateParameter> getITemplateParameters() {
		return nonNull(tplParams);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Template;
	}
	
	@Override
	public boolean isTemplated() {
		return true;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new DefTemplateSemantics(this, pickedElement);
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(tplParams);
	}
	
}