/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.engine.intrinsics;

import java.util.ArrayList;

import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.utilbox.misc.CollectionUtil;

public class InstrinsicsScope implements IScopeElement {
	
	public final ArrayList<IntrinsicNamedElement> members;
	
	public InstrinsicsScope(IntrinsicNamedElement... members) {
		this.members = CollectionUtil.createArrayList(members); 
	}
	
	public InstrinsicsScope(ArrayList<IntrinsicNamedElement> members) {
		this.members = members; 
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		// TODO: make nonnull?
		if(members == null) {
			return;
		}
		search.evaluateScopeElements(members);
	}
	
}