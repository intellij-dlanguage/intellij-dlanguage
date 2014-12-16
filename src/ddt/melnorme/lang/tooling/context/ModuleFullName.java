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

import ddt.melnorme.lang.tooling.symbols.ElementName;
import ddt.melnorme.utilbox.misc.ArrayUtil;

/**
 * A fully qualified name of a module.
 */
public class ModuleFullName extends ElementName {
	
	public static ModuleFullName fromString(String nameString) {
		return nameString == null ? null : new ModuleFullName(nameString);
	}
	
	public ModuleFullName(String moduleFullName) {
		super(moduleFullName);
	}
	
	public ModuleFullName(String[] segments) {
		super(segments);
	}
	
	public String[] getPackages() {
		return ArrayUtil.copyFrom(segments.getInternalArray(), segments.size() - 1);
	}
	
	public String getModuleSimpleName() {
		return getLastSegment();
	}
	
}