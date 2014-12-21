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
package ddt.dtool.ast.references;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.engine.resolver.IResolvable;
import ddt.dtool.ast.expressions.Resolvable;

/** Marker interface for nodes that can appear as qualifier in {@link RefQualified}. 
 * Must be a {@link Resolvable}. */
public interface IQualifierNode extends IResolvable, IASTNode { }