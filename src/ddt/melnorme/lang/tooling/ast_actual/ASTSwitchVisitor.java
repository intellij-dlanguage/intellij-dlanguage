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
package ddt.melnorme.lang.tooling.ast_actual;


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.ASTVisitor;
import ddt.dtool.ast.definitions.DefVarFragment;
import ddt.dtool.ast.definitions.DefinitionAlias.DefinitionAliasFragment;
import ddt.dtool.ast.definitions.DefinitionAliasFunctionDecl;
import ddt.dtool.ast.definitions.DefinitionAliasVarDecl;
import ddt.dtool.ast.definitions.DefinitionClass;
import ddt.dtool.ast.definitions.DefinitionConstructor;
import ddt.dtool.ast.definitions.DefinitionEnum;
import ddt.dtool.ast.definitions.DefinitionEnumVar.DefinitionEnumVarFragment;
import ddt.dtool.ast.definitions.DefinitionFunction;
import ddt.dtool.ast.definitions.DefinitionInterface;
import ddt.dtool.ast.definitions.DefinitionMixinInstance;
import ddt.dtool.ast.definitions.DefinitionStruct;
import ddt.dtool.ast.definitions.DefinitionTemplate;
import ddt.dtool.ast.definitions.DefinitionUnion;
import ddt.dtool.ast.definitions.DefinitionVariable;
import ddt.dtool.ast.definitions.EnumMember;
import ddt.dtool.ast.definitions.Module;
import ddt.dtool.ast.references.NamedReference;

public abstract class ASTSwitchVisitor extends ASTVisitor {
	
	// NOTE: make sure preVisit code matches endVisit
	@Override
	public final boolean preVisit(ASTNode node) {
		switch (node.getNodeType()) {
		case MODULE:
			return visit((Module) node);
		case DEFINITION_VARIABLE:
		case DEFINITION_AUTO_VARIABLE:
			return visit((DefinitionVariable) node);
		case DEFINITION_VAR_FRAGMENT:
			return visit((DefVarFragment) node);
		case DEFINITION_FUNCTION:
			return visit((DefinitionFunction) node);
		case DEFINITION_CONSTRUCTOR:
			return visit((DefinitionConstructor) node);
		case DEFINITION_STRUCT:
			return visit((DefinitionStruct) node);
		case DEFINITION_UNION:
			return visit((DefinitionUnion) node);
		case DEFINITION_CLASS:
			return visit((DefinitionClass) node);
		case DEFINITION_INTERFACE:
			return visit((DefinitionInterface) node);
		case DEFINITION_TEMPLATE:
			return visit((DefinitionTemplate) node);
		case DEFINITION_MIXIN_INSTANCE:
			return visit((DefinitionMixinInstance) node);
		case DEFINITION_ENUM_VAR_FRAGMENT:
			return visit((DefinitionEnumVarFragment) node);
		case DEFINITION_ENUM:
			return visit((DefinitionEnum) node);
		case ENUM_MEMBER:
			return visit((EnumMember) node);
		case DEFINITION_ALIAS_VAR_DECL:
			return visit((DefinitionAliasVarDecl) node);
		case DEFINITION_ALIAS_FRAGMENT:
			return visit((DefinitionAliasFragment) node);
		case DEFINITION_ALIAS_FUNCTION_DECL:
			return visit((DefinitionAliasFunctionDecl) node);
		case REF_IDENTIFIER:
		case REF_IMPORT_SELECTION:
		case REF_MODULE_QUALIFIED:
		case REF_QUALIFIED:
		case REF_PRIMITIVE:
		case REF_MODULE:
			return visit((NamedReference) node);
		default:
			assertTrue(!(node instanceof NamedReference));
			return visitOther(node);
		}
	}
	
	@Override
	public final void postVisit(ASTNode node) {
		switch (node.getNodeType()) {
		case MODULE:
			endVisit((Module) node); return;
		case DEFINITION_VARIABLE:
		case DEFINITION_AUTO_VARIABLE:
			endVisit((DefinitionVariable) node); return;
		case DEFINITION_VAR_FRAGMENT:
			endVisit((DefVarFragment) node); return;
		case DEFINITION_FUNCTION:
			endVisit((DefinitionFunction) node); return;
		case DEFINITION_CONSTRUCTOR:
			endVisit((DefinitionConstructor) node); return;
		case DEFINITION_STRUCT:
			endVisit((DefinitionStruct) node); return;
		case DEFINITION_UNION:
			endVisit((DefinitionUnion) node); return;
		case DEFINITION_CLASS:
			endVisit((DefinitionClass) node); return;
		case DEFINITION_INTERFACE:
			endVisit((DefinitionInterface) node); return;
		case DEFINITION_TEMPLATE:
			endVisit((DefinitionTemplate) node); return;
		case DEFINITION_MIXIN_INSTANCE:
			endVisit((DefinitionMixinInstance) node); return;
		case DEFINITION_ENUM_VAR_FRAGMENT:
			endVisit((DefinitionEnumVarFragment) node); return;
		case DEFINITION_ENUM:
			endVisit((DefinitionEnum) node); return;
		case ENUM_MEMBER:
			endVisit((EnumMember) node); return;
		case DEFINITION_ALIAS_VAR_DECL:
			endVisit((DefinitionAliasVarDecl) node); return;
		case DEFINITION_ALIAS_FRAGMENT:
			endVisit((DefinitionAliasFragment) node); return;
		case DEFINITION_ALIAS_FUNCTION_DECL:
			endVisit((DefinitionAliasFunctionDecl) node); return;
		default:
			endVisitOther(node);
			break;
		}
	}
	
	public abstract boolean visitOther(ASTNode node);
	public abstract void endVisitOther(ASTNode node);
	
	public abstract boolean visit(Module node);
	public abstract void endVisit(Module node);
	
	public abstract boolean visit(DefinitionStruct node);
	public abstract void endVisit(DefinitionStruct node);
	
	public abstract boolean visit(DefinitionUnion node);
	public abstract void endVisit(DefinitionUnion node);
	
	public abstract boolean visit(DefinitionClass node);
	public abstract void endVisit(DefinitionClass node);
	
	public abstract boolean visit(DefinitionInterface node);
	public abstract void endVisit(DefinitionInterface node);
	
	
	public abstract boolean visit(DefinitionTemplate node);
	public abstract void endVisit(DefinitionTemplate node);
	
	public abstract boolean visit(DefinitionMixinInstance node);
	public abstract void endVisit(DefinitionMixinInstance node);
	
	public abstract boolean visit(DefinitionEnumVarFragment node);
	public abstract void endVisit(DefinitionEnumVarFragment node);
	
	public abstract boolean visit(DefinitionEnum node);
	public abstract void endVisit(DefinitionEnum node);
	
	public abstract boolean visit(EnumMember node);
	public abstract void endVisit(EnumMember node);
	
	public abstract boolean visit(DefinitionAliasVarDecl node);
	public abstract void endVisit(DefinitionAliasVarDecl node);
	
	public abstract boolean visit(DefinitionAliasFragment node);
	public abstract void endVisit(DefinitionAliasFragment node);
	
	public abstract boolean visit(DefinitionAliasFunctionDecl node);
	public abstract void endVisit(DefinitionAliasFunctionDecl node);
	
	/* ---------------------------------- */
	
	public abstract boolean visit(DefinitionFunction node);
	public abstract void endVisit(DefinitionFunction node);
	
	public abstract boolean visit(DefinitionConstructor node);
	public abstract void endVisit(DefinitionConstructor node);
	
	/* ---------------------------------- */
	
	public abstract boolean visit(DefinitionVariable node);
	public abstract void endVisit(DefinitionVariable node);
	
	public abstract boolean visit(DefVarFragment node);
	public abstract void endVisit(DefVarFragment node);
	
	/* ---------------------------------- */
	
	public abstract boolean visit(NamedReference elem);
	
	
	/* ========================================== */
	
	public static abstract class ASTCommonSwitchVisitor extends ASTSwitchVisitor {
		
		@Override public void endVisitOther(ASTNode node) { }
		
		@Override public void endVisit(Module node) { }
		
		@Override public void endVisit(DefinitionStruct node) {}
		
		@Override public void endVisit(DefinitionUnion node) { }
		
		@Override public void endVisit(DefinitionClass node) { }
		
		@Override public void endVisit(DefinitionInterface node) { }
		
		@Override public void endVisit(DefinitionTemplate node) { }
		
		@Override public void endVisit(DefinitionMixinInstance node) { }
		
		@Override public void endVisit(DefinitionEnumVarFragment node) { }
		
		@Override public void endVisit(DefinitionEnum node) { }
		
		@Override public void endVisit(EnumMember node) { }
		
		@Override public void endVisit(DefinitionAliasVarDecl node) { }
		
		@Override public void endVisit(DefinitionAliasFragment node) { }
		
		@Override public void endVisit(DefinitionAliasFunctionDecl node) { }
		
		@Override public void endVisit(DefinitionFunction node) { }
		
		@Override public void endVisit(DefinitionConstructor node) { }
		
		@Override public void endVisit(DefinitionVariable node) { }
		
		@Override public void endVisit(DefVarFragment node) { }
		
	}
	
}