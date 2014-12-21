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

import java.util.ArrayList;

import ddt.dtool.parser.DeeTokenSemantics;
import ddt.dtool.parser.DeeTokens;
import ddt.melnorme.utilbox.misc.ArrayUtil;

/**
 * Produces {@link LexElement}'s from a lexer to construct a {@link LexElementSource}.
 * The additional abstraction this classes creates allows for customization of {@link LexElement}'s, plus
 * potential performance optimizations (TODO) 
 */
public class LexElementProducer {
	
	public static LexElementSource createFromLexer(AbstractLexer lexer) {
		return new LexElementSource(new LexElementProducer().produceLexTokens(lexer));
	}
	
	public ArrayList<LexElement> produceLexTokens(AbstractLexer lexer) {
		ArrayList<LexElement> lexElementList = new ArrayList<>();
		
		while(true) {
			LexElement lexElement = produceLexElement(lexer);
			lexElementList.add(lexElement);
			if(lexElement.isEOF()) {
				break;
			}
		}
		return lexElementList;
	}
	
	public LexElement produceLexElement(AbstractLexer lexer) {
		ArrayList<Token> relevantSubChannelTokens = null;
		int fullStartPos = lexer.getLexingPosition();
		while(true) {
			lexer.parseToken();
			Token token = lexer.createParsedToken();
			tokenParsed(token);
			
			DeeTokens tkType = lexer.tokenType;
			if(tkType.isSubChannel && !isRelevant(tkType)) {
				continue;
			}
			
			if(tkType.isSubChannel) {
				if(relevantSubChannelTokens == null)
					relevantSubChannelTokens = new ArrayList<Token>(4);
				relevantSubChannelTokens.add(token);
				continue;
			}
			return new LexElement(tkType, token.source, token.startPos, fullStartPos, 
				ArrayUtil.toArray(relevantSubChannelTokens, Token.class));
		}
	}
	
	protected static boolean isRelevant(DeeTokens type) {
		return type == DeeTokens.LINE_END || DeeTokenSemantics.tokenTypeIsDocComment(type);
	}
	
	@SuppressWarnings("unused")
	protected void tokenParsed(Token token) {
		// Default implementation
	}
	
}