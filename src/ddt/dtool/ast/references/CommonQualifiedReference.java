/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.references;


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;

import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.INamedElement;


/**
 * Common class for qualified references 
 * There are two: normal qualified references and Module qualified references.
 */
public abstract class CommonQualifiedReference extends NamedReference implements ITemplateRefNode {
	
	public final RefIdentifier qualifiedId;
	
	public CommonQualifiedReference(RefIdentifier qualifiedId) {
		this.qualifiedId = parentize(assertNotNull(qualifiedId));
	}
	
	/** Return the qualified name (the name reference on the right side). */
	public RefIdentifier getQualifiedName() {
		return qualifiedId;
	}
	
	@Override
	public String getCoreReferenceName() {
		return qualifiedId.getCoreReferenceName();
	}
	
	public abstract int getDotOffset();
	
	public abstract Collection<INamedElement> findRootDefUnits(ISemanticContext moduleResolver);
	
	@Override
	public void performNameLookup(CommonScopeLookup search) {
		performQualifiedRefSearch(search);
	}
	
	public void performQualifiedRefSearch(CommonScopeLookup search) {
		Collection<INamedElement> defunits = findRootDefUnits(search.modResolver);
		// TODO: create new search object here.
		CommonQualifiedReference.resolveSearchInMultipleContainers(defunits, search);
	}
	
	public static void resolveSearchInMultipleContainers(Collection<INamedElement> containers, 
			CommonScopeLookup search) {
		if(containers == null)
			return;
		
		for (INamedElement container : containers) {
			search.evaluateInMembersScope(container);
		}
		
	}
	
}