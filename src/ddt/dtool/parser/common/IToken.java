/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.parser.common;

import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.parser.ISourceRepresentation;
import ddt.dtool.parser.DeeTokens;

public interface IToken extends ISourceRepresentation {
	
	DeeTokens getType();
	
	@Override
	String getSourceValue();
	
	int getStartPos();
	int getEndPos();
	SourceRange getSourceRange();
	
}