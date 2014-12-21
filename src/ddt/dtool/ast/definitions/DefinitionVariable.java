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
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.engine.analysis.CommonDefVarSemantics;
import ddt.dtool.engine.analysis.IVarDefinitionLike;
import ddt.dtool.parser.common.Token;

/**
 * A variable definition. 
 * Optionally has multiple variables defined with the multi-identifier syntax.
 */
public class DefinitionVariable extends CommonDefinition 
	implements IDeclaration, IStatement, INonScopedContainer, IVarDefinitionLike { 
	
	public static final ArrayView<DefVarFragment> NO_FRAGMENTS = ArrayView.create(new DefVarFragment[0]);
	
	public final Reference type; // Can be null
	public final Reference cstyleSuffix;
	public final IInitializer initializer;
	protected final ArrayView<DefVarFragment> fragments;
	
	public DefinitionVariable(Token[] comments, ProtoDefSymbol defId, Reference type, Reference cstyleSuffix,
		IInitializer initializer, ArrayView<DefVarFragment> fragments)
	{
		super(comments, defId);
		this.type = parentize(type);
		this.cstyleSuffix = parentize(cstyleSuffix);
		this.initializer = parentize(initializer);
		this.fragments = parentize(fragments);
		assertTrue(fragments == null || fragments.size() > 0);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_VARIABLE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, cstyleSuffix);
		acceptVisitor(visitor, initializer);
		
		acceptVisitor(visitor, fragments);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(type, " ");
		cp.append(defname);
		cp.append(cstyleSuffix);
		cp.append(" = ", initializer);
		cp.appendList(", ", fragments, ", ", "");
		cp.append(";");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Variable;
	}
	
	public ArrayView<DefVarFragment> getFragments() {
		return fragments == null ? NO_FRAGMENTS : fragments;
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return getFragments();
	}
	
	@Override
	public Reference getDeclaredType() {
		return type;
	}
	
	@Override
	public IInitializer getDeclaredInitializer() {
		return initializer;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected CommonDefVarSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new CommonDefVarSemantics(this, pickedElement);
	}
	
	/* -----------------  ----------------- */
	
	public static class DefinitionAutoVariable extends DefinitionVariable {
		
		public DefinitionAutoVariable(Token[] comments, ProtoDefSymbol defId, IInitializer initializer,
			ArrayView<DefVarFragment> fragments) {
			super(comments, defId, null, null, initializer, fragments);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.DEFINITION_AUTO_VARIABLE;
		}
		
	}
	
}