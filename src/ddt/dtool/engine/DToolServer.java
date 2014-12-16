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
package ddt.dtool.engine;

import java.nio.file.Path;
import java.text.SimpleDateFormat;

import ddt.melnorme.lang.tooling.engine.completion.CompletionSearchResult;
import ddt.melnorme.utilbox.concurrency.ExecutorTaskAgent;
import ddt.melnorme.utilbox.core.CommonException;
import ddt.melnorme.utilbox.misc.Location;
import ddt.dtool.engine.compiler_installs.CompilerInstall;
import ddt.dtool.engine.compiler_installs.CompilerInstallDetector;
import ddt.dtool.engine.compiler_installs.SearchCompilersOnPathOperation;
import ddt.dtool.engine.operations.CodeCompletionOperation;
import ddt.dtool.engine.operations.FindDefinitionOperation;
import ddt.dtool.engine.operations.FindDefinitionResult;
import ddt.dtool.engine.operations.ResolveDocViewOperation;

public class DToolServer {
	
	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
	
	protected final SemanticManager semanticManager = createSemanticManager();
	
	public DToolServer() {
		logMessage(" ------ DTool engine started ------ ");
	}
	
	protected SemanticManager createSemanticManager() {
		return new SemanticManager(this);
	}
	
	public SemanticManager getSemanticManager() {
		return semanticManager;
	}
	
	protected void shutdown() {
		semanticManager.shutdown();
	}
	
	public void logMessage(String message) {
		System.out.println("> " + message);
	}
	
	public final void logError(String message) {
		logError(message, null);
	}
	public void logError(String message, Throwable throwable) {
		System.out.println("!! " + message);
		if(throwable != null) {
			System.out.println(throwable);
		}
	}
	
	public void handleInternalError(Throwable throwable) {
		logError("!!!! INTERNAL ERRROR: ", throwable);
		throwable.printStackTrace(System.err);
	}
	
	
	/* -----------------  ----------------- */
	
	public class DToolTaskAgent extends ExecutorTaskAgent {
		public DToolTaskAgent(String name) {
			super(name);
		}
		
		@Override
		protected void handleUnexpectedException(Throwable throwable) {
			handleInternalError(throwable);
		}
	}
	
	/* ----------------- Operations ----------------- */
	
	// TODO: compiler path for other operations
	
	public FindDefinitionResult doFindDefinition(Path filePath, int offset, String dubPath) 
			throws CommonException {
		return new FindDefinitionOperation(getSemanticManager(), filePath, offset, null, dubPath).findDefinition();
	}
	
	public String getDDocHTMLView(Path filePath, int offset, String dubPath) 
			throws CommonException {
		return new ResolveDocViewOperation(getSemanticManager(), filePath, offset, null, dubPath).perform();
	}
	
	public CompletionSearchResult doCodeCompletion(Path filePath, int offset, Location compilerPath, String dubPath) 
			throws CommonException {
		Path path = compilerPath == null ? null : compilerPath.path;
		return new CodeCompletionOperation(getSemanticManager(), filePath, offset, path, dubPath).doCodeCompletion();
	}
	
	/* ----------------- helpers ----------------- */
	
	@Deprecated
	public ResolvedModule getUpdatedResolvedModule(Location filePath, String dubPath) throws CommonException {
		CompilerInstall compilerInstall = findBestCompilerInstall(null);
		return getUpdatedResolvedModule(filePath, compilerInstall, dubPath);
	}
	
	public ResolvedModule getUpdatedResolvedModule(Location filePath, CompilerInstall compilerInstall, String dubPath) 
			throws CommonException {
		return semanticManager.getUpdatedResolvedModule(filePath, compilerInstall, dubPath);
	}
	
	public CompilerInstall findBestCompilerInstall(Location compilerPath) {
		CompilerInstall compilerInstall = getCompilerInstallForPath(compilerPath);
		if(compilerInstall == null) {
			SM_SearchCompilersOnPath compilersSearch = new SM_SearchCompilersOnPath();
			compilerInstall = compilersSearch.searchForCompilersInDefaultPathEnvVars().getPreferredInstall();
		}
		return compilerInstall;
	}
	
	public static CompilerInstall getCompilerInstallForPath(Location compilerPath) {
		CompilerInstall compilerInstall = null;
		if(compilerPath != null) {
			return new CompilerInstallDetector().detectInstallFromCompilerCommandPath(compilerPath);
		}
		return compilerInstall;
	}
	
	protected class SM_SearchCompilersOnPath extends SearchCompilersOnPathOperation {
		@Override
		protected void handleWarning(String message) {
			DToolServer.this.logMessage(message);
		}
	}
	
}