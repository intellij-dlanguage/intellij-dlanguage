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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.misc.NumberUtil.isInRange;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.parser.DeeLexer;
import ddt.dtool.parser.DeeTokens;


public class LexerResult {
	
	public final String source;
	public final List<LexElement> tokenList;
	
	public LexerResult(String source, List<LexElement> tokenList) {
		this.source = source;
		this.tokenList = Collections.unmodifiableList(tokenList);
		
		assertTrue(tokenList.size() > 0);
		assertTrue(tokenList.get(tokenList.size()-1).isEOF());
	}
	
	public ListIterator<LexElement> getFirstLexElementAtOffset(final int offset) {
		assertTrue(offset <= source.length());
		
		ListIterator<LexElement> iterator = tokenList.listIterator();
		
		while(true) {
			assertTrue(iterator.hasNext());
			LexElement lexElement = iterator.next();
			
			assertTrue(lexElement.getFullRangeStartPos() <= offset);
			
			if(lexElement.isEOF())
				break;
			if(offset <= lexElement.getEndPos())
				break;
		}
		return iterator;
	}
	
	public IToken findFirstTokenAtOffset(final int offset) {
		ListIterator<LexElement> iterator = getFirstLexElementAtOffset(offset);
		LexElement lexElement = IteratorUtil.getCurrentElement(iterator);
		return findTokenInLexElement(lexElement, offset);
	}
	
	public IToken findTokenInLexElement(LexElement lexElement, int offset) {
		assertTrue(isInRange(lexElement.getFullRangeStartPos(), offset, lexElement.getEndPos()));
		
		if(offset >= lexElement.getStartPos()) {
			return lexElement;
		} else {
			// Search in comments, token is in the subchannel range [FullRangeStartPos .. StartPos]
			int searchStartPos = lexElement.getFullRangeStartPos();
			// We don't store subchannel tokens, so we have to reparse to find them:
			return findFirstTokenAtOffset(source, searchStartPos, offset);
		}
	}
	
	public TokenAtOffsetResult findTokenAtOffset(int offset) {
		ListIterator<LexElement> lexElementCursor = getFirstLexElementAtOffset(offset);
		LexElement lexElement = IteratorUtil.getCurrentElement(lexElementCursor);		
		
		IToken tokenAtLeft;
		IToken tokenAtRight;
		
		if(lexElement.getFullRangeStartPos() < offset) {
			assertTrue(offset <= lexElement.getEndPos());
			
			tokenAtLeft = findTokenInLexElement(lexElement, offset);
			
			if(offset < tokenAtLeft.getEndPos()) {
				tokenAtRight = tokenAtLeft;
			} else {
				assertTrue(tokenAtLeft.getEndPos() == offset);
				
				if(offset < lexElement.getEndPos()) {
					tokenAtRight = findTokenInLexElement(lexElement, tokenAtLeft.getEndPos());
				} else {  
					assertTrue(tokenAtLeft.getEndPos() == offset);
					
					if(lexElementCursor.hasNext()) {
						LexElement nextLexElement = lexElementCursor.next();
						tokenAtRight = findTokenInLexElement(nextLexElement, offset);
					} else {
						tokenAtRight = null;
					}
				}
			}
			
		} else {
			tokenAtLeft = null;
			assertTrue(offset == lexElement.getFullRangeStartPos());
			tokenAtRight = findTokenInLexElement(lexElement, offset);
		}
		
		return new TokenAtOffsetResult(tokenAtLeft, tokenAtRight);
	}
	
	public static class TokenAtOffsetResult {
		
		public final IToken atLeft;
		public final IToken atRight;
		
		public TokenAtOffsetResult(IToken tokenAtOffsetLeft, IToken tokenAtOffsetRight) {
			this.atLeft = tokenAtOffsetLeft;
			this.atRight = tokenAtOffsetRight;
		}
		
		public boolean isSingleToken() {
			return atLeft == atRight;
		}
		
	}
	
	protected static Token findFirstTokenAtOffset(String source, int startPos, final int offset) {
		assertTrue(startPos <= offset);
		DeeLexer lexer = new DeeLexer(source);
		lexer.reset(startPos);
		while(true) {
			Token token = lexer.next();
			if(offset <= token.getEndPos())
				return token;
			assertTrue(token.type != DeeTokens.EOF);
		}
	}
	
}