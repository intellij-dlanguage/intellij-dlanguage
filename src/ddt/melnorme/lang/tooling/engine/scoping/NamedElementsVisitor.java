/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.engine.scoping;

import java.util.HashMap;
import java.util.List;

import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayList2;
import ddt.dtool.ast.declarations.PackageNamespace;

public abstract class NamedElementsVisitor {
	
	protected ArrayList2<INamedElement> matches = new ArrayList2<>(2);
	
	protected HashMap<String, ArrayList2<INamedElement>> matches2 = new HashMap<>(2);
	
	protected boolean matchesArePartialDefUnits = false;
	
	public List<INamedElement> getMatchedElements() {
		return matches;
	}
	
	public void visitElement(INamedElement namedElement) {
		String name = getNameToMatch(namedElement);
		if(name == null || name.isEmpty()) {
			// Never match an element with missing name;
			return;
		}
		if(!matchesName(name)) {
			return;
		}
		
		addMatch(namedElement);
	}
	
	protected String getNameToMatch(INamedElement namedElement) {
		return namedElement.getNameInRegularNamespace();
	}
	
	/** Returns whether this search matches the given name or not. */
	public abstract boolean matchesName(String name);
	
	public void addMatch(INamedElement namedElement) {
		String name = getNameToMatch(namedElement);
		matches.add(namedElement);
		
		addScopedMatch(name, namedElement);
		
		if(namedElement instanceof PackageNamespace) {
			matchesArePartialDefUnits = true;
		}
	}
	
	private void addScopedMatch(String name, INamedElement newMatch) {
		ArrayList2<INamedElement> currentEntry = matches2.get(name);
		if(currentEntry == null) {
			currentEntry = new ArrayList2<>(1);
			matches2.put(name, currentEntry);
		}
		
		if(currentEntry.size() > 0) {
			
		}
		
		currentEntry.add(newMatch);
	}
	
	public HashMap<String, ArrayList2<INamedElement>> getMatches2() {
		return matches2;
	}
	
}