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


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.context.BundleModules;
import ddt.melnorme.utilbox.collections.Indexable;
import ddt.melnorme.utilbox.misc.Location;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.dub.BundlePath;

public class BundleResolution extends AbstractBundleResolution {
	
	protected final ResolutionKey resKey;
	protected final StandardLibraryResolution stdLibResolution;
	
	protected final Indexable<? extends BundleResolution> depResolutions;
	
	public BundleResolution(SemanticManager manager, BundleKey bundleKey, BundleModules bundleModules,
			StandardLibraryResolution stdLibResolution, Indexable<? extends BundleResolution> depResolutions) {
		super(manager, bundleModules);
		this.stdLibResolution = assertNotNull(stdLibResolution);
		this.resKey = bundleKey == null ? null :  
				new ResolutionKey(bundleKey, getStdLibResolution().getCompilerInstall());
		
		this.depResolutions = depResolutions;
	}
	
	public BundleKey getBundleKey() {
		return resKey == null ? null : resKey.bundleKey;
	}
	
	public ResolutionKey getResKey() {
		return resKey;
	}
	
	public BundlePath getBundlePath() {
		return getBundleKey() != null ? getBundleKey().bundlePath : null;
	}
	
	public Indexable<? extends BundleResolution> getDirectDependencies() {
		return depResolutions;
	}
	
	@Override
	public StandardLibraryResolution getStdLibResolution() {
		return stdLibResolution;
	}
	
	public Location getCompilerPath() {
		return getStdLibResolution().getCompilerInstall().getCompilerPath();
	}
	
	@Override
	public String toString() {
		if(resKey == null) {
			return "BundleResolution: [" + StringUtil.collToString(bundleModules.moduleFiles, ":") + "]";
		}
		return "BundleResolution: " + resKey;
	}

	
	/* -----------------  ----------------- */
	
	protected static boolean CHECK_STD_LIB_STALENESS = true;
	
	@Override
	public boolean checkIsStale() {
		if(checkIsModuleListStale() || checkIsModuleContentsStale()) {
			return true;
		}
		
		if(CHECK_STD_LIB_STALENESS && stdLibResolution.checkIsStale()) {
			return true;
		}
		
		for (BundleResolution bundleRes : depResolutions) {
			if(bundleRes.checkIsStale()) {
				return true;
			}
		}
		return false;
	}
	
	/* ----------------- ----------------- */
	
	@Override
	public <E extends Exception> void visitBundleResolutionsAfterStdLib(BundleResolutionVisitor<?, E> visitor) 
			throws E {
		visitor.visit(this);
		if(visitor.isFinished()) {
			return;
		}
		
		for (BundleResolution depBundleRes : depResolutions) {
			depBundleRes.visitBundleResolutionsAfterStdLib(visitor);
			if(visitor.isFinished()) {
				return;
			}
		}
	}
	
}