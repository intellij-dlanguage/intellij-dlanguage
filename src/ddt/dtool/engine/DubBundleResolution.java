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

import ddt.melnorme.utilbox.collections.ArrayList2;
import ddt.melnorme.utilbox.collections.Indexable;
import ddt.dtool.dub.DubBundle;
import ddt.dtool.dub.ResolvedManifest;

public class DubBundleResolution extends BundleResolution {
	
	protected final ResolvedManifest manifest;
	protected final DubBundle dubBundle;
	
	public DubBundleResolution(SemanticManager manager, ResolvedManifest manifest, 
			StandardLibraryResolution stdLibResolution) {
		super(manager, 
			manifest.getBundleKey(),
			manager.createBundleModules(manifest.getBundle().getEffectiveImportFolders_AbsolutePath()),
			stdLibResolution,
			createDepSRs(manager, manifest, stdLibResolution)
		);
		this.manifest = manifest;
		this.dubBundle = manifest.getBundle();
	}
	
	protected static Indexable<BundleResolution> createDepSRs(SemanticManager manager, 
		ResolvedManifest manifest, 
		StandardLibraryResolution stdLibResolution) {
		ArrayList2<BundleResolution> depSRs = new ArrayList2<>();
		for (ResolvedManifest depManifest : manifest.getBundleDeps()) {
			/*FIXME: BUG here replace dep bundle resolutions?*/
			depSRs.add(new DubBundleResolution(manager, depManifest, stdLibResolution));
		}
		return depSRs;
	}
	
}