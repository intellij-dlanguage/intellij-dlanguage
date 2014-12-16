/*******************************************************************************
 * Copyright (c) 2013, 2013 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.dub;

import static java.util.Collections.unmodifiableList;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.misc.ArrayUtil.nullToEmpty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ddt.melnorme.utilbox.misc.CollectionUtil;
import ddt.melnorme.utilbox.misc.Location;
import ddt.melnorme.utilbox.misc.MiscUtil;
import ddt.melnorme.utilbox.misc.StringUtil;

public class DubBundle {
	
	public static final String DEFAULT_VERSION = "~master";
	
	public final String name; // not null
	
	// bundlePath is the bundle's location in the filesystem. Can be null if path is invalid or not specified.
	protected final BundlePath bundlePath;
	
	public final DubBundleException error;
	
	public final String version;
	public final String[] srcFolders;
	public final Path[] effectiveSourceFolders;
	public final List<BundleFile> bundleFiles;
	
	public final DubDependecyRef[] dependencies; // not null
	public final String targetName;
	public final String targetPath;
	
	public DubBundle(
			BundlePath bundlePath, 
			String name, 
			DubBundleException error, 
			String version, 
			String[] srcFolders,
			Path[] effectiveSrcFolders, 
			List<BundleFile> bundleFiles,
			DubDependecyRef[] dependencies, 
			String targetName, 
			String targetPath) {
		this.bundlePath = bundlePath;
		this.name = assertNotNull(name);
		this.error = error;
		
		this.version = version == null ? DEFAULT_VERSION : version;
		this.srcFolders = srcFolders;
		this.effectiveSourceFolders = nullToEmpty(effectiveSrcFolders, Path.class);
		this.dependencies = nullToEmpty(dependencies, DubDependecyRef.class);
		this.bundleFiles = unmodifiableList(CollectionUtil.nullToEmpty(bundleFiles));
		this.targetName = targetName;
		this.targetPath = targetPath;
		
		if(!hasErrors()) {
			assertTrue(bundlePath != null);
		}
	}
	
	public DubBundle(BundlePath bundlePath, String name, DubBundleException error) {
		this(bundlePath, name, error, null, null, null, null, null, null, null);
	}
	
	/** @return the bundle name, not null. */
	public String getBundleName() {
		return name;
	}
	
	/** @return the simple name of this child bundle, if it is a child bundle. null otherwise. */
	public String getSubpackageSuffix() {
		return StringUtil.segmentAfterMatch(getBundleName(), ":");
	}
	
	/** @return the bundlePath. Can be null. */
	public BundlePath getBundlePath() {
		return bundlePath;
	}
	
	public Path getLocationPath() {
		return bundlePath == null ? null : bundlePath.getPath();
	}
	
	public Location getLocation() {
		return bundlePath == null ? null : bundlePath.location;
	}
	
	public String getLocationString() {
		return getLocation() == null ? "[null]" : getLocation().toString();
	}
	
	public boolean hasErrors() {
		return error != null;
	}
	
	public String[] getDefinedSourceFolders() {
		return srcFolders;
	}
	
	public Path[] getEffectiveSourceFolders() {
		return assertNotNull(effectiveSourceFolders);
	}
	
	public Path[] getEffectiveImportFolders() {
		return assertNotNull(effectiveSourceFolders);
	}
	
	public static class BundleFile {
		
		public final String filePath;
		public final boolean importOnly;
		
		public BundleFile(String filePath, boolean importOnly) {
			this.filePath = assertNotNull(filePath);
			this.importOnly = importOnly;
		}
		
	}
	
	public DubDependecyRef[] getDependencyRefs() {
		return dependencies;
	}
	
	public String getTargetName() {
		return targetName;
	}
	
	public String getTargetPath() {
		return targetPath;
	}
	
	public String getEffectiveTargetName() {
		String baseName = targetName != null ? targetName : name;
		return baseName + getExecutableSuffix();
	}
	
	protected static String getExecutableSuffix() {
		return MiscUtil.OS_IS_WINDOWS ? ".exe" : "";
	}
	
	public Path getEffectiveTargetFullPath() {
		Path path = MiscUtil.createPathOrNull(getTargetPath() == null ? "" : getTargetPath());
		if(path == null) {
			path = Paths.get("");
		}
		return path.resolve(getEffectiveTargetName());
	}
	
	@Override
	public String toString() {
		return name + " ("+version+") @" + (bundlePath == null ? "<null>" : bundlePath);
	}
	
	public static class DubDependecyRef {
		
		public final String bundleName;
		public final String version; // not implemented yet, not really important.
		
		public DubDependecyRef(String bundleName, String version) {
			this.bundleName = assertNotNull(bundleName);
			this.version = version;
		}
		
		public String getBundleName() {
			return bundleName;
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class DubBundleException extends Exception {
		
	    public DubBundleException(String message, Throwable cause) {
	        super(message, cause);
	    }
		
		public DubBundleException(String message) {
	        super(message);
	    }
		
		public DubBundleException(Throwable exception) {
	        super(exception);
	    }
		
		@Override
		public String getMessage() {
			return super.getMessage();
		}
		
	}
	
	/* ----------------- utilities ----------------- */
	
	public ArrayList<Location> getEffectiveImportFolders_AbsolutePath() {
		assertTrue(bundlePath != null);
		
		ArrayList<Location> importFolders = new ArrayList<>(effectiveSourceFolders.length);
		for (Path srcFolder_relative : effectiveSourceFolders) {
			importFolders.add(bundlePath.resolve(srcFolder_relative));
		}
		return importFolders;
	}
	
	public Path relativizePathToImportFolder(Path path) {
		ArrayList<Location> importFolders = getEffectiveImportFolders_AbsolutePath();
		for(Location importFolder : importFolders) {
			if(path.startsWith(importFolder.path)) {
				return importFolder.path.relativize(path);
			}
		}
		return null;
	}
	
}