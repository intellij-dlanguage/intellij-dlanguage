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
package ddt.dtool.engine.analysis;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.references.Reference;

/**
 * Interface for nodes similar to a variable definition (basically defUnits that have an associated type).
 */
public interface IVarDefinitionLike extends INamedElement, IASTNode, IConcreteNamedElement {
	
	Reference getDeclaredType();
	
	IInitializer getDeclaredInitializer();
	
}