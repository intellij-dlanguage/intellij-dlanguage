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
package ddt.melnorme.lang.tooling.engine;

import ddt.melnorme.lang.tooling.ast.ILanguageElement;

public class ElementResolution<E extends ILanguageElement> {
	
	public final E result;
	
	public ElementResolution(E result) {
		this.result = result; // TODO make not null?
	}
	
}