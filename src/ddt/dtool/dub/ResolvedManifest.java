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

import java.util.Collections;
import java.util.List;

import ddt.dtool.engine.BundleKey;

/**
 * A {@link ResolvedManifest} has manifest info for a bundle, 
 * plus the same info for all dependencies, organized into a tree.
 */
public class ResolvedManifest {
	
	public final DubBundle bundle;
	public final BundlePath bundlePath;
	public final BundleKey bundleKey;
	
	protected final List<ResolvedManifest> bundleDependencies;
	
	public ResolvedManifest(DubBundle bundle, List<ResolvedManifest> bundleDependencies) {
		this.bundle = assertNotNull(bundle);
		this.bundlePath = assertNotNull(bundle.getBundlePath());
		this.bundleKey = new BundleKey(bundlePath, bundle.getSubpackageSuffix());
		this.bundleDependencies = Collections.unmodifiableList(bundleDependencies);
	}
	
	public DubBundle getBundle() {
		return bundle;
	}
	
	public BundleKey getBundleKey() {
		return bundleKey;
	}
	
	public String getBundleName() {
		return bundle.getBundleName();
	}
	
	public BundlePath getBundlePath() {
		return bundlePath;
	}
	
	public List<ResolvedManifest> getBundleDeps() {
		return bundleDependencies;
	}
	
	@Override
	public String toString() {
		return bundle.getBundleName() + " @" + bundlePath;
	}
	
}