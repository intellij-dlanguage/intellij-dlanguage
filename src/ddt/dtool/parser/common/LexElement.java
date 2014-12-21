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

import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.dtool.parser.DeeTokens;


public class LexElement extends BaseLexElement {
	
	public LexElement(DeeTokens type, String source, int startPos, int fullStartPos, Token[] ignoredPrecedingTokens) {
		super(type, source, startPos, fullStartPos, ignoredPrecedingTokens);
	}
	
	@Override
	public final boolean isMissingElement() {
		return false;
	}
	
	@Override
	public ParserError getMissingError() {
		return null;
	}
	
	public final static class MissingLexElement extends BaseLexElement {
		
		public ParserError error;
		
		public MissingLexElement(int lookAheadStart, ParserError error, int laFullStartPos,
			Token[] ignoredPrecedingTokens) {
			super(DeeTokens.WHITESPACE, "", lookAheadStart, laFullStartPos, ignoredPrecedingTokens);
			this.error = error; // can be null
		}
		
		@Override
		public final boolean isMissingElement() {
			return true;
		}
		
		@Override
		public ParserError getMissingError() {
			return error;
		}
		
		@Override
		public String toString() {
			return super.toString() + "◙";
		}
		
	}
	
}