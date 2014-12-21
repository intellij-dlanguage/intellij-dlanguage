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


import ddt.melnorme.lang.tooling.AbstractElementName2;

/**
 * A qualified element name.
 */
public class ElementName extends AbstractElementName2 {
	
	public static final String NAME_SEP = ".";
	
	public ElementName(String fullName) {
		super(fullName, NAME_SEP);
	}
	
	/** Note: the new class will own segments array, it should not be modified. */
	public ElementName(String[] segments) {
		super(segments, NAME_SEP);
	}
	
}