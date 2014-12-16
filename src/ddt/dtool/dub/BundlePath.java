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
package ddt.dtool.dub;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.nio.file.Path;

import ddt.melnorme.utilbox.misc.Location;
import ddt.melnorme.utilbox.misc.MiscUtil;
import ddt.melnorme.utilbox.misc.PathUtil.InvalidPathExceptionX;

/**
 * A valid directory path for a dub bundle.
 * Is normalized, absolute, and has at least one segment 
 */
public class BundlePath {
	
	public static final String DUB_MANIFEST_FILENAME = "dub.json";
	
	public static BundlePath create(String pathStr) {
		try {
			Path path = MiscUtil.createPath(pathStr);
			return BundlePath.create(path);
		} catch (InvalidPathExceptionX e) {
			return null;
		}
	}
	
	public static BundlePath create(Path path) {
		if(isValidBundlePath(path)) {
			return new BundlePath(Location.fromAbsolutePath(path));
		}
		return null;
	}
	
	public static BundlePath create(Location location) {
		if(isValidBundlePath(location.path)) {
			return new BundlePath(location);
		}
		return null;
	}
	
	public static boolean isValidBundlePath(Path path) {
		assertNotNull(path);
		return path.isAbsolute() && path.getNameCount() > 0;
	}
	
	/* -----------------  ----------------- */
	
	public final Location location;
	
	public BundlePath(Location location) {
		assertTrue(BundlePath.isValidBundlePath(location.path));
		this.location = location;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof BundlePath)) return false;
		
		BundlePath other = (BundlePath) obj;
		
		return areEqual(location, other.location);
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	public Path getPath() {
		return location.path;
	}
	
	public Path getManifestFilePath() {
		return getPath().resolve(DUB_MANIFEST_FILENAME);
	}
	
	public Location resolve(Path other) {
		return location.resolve(other);
	}
	
	@Override
	public String toString() {
		return location.toString();
	}
	
	/***
	 * Searches for a manifest file in any of the directories denoted by given path, starting in path. 
	 */
	public static BundlePath findBundleForPath(Location path) {
		if(path == null) {
			return null;
		}
		BundlePath bundlePath = create(path);
		if(bundlePath != null && bundlePath.getManifestFilePath().toFile().exists()) {
			return bundlePath;
		}
		return findBundleForPath(path.getParent());
	}
	
}