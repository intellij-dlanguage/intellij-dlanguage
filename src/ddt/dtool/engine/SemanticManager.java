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

import static ddt.dtool.engine.DToolServer.TIMESTAMP_FORMAT;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;

import ddt.melnorme.lang.tooling.context.BundleModules;
import ddt.melnorme.lang.tooling.context.ModuleSourceException;
import ddt.melnorme.utilbox.collections.ArrayList2;
import ddt.melnorme.utilbox.concurrency.ITaskAgent;
import ddt.melnorme.utilbox.core.CommonException;
import ddt.melnorme.utilbox.misc.Location;
import ddt.dtool.dub.BundlePath;
import ddt.dtool.dub.DubBundleDescription;
import ddt.dtool.dub.DubBundleDescription.DubDescribeAnalysis;
import ddt.dtool.dub.DubHelper.RunDubDescribeCallable;
import ddt.dtool.dub.ResolvedManifest;
import ddt.dtool.engine.StandardLibraryResolution.MissingStandardLibraryResolution;
import ddt.dtool.engine.compiler_installs.CompilerInstall;
import ddt.dtool.engine.modules.BundleModulesVisitor;
import ddt.dtool.engine.util.CachingRegistry;
import ddt.dtool.engine.util.FileCachingEntry;
import ddt.dtool.parser.DeeParserResult.ParsedModule;

class BundleResolutionEntry {
	
	protected volatile BundleResolution bundleResolution;
	
	public BundleResolutionEntry() {
	}
	
	public BundleResolution getSemanticResolution() {
		return bundleResolution;
	}
	
}

/**
 * Maintains a registry of parsed bundle manifests, indexed by bundle path.
 */
public class SemanticManager {
	
	protected final DToolServer dtoolServer;
	protected final ITaskAgent dubProcessAgent;
	protected final ModuleParseCache parseCache;
	
	protected final ManifestsManager manifestManager = new ManifestsManager();
	protected final ResolutionsManager resolutionsManager = new ResolutionsManager();
	
	
	protected SemanticManager(DToolServer dtoolServer) {
		this.dtoolServer = dtoolServer;
		this.parseCache = new ModuleParseCache(dtoolServer);
		this.dubProcessAgent = dtoolServer.new DToolTaskAgent("DToolServer.DubProcessAgent");
	}
	
	public void shutdown() {
		dubProcessAgent.shutdownNow();
	}
	
	public ModuleParseCache getParseCache() {
		return parseCache;
	}
	
	public DToolServer getDToolServer() {
		return dtoolServer;
	}
	
	/* ----------------- Manifest Registry ----------------- */
	
	public static class ManifestUpdateOptions {
		
		public final String dubPath; // Can be null

		public ManifestUpdateOptions(String dubPath) {
			this.dubPath = dubPath;
		}
		
	}
	
	public final ResolvedManifest getStoredManifest(BundleKey bundleKey) {
		return manifestManager.getEntryManifest(bundleKey);
	}
	public final boolean checkIsManifestStale(BundleKey bundleKey) {
		return manifestManager.checkIsEntryStale(bundleKey);
	}
	public ResolvedManifest getUpdatedManifest(BundleKey bundleKey, ManifestUpdateOptions options) 
			throws CommonException {
		return manifestManager.getUpdatedManifest(bundleKey, options);
	}
	
	public class ManifestsManager extends 
		AbstractCachingManager<BundleKey, FileCachingEntry<ResolvedManifest>, ManifestUpdateOptions> 
	{
		
		@Override
		protected FileCachingEntry<ResolvedManifest> doCreateEntry(BundleKey bundleKey) {
			return new FileCachingEntry<>(bundleKey.getPath());
		}
		
		public ResolvedManifest getEntryManifest(BundleKey bundleKey) {
			FileCachingEntry<ResolvedManifest> entry = infos.getEntryOrNull(bundleKey);
			return getManifestFromEntry(entry);
		}
		
		protected ResolvedManifest getManifestFromEntry(FileCachingEntry<ResolvedManifest> entry) {
			return entry != null ? entry.getValue() : null;
		}
		
		public ResolvedManifest getUpdatedManifest(BundleKey bundleKey, ManifestUpdateOptions options) 
				throws CommonException {
			return getUpdatedEntry(bundleKey, options).getValue();
		}
		
		@Override
		public boolean doCheckIsEntryStale(BundleKey key, FileCachingEntry<ResolvedManifest> entry) {
			if(entry.isStale()) {
				return true;
			}
			
			synchronized(entriesLock) {
				
				for(ResolvedManifest depBundle : entry.getValue().getBundleDeps()) {
					BundleKey depKey = depBundle.getBundleKey();
					FileCachingEntry<ResolvedManifest> depBundleEntry = getEntry(depKey);
					
					if(depBundle != depBundleEntry.getValue()) {
						// The manifest of the dependency is not stale, 
						// but it has changed since the parent was created, therefore parent is stale.
						return true;
					}
					if(doCheckIsEntryStale(depKey, depBundleEntry)) {
						return true;
					}
				}
				return false;
			}
		}
		
		@Override
		protected void doUpdateEntry(BundleKey key, FileCachingEntry<ResolvedManifest> staleInfo, 
				ManifestUpdateOptions options) throws CommonException {
			assertNotNull(options);
			
			RunDubDescribeCallable dubDescribeTask = new RunDubDescribeCallable(key.bundlePath, options.dubPath, 
				false);
			DubBundleDescription bundleDesc = dubDescribeTask.submitAndGet(dubProcessAgent);
			
			FileTime dubStartTimeStamp = dubDescribeTask.getStartTimeStamp();
			DubDescribeAnalysis dubDescribeAnalyzer = new DubDescribeAnalysis(bundleDesc);
			
			setNewManifestEntry(dubStartTimeStamp, dubDescribeAnalyzer);
		}
		
		protected void setNewManifestEntry(FileTime dubStartTimeStamp, DubDescribeAnalysis dubDescribeAnalyzer) {
			synchronized(entriesLock) {
				
				StringBuilder sb = new StringBuilder();
				
				sb.append(" Completed `dub describe`, resolved new manifests");
				sb.append(" (timestamp: " + TIMESTAMP_FORMAT.format(new Date(dubStartTimeStamp.toMillis())) + ")");
				sb.append(" : \n");
				
				for(ResolvedManifest newManifestValue : dubDescribeAnalyzer.getAllManifests()) {
					sb.append(" Bundle:  " + String.format("%-25s", newManifestValue.getBundleName())  
						+ "  @ " + newManifestValue.bundlePath + "\n");
					
					BundleKey bundleKey = newManifestValue.getBundleKey();
					// We cap the maximum timestamp because DUB describe is only guaranteed to have read the 
					// manifest files up to dubStartTimeStamp
					getEntry(bundleKey).updateValue(newManifestValue, dubStartTimeStamp);
				}
				sb.append("---");
				dtoolServer.logMessage(sb.toString());
			}
		}
	
	};
	
	/* ----------------- StandardLib Resolution resolution ----------------- */
	
	protected StandardLibraryResolution getUpdatedStdLibResolution(CompilerInstall foundInstall) {
		assertNotNull(foundInstall);
		return stdLibResolutions.getEntry(foundInstall);
	}
	
	protected final StdLibResolutionsCache stdLibResolutions = new StdLibResolutionsCache();
	
	protected class StdLibResolutionsCache extends CachingRegistry<CompilerInstall, StandardLibraryResolution> {
		@Override
		public synchronized StandardLibraryResolution getEntry(CompilerInstall key) {
			if(key == null) {
				key = MissingStandardLibraryResolution.NULL_COMPILER_INSTALL;
			}
			StandardLibraryResolution entry = map.get(key);
			if(entry == null || entry.checkIsStale()) {
				entry = createEntry(key);
				map.put(key, entry);
			}
			return entry;
		}
		
		@Override
		protected StandardLibraryResolution createEntry(CompilerInstall compilerInstall) {
			if(compilerInstall == MissingStandardLibraryResolution.NULL_COMPILER_INSTALL) {
				return new MissingStandardLibraryResolution(SemanticManager.this);
			}
			return new StandardLibraryResolution(SemanticManager.this, compilerInstall);
		}
	}
	
	/* ----------------- Semantic Resolution registry ----------------- */
	
	public BundleResolution getStoredResolution(ResolutionKey resKey) {
		BundleResolutionEntry info = resolutionsManager.getEntry(resKey);
		return info != null ? info.getSemanticResolution() : null;
	}
	public boolean checkIsResolutionStale(ResolutionKey resKey) {
		return resolutionsManager.checkIsEntryStale(resKey);
	}
	public BundleResolution getUpdatedResolution(ResolutionKey resKey, ManifestUpdateOptions options) 
			throws CommonException {
		assertNotNull(options);
		return resolutionsManager.getUpdatedEntry(resKey, options).getSemanticResolution();
	}
	
	public class ResolutionsManager 
		extends AbstractCachingManager<ResolutionKey, BundleResolutionEntry, ManifestUpdateOptions> 
	{
		@Override
		protected BundleResolutionEntry doCreateEntry(ResolutionKey key) {
			return new BundleResolutionEntry();
		}
		
		@Override
		public boolean doCheckIsEntryStale(ResolutionKey key, BundleResolutionEntry entry) {
			return
					entry.getSemanticResolution() == null ||
					checkIsManifestStale(key.bundleKey) ||
					entry.getSemanticResolution().checkIsStale();
		};
		
		@Override
		protected void doUpdateEntry(ResolutionKey resKey, BundleResolutionEntry staleInfo, 
				ManifestUpdateOptions options) throws CommonException {
			ResolvedManifest manifest = manifestManager.getUpdatedManifest(resKey.bundleKey, options);
			StandardLibraryResolution stdLibResolution = getUpdatedStdLibResolution(resKey.compilerInstall);
			
			BundleResolution bundleRes = new DubBundleResolution(SemanticManager.this, manifest, stdLibResolution);
			
			setNewBundleResolutionEntry(bundleRes);
		}
		
		protected BundleResolutionEntry setNewBundleResolutionEntry(BundleResolution bundleRes) {
			synchronized(entriesLock) {
				for(BundleResolution newDepBundleRes : bundleRes.getDirectDependencies()) {
					setNewBundleResolutionEntry(newDepBundleRes);
				}
				BundleResolutionEntry newInfo = getEntry(bundleRes.getResKey());
				newInfo.bundleResolution = bundleRes;
				return newInfo;
			}
		}
	
	}
	
	/* ----------------- helper ----------------- */
	
	public BundleModules createBundleModules(List<Location> importFolders) {
		return new SM_BundleModulesVisitor(importFolders).toBundleModules();
	}
	
	protected class SM_BundleModulesVisitor extends BundleModulesVisitor {
		public SM_BundleModulesVisitor(List<Location> importFolders) {
			super(importFolders);
		}
		
		@Override
		protected FileVisitResult handleFileVisitException(Path file, IOException exc) {
			dtoolServer.logError("Error visiting directory/file path: " + file, exc);
			return FileVisitResult.CONTINUE;
		}
	}
	
	/* ----------------- Working Copy and module resolution ----------------- */
	
	public ParsedModule setWorkingCopyAndParse(Path filePath, String source) {
		return parseCache.setWorkingCopyAndGetParsedModule(filePath, source);
	}
	
	public void discardWorkingCopy(Path filePath) {
		parseCache.discardWorkingCopy(filePath);
	}
	
	public ResolvedModule getUpdatedResolvedModule(Location filePath, CompilerInstall compilerInstall, String dubPath)
			throws CommonException {
		BundlePath bundlePath = BundlePath.findBundleForPath(filePath);
		
		if(compilerInstall == null) {
			compilerInstall =  MissingStandardLibraryResolution.NULL_COMPILER_INSTALL;
		}
		ManifestUpdateOptions options = new ManifestUpdateOptions(dubPath);
		
		try {
			ResolvedModule resolvedModule;
			StandardLibraryResolution stdLibResolution;
			if(bundlePath == null) {
				stdLibResolution = getUpdatedStdLibResolution(compilerInstall);
				resolvedModule = stdLibResolution.getBundleResolvedModule(filePath);
			} else {
				ResolutionKey resKey = new ResolutionKey(new BundleKey(bundlePath), compilerInstall);
				BundleResolution bundleRes = getUpdatedResolution(resKey, options);
				stdLibResolution = bundleRes.getStdLibResolution();
				resolvedModule = bundleRes.getBundleResolvedModule(filePath);
			}
			
			if(resolvedModule != null) {
				return resolvedModule;
			}
			return createSyntheticBundle(filePath, stdLibResolution);
		} catch (ModuleSourceException e) {
			throw new CommonException("Error parsing module source: ", e);
		}
	}
	
	protected ResolvedModule createSyntheticBundle(Location filePath, StandardLibraryResolution stdLibResolution) 
			throws ModuleSourceException {
		BundleModules bundleModules = BundleModules.createSyntheticBundleModules(filePath);
		BundleResolution syntheticBR = new BundleResolution(this, null, bundleModules, stdLibResolution,
			new ArrayList2<BundleResolution>());
		ResolvedModule resolvedModule = syntheticBR.getBundleResolvedModule(filePath);
		return assertNotNull(resolvedModule);
	}
	
}