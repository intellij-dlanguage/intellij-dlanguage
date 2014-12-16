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
package ddt.melnorme.lang.tooling.engine.resolver;

import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

/**
 * Common class for un-implemented NamedElementSemantics functionality.
 */
public class TODO_NamedElementSemantics extends NamedElementSemantics {
	
	public TODO_NamedElementSemantics(INamedElement element, PickedElement<?> pickedElement) {
		super(element, pickedElement);
	}
	
	@Override
	protected IConcreteNamedElement doResolveConcreteElement() {
		return null; // TODO
	}
	
	@Override
	public INamedElement resolveTypeForValueContext() {
		return null; // TODO
	}
	
	@Override
	public void resolveSearchInMembersScope(CommonScopeLookup search) {
	}
	
}