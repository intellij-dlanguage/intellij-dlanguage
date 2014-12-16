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
package ddt.dtool.parser.common;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertEquals;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.parser.ISourceRepresentation;
import ddt.dtool.parser.DeeTokens;
import ddt.dtool.parser.DeeLexerErrors;

public class Token implements ISourceRepresentation, IToken {
	
	public final DeeTokens type;
	public final int startPos;
	public final String source;
	
	public Token(DeeTokens tokenType, String source, int startPos) {
		this.type = assertNotNull(tokenType);
		this.source = assertNotNull(source);
		this.startPos = startPos;
		if(tokenType.hasSourceValue()) {
			assertEquals(tokenType.getSourceValue(), source);
		}
	}
	
	@Override
	public final DeeTokens getType() {
		return type;
	}
	
	@Override
	public final int getStartPos() {
		return startPos;
	}
	
	public final int getLength() {
		return source.length();
	}
	
	@Override
	public final int getEndPos() {
		return startPos + source.length();
	}
	
	@Override
	public final SourceRange getSourceRange() {
		return new SourceRange(getStartPos(), getLength());
	}
	
	@Override
	public final String getSourceValue() {
		return source;
	}
	
	public DeeLexerErrors getError() {
		return null;
	}
	
	@Override
	public String toString() {
		return type +"►"+ source;
	}
	
	public static class ErrorToken extends Token {
		
		protected final DeeLexerErrors error;
		
		public ErrorToken(DeeTokens tokenType, String value, int start, DeeLexerErrors error) {
			super(tokenType, value, start);
			this.error = error;
			if(tokenType == DeeTokens.INVALID_TOKEN || error == DeeLexerErrors.INVALID_CHARACTERS) {
				assertTrue(tokenType == DeeTokens.INVALID_TOKEN);
				assertTrue(error == DeeLexerErrors.INVALID_CHARACTERS);
			}
		}
		
		@Override
		public DeeLexerErrors getError() {
			return error;
		}
	}
	
}