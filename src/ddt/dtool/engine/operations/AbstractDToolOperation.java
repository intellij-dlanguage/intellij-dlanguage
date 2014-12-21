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
package ddt.dtool.engine.operations;

import java.nio.file.Path;

import ddt.melnorme.utilbox.core.CommonException;
import ddt.melnorme.utilbox.misc.Location;
import ddt.dtool.engine.ResolvedModule;
import ddt.dtool.engine.SemanticManager;
import ddt.dtool.engine.compiler_installs.CompilerInstall;

public class AbstractDToolOperation {
	
	protected final SemanticManager semanticManager;
	protected final CompilerInstall compilerInstall;
	protected final Location fileLoc;
	protected final int offset;
	protected final String dubPath;
	
	public AbstractDToolOperation(SemanticManager semanticManager, 
			Path filePath, int offset, Path compilerPath, String dubPath) throws CommonException {
		this.semanticManager = semanticManager;
		
		this.fileLoc = Location.validateLocation(filePath, true, "D module");
		this.offset = offset;
		
		Location compilerLoc = Location.validateLocation(compilerPath, false, "compiler location");
		this.compilerInstall = semanticManager.getDToolServer().findBestCompilerInstall(compilerLoc);
		
		this.dubPath = dubPath; // XXX: should we validate this right now?
	}
	
	public SemanticManager getSemanticManager() {
		return semanticManager;
	}
	
	protected ResolvedModule getResolvedModule(Location filePath) throws CommonException {
		return semanticManager.getUpdatedResolvedModule(filePath, compilerInstall, dubPath);
	}
	
}