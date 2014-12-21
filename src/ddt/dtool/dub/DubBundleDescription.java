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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import ddt.dtool.dub.DubBundle.DubBundleException;
import ddt.dtool.dub.DubBundle.DubDependecyRef;

/**
 * A resolved DUB bundle description. 
 * This is usually derived from running the DUB describe tool, and as such it can be incomplete and have errors.  
 */
public class DubBundleDescription {
	
	protected final boolean isResolved;
	protected final DubBundle mainDubBundle;
	protected final DubBundle[] bundleDependencies; //not null
	protected final DubBundleException error;
	
	/** Constructor for unresolved descriptions. */
	public DubBundleDescription(DubBundle unresolvedBundle) {
		this(unresolvedBundle, EMTPY_BUNDLE_DEPS, false, unresolvedBundle.error);
	}
	
	public DubBundleDescription(DubBundle unresolvedBundle, DubBundleException error) {
		this(unresolvedBundle, EMTPY_BUNDLE_DEPS, false, error);
	}
	
	public DubBundleDescription(DubBundle mainBundle, DubBundle[] bundleDeps) {
		this(mainBundle, bundleDeps, true, findError(mainBundle, bundleDeps));
	}
	
	protected DubBundleDescription(DubBundle mainBundle, DubBundle[] bundleDeps, boolean isResolvedFlag,
			DubBundleException error) {
		
		this.mainDubBundle = assertNotNull(mainBundle);
		this.bundleDependencies = assertNotNull(bundleDeps);
		
		this.error = error;
		this.isResolved = isResolvedFlag && error == null;
	}
	
	protected static DubBundleException findError(DubBundle mainDubBunble, DubBundle[] bundleDependencies) {
		if(mainDubBunble.error != null) {
			return mainDubBunble.error;
		} else {
			for (DubBundle dubBundle : bundleDependencies) {
				if(dubBundle.error != null) {
					return dubBundle.error;
				}
			}
			return null;
		}
	}
	
	protected static final DubBundle[] EMTPY_BUNDLE_DEPS = { };
	
	public DubBundle getMainBundle() {
		return mainDubBundle;
	}
	
	public DubBundle[] getBundleDependencies() {
		return bundleDependencies;
	}
	
	/** A bundle description is considered resolved if dub.json had no errors, and if 
	 * a 'dub describe' output was processed successfully. */
	public boolean isResolved() {
		return isResolved;
	}
	
	public boolean hasErrors() {
		return error != null;
	}
	
	public DubBundleException getError() {
		return error;
	}
	
	/* ----------------- helper to create ResolvedManifest ----------------- */
	
	public static class DubDescribeAnalysis {
		
		protected final HashMap<String, DubBundle> bundlesMap = new HashMap<>();
		protected final HashMap<String, ResolvedManifest> manifests = new HashMap<>();
		protected final HashSet<String> manifestsBeingCalculated = new HashSet<>();
		public final ResolvedManifest mainManifest;
		
		public DubDescribeAnalysis(DubBundleDescription bundleDesc) {
			
			DubBundle[] bundleDeps = bundleDesc.getBundleDependencies();
			for (DubBundle depBundle : bundleDeps) {
				bundlesMap.put(depBundle.getBundleName(), depBundle);
			}
			
			for (DubBundle dubBundle : bundleDeps) {
				calculateResolvedManifest(dubBundle);
			}
			mainManifest = calculateResolvedManifest(bundleDesc.getMainBundle());
		}
		
		public Collection<ResolvedManifest> getAllManifests() {
			return manifests.values();
		}
		
		public ResolvedManifest calculateResolvedManifest(DubBundle bundle) {
			final BundlePath bundlePath = bundle.getBundlePath();
			if(bundlePath == null) {
//				dtoolServer.logError("DUB describe: invalid bundle path: " + bundlePath);
				return null;
			}
			
			final String bundleName = bundle.getBundleName();
			ResolvedManifest manifest = manifests.get(bundleName);
			if(manifest != null) {
				return manifest;
			}
			
			if(manifestsBeingCalculated.contains(bundleName)) {
				// Error: cycle in DUB describe
//				dtoolServer.logError("DUB describe: bundle dependencies cycle detected!");
				return null;
			}
			manifestsBeingCalculated.add(bundleName); // Mark as being created, for cycle checking
			
			ArrayList<ResolvedManifest> directDeps = calculateDirectDependencies(bundle);
			
			manifest = new ResolvedManifest(bundle, directDeps);
			manifests.put(bundleName, manifest);
			return manifest;
		}
		
		protected ArrayList<ResolvedManifest> calculateDirectDependencies(DubBundle bundle) {
			ArrayList<ResolvedManifest> directDeps = new ArrayList<>(bundle.getDependencyRefs().length);
			
			for (DubDependecyRef directDependencyRef : bundle.getDependencyRefs()) {
				String depName = directDependencyRef.getBundleName();
				DubBundle depBundle = bundlesMap.get(depName);
				if(depBundle == null) {
//					dtoolServer.logError("DUB describe: missing dependency: " + depName, null);
					continue;
				}
				ResolvedManifest manifest = calculateResolvedManifest(depBundle);
				if(manifest == null) {
//					dtoolServer.logError("DUB describe: invalid dependency: " + depName, null);
					continue;
				}
				directDeps.add(manifest);
			}
			return directDeps;
		}
		
	}
	
}