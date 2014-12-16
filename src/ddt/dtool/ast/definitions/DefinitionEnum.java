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
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.intrinsics.InstrinsicsScope;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

public class DefinitionEnum extends CommonDefinition implements IDeclaration, IStatement, IConcreteNamedElement {
	
	public final Reference type;
	public final EnumBody body;
	
	public DefinitionEnum(Token[] comments, ProtoDefSymbol defId, Reference type, EnumBody body) {
		super(comments, defId);
		this.type = parentize(type);
		this.body = parentize(body);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_ENUM;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, body);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("enum ");
		cp.append(defname, " ");
		cp.append(": ", type);
		cp.append(body);
	}
	
	public static class EnumBody extends ASTNode implements IScopeElement {
		
		public final NodeListView<EnumMember> nodeList;
		
		public EnumBody(NodeListView<EnumMember> nodeList) {
			this.nodeList = parentize(assertNotNull(nodeList));
		}
		
		@Override
		protected ASTNode getParent_Concrete() {
			assertTrue(parent instanceof DeclarationEnum || parent instanceof DefinitionEnum);
			return parent;
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.ENUM_BODY;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, nodeList);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.appendNodeList("{", nodeList, ", ", "}");
		}
		
		@Override
		public void resolveSearchInScope(CommonScopeLookup search) {
			search.evaluateScopeNodeList(nodeList, false);
		}
		
	}
	
	public static class NoEnumBody extends EnumBody {
		
		public static NodeListView<EnumMember> NULL_DECLS = new NodeListView<>(new EnumMember[0], false);
		
		public NoEnumBody() {
			super(NULL_DECLS);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.ENUM_BODY;
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(";");
		}
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Enum;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeSemantics(this, pickedElement) {
		
			protected final InstrinsicsScope commonTypeScope = createAggregateCommonTypeScope();
			
			protected InstrinsicsScope createAggregateCommonTypeScope() {
				return new InstrinsicsScope(D2_063_intrinsics.createCommonProperties(getTypeElement()));
			}
			
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				if(body != null) {
					body.resolveSearchInScope(search);
				}
				commonTypeScope.resolveSearchInScope(search);
			}
			
		};
	}
	
}