/*******************************************************************************
 * Copyright (c) 2013, 2013 IBM Corporation and others.
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
import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.dtool.parser.DeeTokens;


/**
 * An extension of the simple {@link Token} with additional info.
 * Stores information about some preceding sub-channel tokens.
 * Can be a {@link MissingLexElement}, a synthetic token that represents a missing expected token.
 */
public abstract class BaseLexElement implements IToken {
	
	public final DeeTokens type;
	public final String source;
	public final int startPos;
	public final int fullStartPos;
	/** This array stores some (but not all) preceding subchannel tokens.  */
	protected final Token[] relevantPrecedingSubChannelTokens;
	
	public BaseLexElement(DeeTokens type, String source, int startPos, int fullStartPos, 
		Token[] ignoredPrecedingTokens) {
		this.type = assertNotNull(type);
		if(type.hasSourceValue()) {
			assertEquals(type.getSourceValue(), source);
		}
		this.source = assertNotNull(source);
		this.startPos = startPos;
		this.fullStartPos = fullStartPos;
		this.relevantPrecedingSubChannelTokens = ignoredPrecedingTokens;
		assertTrue(ignoredPrecedingTokens == null || ignoredPrecedingTokens.length > 0);
	}
	
	@Override
	public DeeTokens getType() {
		return type;
	}
	
	@Override
	public final String getSourceValue() {
		return source;
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
	
	public final boolean isEOF() {
		return type == DeeTokens.EOF;
	}
	
	/** @return the full range start pos of this token, including preceding ignored tokens. */
	public final int getFullRangeStartPos() {
		return fullStartPos;
	}
	
	// TODO: maybe getStarpos should behave like this all the time
	public final int getEffectiveStartPos() {
		return isMissingElement() ? getFullRangeStartPos() : getStartPos();
	}
	
	public static final Token[] EMPTY_ARRAY = new Token[0];
	
	public Token[] getRelevantPrecedingSubChannelTokens() {
		return relevantPrecedingSubChannelTokens == null ? EMPTY_ARRAY : relevantPrecedingSubChannelTokens;
	}
	
	public abstract boolean isMissingElement();
	
	public abstract ParserError getMissingError();
	
	
	public String toStringRangePrefix() {
		return getFullRangeStartPos() != getStartPos() ? "【"+getFullRangeStartPos()+"】" : "";
	}
	
	@Override
	public String toString() {
		return toStringRangePrefix() + type +"►"+ source;
	}
	
}