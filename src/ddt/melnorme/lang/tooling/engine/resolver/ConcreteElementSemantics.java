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
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;


public abstract class ConcreteElementSemantics extends NamedElementSemantics {
	
	protected final ConcreteElementResult elementRes;
	
	public ConcreteElementSemantics(IConcreteNamedElement concreteElement, PickedElement<?> pickedElement) {
		super(concreteElement, pickedElement);
		this.elementRes = new ConcreteElementResult(concreteElement);
	}
	
	@Override
	public final ConcreteElementResult resolveConcreteElement() {
		return elementRes;
	}
	
	@Override
	protected IConcreteNamedElement doResolveConcreteElement() {
		return elementRes.result;
	}
	
	@Override
	protected ConcreteElementResult getOrCreateElementResolution() {
		return elementRes;
	}
	
}