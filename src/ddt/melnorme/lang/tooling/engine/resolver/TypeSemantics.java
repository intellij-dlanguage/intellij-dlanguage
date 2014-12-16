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

import ddt.melnorme.lang.tooling.engine.NotAValueErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public abstract class TypeSemantics extends ConcreteElementSemantics {
	
	protected final NotAValueErrorElement notAValueError;
	
	public TypeSemantics(IConcreteNamedElement typeElement, PickedElement<?> pickedElement) {
		super(typeElement, pickedElement);
		this.notAValueError = new NotAValueErrorElement(typeElement);
	}
	
	protected final IConcreteNamedElement getTypeElement() {
		return elementRes.result;
	}
	
	@Override
	public final INamedElement resolveTypeForValueContext() {
		return notAValueError;
	}
	
}