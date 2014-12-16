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
package ddt.melnorme.lang.tooling.engine.completion;

import java.util.HashSet;
import java.util.Set;

import ddt.melnorme.lang.tooling.ast.IModuleElement;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.completion.CompletionSearchResult.PrefixSearchOptions;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

/** 
 * Class that does a scoped name lookup for matches that start with a given prefix name. 
 * TODO: The matches with the same name as matches in a scope with higher 
 * priority are not added.
 */
public class CompletionScopeLookup extends CommonScopeLookup {
	
	public final PrefixSearchOptions searchOptions;
	protected final Set<String> addedDefElements = new HashSet<>();
	
	public CompletionScopeLookup(IModuleElement refOriginModule, int refOffset, ISemanticContext moduleResolver) {
		this(refOriginModule, refOffset, moduleResolver, new PrefixSearchOptions());
	}
	
	public CompletionScopeLookup(IModuleElement refOriginModule, int refOffset, ISemanticContext moduleResolver, 
			PrefixSearchOptions searchOptions) {
		super(refOriginModule, refOffset, moduleResolver);
		this.searchOptions = searchOptions;
	}
	
	@Override
	public boolean matchesName(String defName) {
		if(searchOptions.searchPrefix.length() > defName.length()) {
			return false;
		}
		String defNamePrefix = defName.substring(0, searchOptions.searchPrefix.length());
		return defNamePrefix.equalsIgnoreCase(searchOptions.searchPrefix);
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void addMatch(INamedElement namedElem) {
		String extendedName = namedElem.getExtendedName();
		
		if(addedDefElements.contains(extendedName)) {
			return;
		}
		addedDefElements.add(extendedName);
		super.addMatch(namedElem);
	}
	
	@Override
	public String toString() {
		String str = getClass().getName() + " ---\n";
		str += "searchPrefix: " + searchOptions.searchPrefix +"\n";
		str += "----- Results: -----\n";
		str += toString_matches();
		return str;
	}
	
}