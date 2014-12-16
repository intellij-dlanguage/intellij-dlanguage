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
package ddt.melnorme.lang.tooling.context;


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ddt.melnorme.lang.tooling.ast.CommonLanguageElement;
import ddt.melnorme.lang.tooling.ast.ILanguageElement;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.utils.EntryMap;
import ddt.melnorme.utilbox.misc.Location;

public abstract class AbstractSemanticContext implements ISemanticContext {
	
	protected final BundleModules bundleModules;
	
	public AbstractSemanticContext(BundleModules bundleModules) {
		this.bundleModules = assertNotNull(bundleModules);
	}
	
	public Map<ModuleFullName, Location> getBundleModulesMap() {
		return bundleModules.modules;
	}
	
	public Set<Location> getBundleModuleFiles() {
		return bundleModules.moduleFiles;
	}
	
	public boolean bundleContainsModule(Location path) {
		return bundleModules.moduleFiles.contains(path);
	}
	
	/** @return the absolute path of a module contained in this bundle resolution, or null if not found. */
	protected Location getBundleModulePath(ModuleFullName moduleFullName) {
		return bundleModules.getModuleAbsolutePath(moduleFullName);
	}
	
	@Override
	public HashSet<String> findModules(String fullNamePrefix) {
		HashSet<String> matchedModules = new HashSet<String>();
		findModules(fullNamePrefix, matchedModules);
		return matchedModules;
	}
	
	protected void findModules(String fullNamePrefix, HashSet<String> matchedModules) {
		findBundleModules(fullNamePrefix, matchedModules);
	}
	
	protected void findBundleModules(String fullNamePrefix, HashSet<String> matchedModules) {
		bundleModules.findModules(fullNamePrefix, matchedModules);
	}
	
	/* ----------------- NodeSemantics ----------------- */
	
	@Override
	public ISemanticContext findSemanticContext(ILanguageElement element) {
		return this; // subclasses must reimplement, if appropriate
	}
	
	protected final SemanticsMap semanticsMap = new SemanticsMap();
	
	public class SemanticsMap extends EntryMap<CommonLanguageElement, ElementSemantics<?>> {
		
		@Override
		protected ElementSemantics<?> createEntry(CommonLanguageElement key) {
			return key.createSemantics(new PickedElement<>(key, AbstractSemanticContext.this));
		}
		
	}
	
	@Override
	public final ElementSemantics<?> getSemanticsEntry(CommonLanguageElement element) {
		assertTrue(findSemanticContext(element) == this);
		return semanticsMap.getEntry(element);
	}
	
}