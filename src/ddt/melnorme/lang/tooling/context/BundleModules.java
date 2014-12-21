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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ddt.melnorme.utilbox.misc.Location;
import ddt.dtool.engine.modules.ModuleNamingRules;

public class BundleModules {
	
	// TODO use compile-time unmodifiable interfaces
	public final Map<ModuleFullName, Location> modules;
	public final Set<Location> moduleFiles;
	public final List<Location> importFolders;
	
	/**
	 * Optimized constructor 
	 */
	public BundleModules(HashMap<ModuleFullName, Location> modules, HashSet<Location> moduleFiles, 
			List<Location> importFolders) {
		this.modules = Collections.unmodifiableMap(modules);
		this.moduleFiles = Collections.unmodifiableSet(moduleFiles);
		this.importFolders = Collections.unmodifiableList(new ArrayList<>(importFolders));
	}
	
	public Set<Location> getModuleFiles() {
		return moduleFiles;
	}
	
	public Map<ModuleFullName, Location> getModules() {
		return modules;
	}
	
	public Location getModuleAbsolutePath(ModuleFullName moduleFullName) {
		return modules.get(moduleFullName);
	}
	
	protected void findModules(String fullNamePrefix, HashSet<String> matchedModules) {
		Set<ModuleFullName> moduleEntries = modules.keySet();
		for (ModuleFullName moduleEntry : moduleEntries) {
			String moduleFullName = moduleEntry.getFullNameAsString();
			if(moduleFullName.startsWith(fullNamePrefix)) {
				matchedModules.add(moduleFullName);
			}
		}
	}
	
	public static BundleModules createSyntheticBundleModules(Location filePath) {
		HashMap<ModuleFullName, Location> modules = new HashMap<>();
		HashSet<Location> moduleFiles = new HashSet<>();
		
		moduleFiles.add(filePath);
		ModuleFullName moduleFullName = ModuleNamingRules.getValidModuleNameOrNull(filePath.path.getFileName());
		modules.put(moduleFullName, filePath);
		
		return new BundleModules(modules, moduleFiles, new ArrayList<Location>());
	}
	
}