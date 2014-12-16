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
package ddt.melnorme.lang.tooling.symbols;

import ddt.melnorme.lang.tooling.context.ISemanticContext;

/**
 * A concrete named element is named element that is not an alias to another element.
 */
public interface IConcreteNamedElement extends INamedElement {
	
	/** @return the receiver, since it is a {@link IConcreteNamedElement}. */
	@Override
	public IConcreteNamedElement resolveConcreteElement(ISemanticContext sr);
	
}