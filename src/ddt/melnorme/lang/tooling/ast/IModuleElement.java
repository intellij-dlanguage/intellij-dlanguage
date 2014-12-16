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
package ddt.melnorme.lang.tooling.ast;

import java.nio.file.Path;

/**
 * The top level element of an AST.
 */
public interface IModuleElement {
	
	/** @return the compilation unit path for this module. Can be null. */
	Path getCompilationUnitPath();
	
}