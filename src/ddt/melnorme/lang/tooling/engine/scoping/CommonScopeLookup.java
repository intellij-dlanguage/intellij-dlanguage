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
package ddt.melnorme.lang.tooling.engine.scoping;


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IModuleElement;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.core.fntypes.Function;
import ddt.melnorme.utilbox.misc.StringUtil;

public abstract class CommonScopeLookup extends NamedElementsVisitor {
	
	/** Flag for stop searching when suitable matches are found. */
	public final boolean findOnlyOne;
	/** The module where the search started. */
	public final IModuleElement refOriginModule;
	/** The offset of the reference. 
	 * Used to check availability in statement scopes. */
	public final int refOffset;
	/** Module Resolver */
	public final ISemanticContext modResolver; // TODO will need to deprecate this field eventually.
	
	/** The scopes that have already been searched */
	protected final HashSet<IScopeElement> searchedScopes = new HashSet<>(4);
	
	/** The member scopes that have already been searched */
	protected final HashSet<INamedElement> searchedMemberScopes = new HashSet<>(4);;
	
	
	public CommonScopeLookup(IModuleElement refOriginModule, int refOffset, ISemanticContext moduleResolver) {
		this(refOriginModule, refOffset, false, moduleResolver);
	}
	
	public CommonScopeLookup(IModuleElement refOriginModule, int refOffset, boolean findOneOnly, 
			ISemanticContext moduleResolver) { 
		this.refOffset = refOffset;
		this.findOnlyOne = findOneOnly;
		this.modResolver = assertNotNull(moduleResolver);
		this.refOriginModule = refOriginModule;
	}
	
	public boolean isSequentialLookup() {
		return refOffset >= 0;
	}
	
	public Set<IScopeElement> getSearchedScopes() {
		return searchedScopes;
	}
	
	/** @return the {@link IModuleElement} of the node or position where this search originates. */
	public IModuleElement getSearchOriginModule() {
		return refOriginModule;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " ---\n" + toString_matches();
	}
	
	public String toString_matches() {
		return StringUtil.iterToString(matches, "\n", new Function<INamedElement, String>() {
			@Override
			public String evaluate(INamedElement obj) {
				return obj.getFullyQualifiedName();
			}
		});
	}
	
	/* -----------------  ----------------- */
	
	public Set<String> findModulesWithPrefix(String fqNamePrefix) {
		return modResolver.findModules(fqNamePrefix);
	}
	
	/* -----------------  ----------------- */
	
	/** Return whether the search has found all matches. */
	public abstract boolean isFinished();
	
	/** 
	 * Evaluate a scope (a collection of nodes), for this name lookup search. 
	 */
	public void evaluateScope(IScopeElement scope) {
		assertNotNull(scope);
		
		if(isFinished())
			return;
		
		if(searchedScopes.contains(scope))
			return;
		
		searchedScopes.add(scope);
		scope.resolveSearchInScope(this);
	}
	
	/* -----------------  ----------------- */
	
	public void evaluateScopeNodeList(Iterable<? extends IASTNode> nodeIterable) {
		evaluateScopeNodeList(nodeIterable, isSequentialLookup());
	}
	
	/* FIXME: need to review this code, possibly remove importsOnly. */
	public void evaluateScopeNodeList(Iterable<? extends IASTNode> nodeIterable, boolean isSequentialLookup) {
		if(nodeIterable != null) {
			evaluateScopeElements(nodeIterable, isSequentialLookup, false);
			evaluateScopeElements(nodeIterable, isSequentialLookup, true);
		}
	}
	
	public void evaluateScopeElements(Iterable<? extends IASTNode> nodeIter, boolean isSequential, 
			boolean importsOnly) {
		
		// Note: don't check for isFinished() during the loop
		for (IASTNode node : nodeIter) {
			
			// Check if we have passed the reference offset
			if(isSequential && refOffset < node.getStartPos()) {
				return;
			}
			
			node.evaluateForScopeLookup(this, importsOnly, isSequential);
		}
	}
	
	public void evaluateScopeElements(Iterable<? extends INamedElement> elementIterable) {
		for (INamedElement namedElement : elementIterable) {
			evaluateNamedElementForSearch(namedElement);
		}
	}
	
	public void evaluateNamedElementForSearch(INamedElement namedElement) {
		if(namedElement != null) {
			visitElement(namedElement);
		}
	}
	
	/* -----------------  ----------------- */
	
	// XXX: perhaps refactor so that normal scopes can be used instead? 
	public void evaluateInMembersScope(INamedElement nameElement) {
		if(isFinished() || nameElement == null)
			return;
		
		IConcreteNamedElement concreteElement = nameElement.resolveConcreteElement(modResolver);
		evaluateInMembersScope(concreteElement);
	}
	
	protected void evaluateInMembersScope(IConcreteNamedElement concreteElement) {
		if(concreteElement == null)
			return;
		
		if(searchedMemberScopes.contains(concreteElement))
			return;
		
		searchedMemberScopes.add(concreteElement);
		
		concreteElement.resolveSearchInMembersScope(this);
	}
	
}