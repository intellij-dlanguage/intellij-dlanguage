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
package ddt.melnorme.lang.tooling.context;

import java.util.Set;

import ddt.melnorme.lang.tooling.ast.CommonLanguageElement;
import ddt.melnorme.lang.tooling.ast.ILanguageElement;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

/** 
 * An interface for a service that given module names knows how to find parsed Module's in an 
 * underlying context (for example the modules in the buildpath of a project). 
 */
public interface ISemanticContext {
	
	/** Searches for the names of modules whose fully qualified names start with the given fqNamePrefix.
	 * @return a set with the results. */
	Set<String> findModules(String fullNamePrefix);
	
	/** Finds a module with the given fully qualified name.
	 * @param packages The packages of the module to find.
	 * @param module The name of the modules to find. 
	 * @return the respective module or null if not found
	 */
	INamedElement findModule(ModuleFullName moduleName) throws ModuleSourceException;
	
	
	/** @return the {@link ISemanticContext} appropriate for the given element. */
	ISemanticContext findSemanticContext(ILanguageElement element);
	
	/**
	 * @return The element semantics for the given element.
	 * Note that: <code>assertTrue(findSemanticContext(element) == this);</code> 
	 */
	ElementSemantics<?> getSemanticsEntry(CommonLanguageElement element);
	
}