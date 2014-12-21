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
package ddt.dtool.engine.compiler_installs;

import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.util.Collections;
import java.util.List;

import ddt.melnorme.utilbox.collections.ArrayList2;
import ddt.melnorme.utilbox.misc.Location;

public class CompilerInstall {
	
	public static enum ECompilerType {
		DMD, GDC, LDC, OTHER
	}
	
	protected final Location compilerPath;
	protected final ECompilerType compilerType;
	protected final List<Location> librarySourceFolders;
	
	
	public CompilerInstall(Location compilerPath, ECompilerType compilerType, Location... librarySourceFolders) {
		this(compilerPath, compilerType, new ArrayList2<>(librarySourceFolders));
	}
	
	public CompilerInstall(Location compilerPath, ECompilerType compilerType, List<Location> librarySourceFolders) {
		this.compilerPath = compilerPath;
		this.compilerType = compilerType;
		this.librarySourceFolders = Collections.unmodifiableList(librarySourceFolders);
	}
	
	public Location getCompilerPath() {
		return compilerPath;
	}
	
	public CompilerInstall.ECompilerType getCompilerType() {
		return compilerType;
	}
	
	public List<Location> getLibrarySourceFolders() {
		return librarySourceFolders;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof CompilerInstall)) return false;
		
		CompilerInstall other = (CompilerInstall) obj;
		
		return areEqual(compilerPath, other.compilerPath) &&
				areEqual(librarySourceFolders, other.librarySourceFolders);
	}
	
	@Override
	public int hashCode() {
		return compilerPath.hashCode();
	}
	
}