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


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.engine.ErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public abstract class NamedElementSemantics extends ElementSemantics<ConcreteElementResult> {
	
	protected final INamedElement element; 
	
	public NamedElementSemantics(INamedElement element, PickedElement<?> pickedElement) {
		super(pickedElement);
		assertTrue(pickedElement.element == element);		
		this.element = assertNotNull(element);
	}
	
	public ConcreteElementResult resolveConcreteElement() {
		return getElementResolution();
	}
	
	@Override
	protected final ConcreteElementResult createResolution() {
		return new ConcreteElementResult(doResolveConcreteElement());
	}
	
	@Override
	protected ConcreteElementResult createLoopResolution() {
		return new ConcreteElementResult(ErrorElement.newLoopError(element, null));
	}
	
	protected abstract IConcreteNamedElement doResolveConcreteElement();
	
	protected IConcreteNamedElement resolveConcreteElement(INamedElement namedElement) {
		if(namedElement == null) {
			return null;
		}
		return namedElement.resolveConcreteElement(context);
	}
	
	public abstract void resolveSearchInMembersScope(CommonScopeLookup search);
	
	/* TODO: review this API */
	public abstract INamedElement resolveTypeForValueContext();
	
}