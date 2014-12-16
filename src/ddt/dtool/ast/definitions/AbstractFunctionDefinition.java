/*******************************************************************************
 * Copyright (c) 2013, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.definitions;


import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.statements.IFunctionBody;
import ddt.dtool.parser.common.Token;

public abstract class AbstractFunctionDefinition extends CommonDefinition 
	implements ICallableElement, IScopeElement, ITemplatableElement
{
	
	public final ArrayView<ITemplateParameter> tplParams;
	public final ArrayView<IFunctionParameter> fnParams;
	public final ArrayView<FunctionAttributes> fnAttributes;
	public final Expression tplConstraint;
	public final IFunctionBody fnBody;
	
	public AbstractFunctionDefinition(Token[] comments, ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams,
		ArrayView<IFunctionParameter> fnParams, ArrayView<FunctionAttributes> fnAttributes, Expression tplConstraint,
		IFunctionBody fnBody) {
		super(comments, defId);
		
		this.tplParams = parentize(tplParams);
		this.fnParams = parentize(fnParams);
		this.fnAttributes = fnAttributes;
		this.tplConstraint = parentize(tplConstraint);
		this.fnBody = parentize(fnBody);
	}
	
	public final ArrayView<ASTNode> getParams_asNodes() {
		return CoreUtil.blindCast(fnParams);
	}
	
	public static ArrayView<IFunctionParameter> NO_PARAMS = new ArrayView<>(new IFunctionParameter[0]);
	
	@Override
	public ArrayView<IFunctionParameter> getParameters() {
		return fnParams == null ? NO_PARAMS : fnParams;
	}
	
	public void toStringAsCode_fromDefId(ASTCodePrinter cp) {
		cp.append(defname);
		cp.appendList("(", tplParams, ",", ") ");
		cp.appendList("(", getParams_asNodes(), ",", ") ");
		cp.appendTokenList(fnAttributes, " ", true);
		DefinitionTemplate.tplConstraintToStringAsCode(cp, tplConstraint);
		cp.appendNodeOrNullAlt(fnBody, " ");
	}
	
	@Override
	public boolean isTemplated() {
		return tplParams != null;
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(tplParams);
		search.evaluateScopeNodeList(fnParams);
	}
	
	/* ------------------------------------------------------------------------ */
	
}