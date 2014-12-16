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
package ddt.dtool.ast.references;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.Set;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.completion.CompletionScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.ModuleProxy;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;

/** 
 * A module reference (in import declarations only).
 */
public class RefModule extends NamedReference {
	
	public final ArrayView<IToken> packageList;
	public final BaseLexElement moduleToken;
	public final ArrayView<String> packages; // TODO: Old API, refactor?
	public final String module;
	
	public RefModule(ArrayView<IToken> packageList, BaseLexElement moduleToken) {
		this.packageList = assertNotNull(packageList);
		this.moduleToken = assertNotNull(moduleToken);
		this.packages = ArrayView.create(tokenArrayToStringArray(packageList));
		this.module = moduleToken.getSourceValue();
	}
	
	public static String[] tokenArrayToStringArray(ArrayView<IToken> tokenArray) {
		String[] stringArray = new String[tokenArray.size()];
		for (int i = 0; i < stringArray.length; i++) {
			stringArray[i] = tokenArray.get(i).getSourceValue();
		}
		return stringArray;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_MODULE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	public String getModuleSimpleName() {
		return module;
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendTokenList(packageList, ".", true);
		cp.append(module);
	}
	
	@Override
	public String getCoreReferenceName() {
		return getRefModuleFullyQualifiedName();
	}
	
	@Override
	public boolean isMissingCoreReference() {
		return module == null || module.isEmpty();
	}
	
	public String getRefModuleFullyQualifiedName() {
		return toStringAsCode();
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public void performNameLookup(CommonScopeLookup search) {
		if(search instanceof CompletionScopeLookup) {
			CompletionScopeLookup prefixDefUnitSearch = (CompletionScopeLookup) search;
			String prefix = prefixDefUnitSearch.searchOptions.searchPrefix;
			Set<String> matchedModule = prefixDefUnitSearch.findModulesWithPrefix(prefix);
			
			for (String fqName : matchedModule) {
				search.addMatch(new ModuleProxy(fqName, prefixDefUnitSearch.modResolver, true, RefModule.this));
			}
		} else {
			assertTrue(isMissingCoreReference() == false);
			String moduleFQName = getRefModuleFullyQualifiedName();
			ModuleProxy moduleProxy = new ModuleProxy(moduleFQName, search.modResolver, true, RefModule.this);
			if(moduleProxy.resolveUnderlyingNode() != null) {
				search.addMatch(moduleProxy);
			}
		}
	}
	
	public ModuleProxy getModuleProxy(ISemanticContext mr) {
		return new ModuleProxy(getRefModuleFullyQualifiedName(), mr, false, RefModule.this);
	}
	
}