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

import java.io.File;

import ddt.melnorme.utilbox.misc.Location;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.engine.compiler_installs.CompilerInstall.ECompilerType;

public class CompilerInstallDetector {
	
	public CompilerInstallDetector() {
	}
	
	public CompilerInstall detectInstallFromCompilerCommandPath(Location commandPath) {
		String fileName = commandPath.path.getFileName().toString();
		
		if(executableMatches(fileName, "dmd")) {
			return detectDMDInstall(commandPath);
		} else if(executableMatches(fileName, "gdc")) {
			return detectGDCInstall(commandPath);
		} else if(executableMatches(fileName, "ldc2") || executableMatches(fileName, "ldc")) {
			return detectLDCInstall(commandPath);
		}
		
		return null;
	}
	
	protected CompilerInstall detectDMDInstall(Location commandPath) {
		Location cmdDir = commandPath.getParent();
		
		if(cmdDir.resolve_fromValid("../../src/druntime").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.DMD, 
				cmdDir.resolve_fromValid("../../src/druntime/import"),
				cmdDir.resolve_fromValid("../../src/phobos"));
		}
		// a MacOSX layout:
		if(cmdDir.resolve_fromValid("../src/druntime").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.DMD, 
				cmdDir.resolve_fromValid("../src/druntime/import"),
				cmdDir.resolve_fromValid("../src/phobos"));
		}
		// another MacOSX layout
		Location resolvedCmdPath = cmdDir.resolve_fromValid("../share/dmd/bin/dmd");
		if(resolvedCmdPath.toFile().exists()) {
			Location resolvedCmdDir = resolvedCmdPath.getParent();
			if(resolvedCmdDir.resolve_fromValid("../src/druntime").toFile().exists()) {
				return new CompilerInstall(resolvedCmdPath, ECompilerType.DMD, 
					resolvedCmdDir.resolve_fromValid("../src/druntime/import"),
					resolvedCmdDir.resolve_fromValid("../src/phobos"));
			}
		}
		
		if(cmdDir.resolve_fromValid("../include/dlang/dmd").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.DMD, 
				cmdDir.resolve_fromValid("../include/dlang/dmd"));
		}
		
		if(cmdDir.resolve_fromValid("../include/dmd").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.DMD, 
				cmdDir.resolve_fromValid("../include/dmd/druntime/import"),
				cmdDir.resolve_fromValid("../include/dmd/phobos"));
		}
		
		if(cmdDir.resolve_fromValid("../../include/d/dmd").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.DMD, 
				cmdDir.resolve_fromValid("../../include/d/dmd/druntime/import"),
				cmdDir.resolve_fromValid("../../include/d/dmd/phobos"));
		}
		return null;
	}
	
	protected CompilerInstall detectLDCInstall(Location commandPath) {
		Location cmdDir = commandPath.getParent();
		
		if(cmdDir.resolve_fromValid("../include/dlang/ldc").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.LDC, 
				cmdDir.resolve_fromValid("../include/dlang/ldc"));
		}
		
		if(cmdDir.resolve_fromValid("../import/core").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.LDC,
				cmdDir.resolve_fromValid("../import/ldc"),
				cmdDir.resolve_fromValid("../import"));
		}
		return null;
	}
	
	protected CompilerInstall detectGDCInstall(Location commandPath) {
		Location cmdDir = commandPath.getParent();
		
		if(cmdDir.resolve_fromValid("../include/dlang/gdc").toFile().exists()) {
			return new CompilerInstall(commandPath, ECompilerType.GDC, 
				cmdDir.resolve_fromValid("../include/dlang/gdc"));
		}
		
		CompilerInstall install = checkGDCLibrariesAt(cmdDir.resolve_fromValid("../include/d"), commandPath);
		if(install != null) 
			return install;
		
		return checkGDCLibrariesAt(cmdDir.resolve_fromValid("../include/d2"), commandPath);
	}
	
	protected CompilerInstall checkGDCLibrariesAt(Location includeD2Dir, Location commandPath) {
		if(includeD2Dir.toFile().exists()) {
			
			File[] d2entries = includeD2Dir.toFile().listFiles();
			if(d2entries == null) // Same as IOException
				return null;
			
			for (File d2entry : d2entries) {
				if(d2entry.isDirectory() && new File(d2entry, "object.di").exists()) {
					return new CompilerInstall(commandPath, ECompilerType.GDC, 
						Location.create_fromValid(d2entry.toPath())
					);
				}
			}
			
		}
		return null;
	}
	
	protected boolean executableMatches(String fileName, String executableName) {
		fileName = StringUtil.trimEnd(fileName, ".exe");
		return fileName.equals(executableName);
	}
	
}