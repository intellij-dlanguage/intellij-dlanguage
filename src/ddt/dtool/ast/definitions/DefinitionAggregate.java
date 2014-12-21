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

import static ddt.dtool.engine.analysis.DeeLanguageIntrinsics.D2_063_intrinsics;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.intrinsics.InstrinsicsScope;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.DeclBlock;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * A definition of a aggregate. 
 */
public abstract class DefinitionAggregate extends CommonDefinition 
	implements IStatement, IScopeElement, ITemplatableElement, IConcreteNamedElement
{
	
	public interface IAggregateBody extends IASTNode {
	}
	
	public final ArrayView<ITemplateParameter> tplParams;
	public final Expression tplConstraint;
	public final IAggregateBody aggrBody;
	
	public final IScopeElement membersScope;
	
	public DefinitionAggregate(Token[] comments, ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams,
		Expression tplConstraint, IAggregateBody aggrBody) {
		super(comments, defId);
		this.tplParams = parentize(tplParams);
		this.tplConstraint = parentize(tplConstraint);
		this.aggrBody = parentize(aggrBody);
		
		membersScope = aggrBody instanceof DeclBlock ? 
				((DeclBlock) aggrBody) :
				parentize(new DeclBlock(ArrayView.<ASTNode>createFrom())); 
		assertNotNull(membersScope);
	}
	
	protected void acceptNodeChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, tplParams);
		acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, aggrBody);
	}
	
	public void aggregateToStringAsCode(ASTCodePrinter cp, String keyword, boolean printDecls) {
		cp.append(keyword);
		cp.append(defname, " ");
		cp.appendList("(", tplParams, ",", ") ");
		DefinitionTemplate.tplConstraintToStringAsCode(cp, tplConstraint);
		if(printDecls) {
			cp.append(aggrBody, "\n");
		}
	}
	
	@Override
	public boolean isTemplated() {
		return tplParams != null;
	}
	
	
	/* ----------------- ----------------- */
	
	@Override
	protected AggregateSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		InstrinsicsScope commonTypeScope = new InstrinsicsScope(D2_063_intrinsics.createCommonProperties(this));
		return new AggregateSemantics(this, commonTypeScope, pickedElement);
	}
	
	public class AggregateSemantics extends TypeSemantics {
		
		protected final InstrinsicsScope commonTypeScope;
		
		public AggregateSemantics(IConcreteNamedElement typeElement, InstrinsicsScope commonTypeScope, 
				PickedElement<?> pickedElement) {
			super(typeElement, pickedElement);
			this.commonTypeScope = commonTypeScope;
		}
		
		@Override
		public void resolveSearchInMembersScope(CommonScopeLookup search) {
			search.evaluateScope(DefinitionAggregate.this.membersScope);
			commonTypeScope.resolveSearchInScope(search);
		}
	
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(tplParams);
	}
	
}