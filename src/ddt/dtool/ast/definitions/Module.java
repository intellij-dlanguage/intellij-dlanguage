/*******************************************************************************
 * Copyright (c) 2010, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.definitions;

import static ddt.dtool.util.NewUtils.assertCast;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.nio.file.Path;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.IModuleNode;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.PackageNamespace;
import ddt.dtool.ast.references.RefModule;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;
import ddt.dtool.parser.common.Token;
import ddt.dtool.util.NewUtils;

/**
 * D Module. 
 * The top-level AST class, has no parent, is the first and main node of every compilation unit.
 */
public class Module extends DefUnit implements IModuleNode, IConcreteNamedElement, IScopeElement {
	
	public static class ModuleDefSymbol extends DefSymbol {
		
		protected Module module;
		
		public ModuleDefSymbol(String id) {
			super(id);
		}
		
		@Override
		protected ASTNode getParent_Concrete() {
			return assertCast(parent, DeclarationModule.class);
		}
		
		@Override
		public DefUnit getDefUnit() {
			return module;
		}
	}
	
	public static class DeclarationModule extends ASTNode {
		
		public final Token[] comments;
		public final ArrayView<IToken> packageList;
		public final String[] packages; // Old API
		public final DefSymbol moduleName; 
		
		public DeclarationModule(Token[] comments, ArrayView<IToken> packageList, BaseLexElement moduleDefUnit) {
			this.comments = comments;
			this.packageList = assertNotNull(packageList);
			this.packages = RefModule.tokenArrayToStringArray(packageList);
			this.moduleName = new ModuleDefSymbol(moduleDefUnit.getSourceValue());
			this.moduleName.setSourceRange(moduleDefUnit.getSourceRange());
			this.moduleName.setParsedStatus();
			parentize(moduleName);
		}
		
		public ModuleDefSymbol getModuleSymbol() {
			return (ModuleDefSymbol) moduleName;
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.DECLARATION_MODULE;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			//TreeVisitor.acceptChildren(visitor, packages);
			acceptVisitor(visitor, moduleName);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append("module ");
			cp.appendTokenList(packageList, ".", true);
			cp.append(moduleName.name);
			cp.append(";");
		}
		
	}
	
	public static Module createModuleNoModuleDecl(String moduleName, ArrayView<ASTNode> members,
			Path compilationUnitPath, SourceRange modRange) {
		ModuleDefSymbol defSymbol = new ModuleDefSymbol(moduleName);
		defSymbol.setSourceRange(modRange.getStartPos(), 0);
		return new Module(defSymbol, null, members, compilationUnitPath);
	}
	
	public final DeclarationModule md;
	public final ArrayView<ASTNode> members;
	public final Path compilationUnitPath; // can be null. This might be removed in the future.
	
	public Module(ModuleDefSymbol defSymbol, DeclarationModule md, ArrayView<ASTNode> members, 
			Path compilationUnitPath) {
		super(defSymbol, false);
		defSymbol.module = this;
		this.md = parentize(md);
		this.members = parentize(members);
		assertNotNull(members);
		this.compilationUnitPath = compilationUnitPath;
		
		this.topLevelElement = createTopLevelElement();
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.MODULE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, md);
		acceptVisitor(visitor, members);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(md, cp.ST_SEP);
		cp.appendList(members, cp.ST_SEP);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Module;
	}
	
	@Override
	public String getFullyQualifiedName() {
		ASTCodePrinter cp = new ASTCodePrinter();
		if(md != null) {
			cp.appendTokenList(md.packageList, ".", true);
		}
		cp.append(getName());
		return cp.toString();
	}
	
	@Override
	public String getModuleFullyQualifiedName() {
		return getFullyQualifiedName();
	}
	
	public String[] getDeclaredPackages() {
		if(md != null) {
			return md.packages;
		}
		return NewUtils.EMPTY_STRING_ARRAY;
	}
	
	@Override
	public Path getCompilationUnitPath() {
		return compilationUnitPath;
	}
	
	@Override
	public Token[] getDocComments() {
		if(md != null) {
			return md.comments;
		}
		return null;
	}
	
	/* -----------------  ----------------- */
	
	protected final INamedElement topLevelElement;
	
	protected INamedElement createTopLevelElement() {
		if(md == null || md.packages.length == 0 || md.packages[0] == "") {
			return this;
		} else {
			String[] packNames = md.packages;
			return PackageNamespace.createPartialDefUnits(packNames, this, this);
		}
	}
	
	@Override
	protected void doPerformNameLookupInThisLexicalScope(CommonScopeLookup search) {
		search.evaluateScope(this);
		
		search.evaluateNamedElementForSearch(topLevelElement);
		
		findDefUnitInObjectIntrinsic(search);
	}
	
	public static void findDefUnitInObjectIntrinsic(CommonScopeLookup search) {
		INamedElement targetModule = ResolvableSemantics.findModuleUnchecked(search.modResolver, "object");
		search.evaluateInMembersScope(targetModule);
	}
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeSemantics(this, pickedElement) {
			
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				search.evaluateScope(Module.this);
			}
			
		};
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(members, false);
	}
	
}