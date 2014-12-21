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
import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.nio.file.Path;

import ddt.melnorme.utilbox.misc.HashcodeUtil;
import ddt.dtool.dub.BundlePath;

public final class BundleKey {
	
	protected final BundlePath bundlePath;
	protected final String subPackageSuffix;
	
	public BundleKey(BundlePath bundlePath) {
		this(bundlePath, null);
	}
	
	public BundleKey(BundlePath bundlePath, String subPackageSuffix) {
		this.bundlePath = assertNotNull(bundlePath);
		this.subPackageSuffix = subPackageSuffix;
	}
	
	public Path getPath() {
		return bundlePath.getPath();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof BundleKey)) return false;
		
		BundleKey other = (BundleKey) obj;
		
		return areEqual(bundlePath, other.bundlePath) && areEqual(subPackageSuffix, other.subPackageSuffix) ;
	}
	
	@Override
	public int hashCode() {
		return HashcodeUtil.combineHashCodes(bundlePath, subPackageSuffix);
	}
	
	@Override
	public String toString() {
		return "@" + bundlePath + (subPackageSuffix == null ? "" : (" [:" + subPackageSuffix + "]"));
	}

}