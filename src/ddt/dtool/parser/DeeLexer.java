/*******************************************************************************
 * Copyright (c) 2012, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.parser;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;

import java.util.Arrays;

import ddt.dtool.parser.common.AbstractLexer;
import ddt.dtool.parser.common.Token;


public class DeeLexer extends AbstractLexer {
	
	public DeeLexer(String source) {
		super(source);
	}
	
	protected enum CharRuleCategory {
		BAD_TOKEN,
		
		EOF,
		EOF_CHARS,
		
		EOL,
		WHITESPACE,
		
		HASH,
		
		OPEN_PARENS, CLOSE_PARENS,
		OPEN_BRACE, CLOSE_BRACE,
		OPEN_BRACKET, CLOSE_BRACKET,
		
		ALPHA(true, true),
		DIGIT(false, true),
		
		QUESTION, COMMA, SEMICOLON, COLON, DOLLAR, AT,
		
		MINUS, PLUS, STAR, SLASH, MOD,
		
		AMPERSAND, VBAR, CARET, EQUAL, TILDE,
		DOT,
		
		LESS_THAN,
		GREATER_THAN,
		EXCLAMATION,
		
		SINGLE_QUOTES,
		
		GRAVE_ACCENT,
		ALPHA_R(true, true),
		DOUBLE_QUOTES,
		ALPHA_H(true, true),
		ALPHA_Q(true, true),
		
		;
		private final boolean canBeIdentifierStart;
		private final boolean canBeIdentifierPart;
		
		private CharRuleCategory() {
			this(false, false);
		}
		
		private CharRuleCategory(boolean canBeIdentifierStart, boolean canBeIdentifierPart) {
			this.canBeIdentifierStart = canBeIdentifierStart;
			this.canBeIdentifierPart = canBeIdentifierPart;
		}
		
	}
	
	protected static final CharRuleCategory[] startRuleCharCategory;
	
	static {
		startRuleCharCategory = new CharRuleCategory[ASCII_LIMIT+1];
		Arrays.fill(startRuleCharCategory, CharRuleCategory.BAD_TOKEN);
		
		startRuleCharCategory[0x00] = CharRuleCategory.EOF_CHARS;
		startRuleCharCategory[0x1A] = CharRuleCategory.EOF_CHARS;
		
		startRuleCharCategory[0x0D] = CharRuleCategory.EOL;
		startRuleCharCategory[0x0A] = CharRuleCategory.EOL;
		
		startRuleCharCategory[0x20] = CharRuleCategory.WHITESPACE;
		startRuleCharCategory[0x09] = CharRuleCategory.WHITESPACE;
		startRuleCharCategory[0x0B] = CharRuleCategory.WHITESPACE;
		startRuleCharCategory[0x0C] = CharRuleCategory.WHITESPACE;
		
		startRuleCharCategory['#'] = CharRuleCategory.HASH;

		startRuleCharCategory['('] = CharRuleCategory.OPEN_PARENS;
		startRuleCharCategory[')'] = CharRuleCategory.CLOSE_PARENS;
		startRuleCharCategory['{'] = CharRuleCategory.OPEN_BRACE;
		startRuleCharCategory['}'] = CharRuleCategory.CLOSE_BRACE;
		startRuleCharCategory['['] = CharRuleCategory.OPEN_BRACKET;
		startRuleCharCategory[']'] = CharRuleCategory.CLOSE_BRACKET;
		
		Arrays.fill(startRuleCharCategory, '0', '9'+1, CharRuleCategory.DIGIT);
		Arrays.fill(startRuleCharCategory, 'a', 'z'+1, CharRuleCategory.ALPHA);
		Arrays.fill(startRuleCharCategory, 'A', 'Z'+1, CharRuleCategory.ALPHA);
		startRuleCharCategory['_'] = CharRuleCategory.ALPHA;
		
		startRuleCharCategory['?'] = CharRuleCategory.QUESTION;
		startRuleCharCategory[','] = CharRuleCategory.COMMA;
		startRuleCharCategory[';'] = CharRuleCategory.SEMICOLON;
		startRuleCharCategory[':'] = CharRuleCategory.COLON;
		startRuleCharCategory['$'] = CharRuleCategory.DOLLAR;
		startRuleCharCategory['@'] = CharRuleCategory.AT;
		
		startRuleCharCategory['.'] = CharRuleCategory.DOT;
		
		startRuleCharCategory['-'] = CharRuleCategory.MINUS;
		startRuleCharCategory['+'] = CharRuleCategory.PLUS;
		startRuleCharCategory['*'] = CharRuleCategory.STAR;
		startRuleCharCategory['/'] = CharRuleCategory.SLASH;
		startRuleCharCategory['%'] = CharRuleCategory.MOD;
		
		startRuleCharCategory['&'] = CharRuleCategory.AMPERSAND;
		startRuleCharCategory['|'] = CharRuleCategory.VBAR;
		startRuleCharCategory['^'] = CharRuleCategory.CARET;
		startRuleCharCategory['='] = CharRuleCategory.EQUAL;
		startRuleCharCategory['~'] = CharRuleCategory.TILDE;
		
		startRuleCharCategory['<'] = CharRuleCategory.LESS_THAN;
		startRuleCharCategory['>'] = CharRuleCategory.GREATER_THAN;
		startRuleCharCategory['!'] = CharRuleCategory.EXCLAMATION;
		
		startRuleCharCategory['\''] = CharRuleCategory.SINGLE_QUOTES;
		
		startRuleCharCategory['`'] = CharRuleCategory.GRAVE_ACCENT;
		startRuleCharCategory['r'] = CharRuleCategory.ALPHA_R;
		startRuleCharCategory['"'] = CharRuleCategory.DOUBLE_QUOTES;
		startRuleCharCategory['x'] = CharRuleCategory.ALPHA_H;
		startRuleCharCategory['q'] = CharRuleCategory.ALPHA_Q;
	}
	
	public static CharRuleCategory getCharCategory(int ch) {
		if(ch == EOF) {
			return CharRuleCategory.EOF;
		}
		if(ch > ASCII_LIMIT) {
			return CharRuleCategory.ALPHA;
		}
		return startRuleCharCategory[ch];
	}
	
	@Override
	public Void doParseToken() {
		CharRuleCategory ruleCategory = getCharCategory(lookAhead());
		
		switch (ruleCategory) {
		case EOF: return endMatch(DeeTokens.EOF);
		
		case EOF_CHARS: return matchEOFCharacter();
		case EOL: return matchEndOfLine();
		case WHITESPACE: return matchWhiteSpace();
		
		case HASH: return ruleHashStart();
		case SLASH: return ruleSlashStart();
		
		case GRAVE_ACCENT: return matchWYSIWYGString();
		case ALPHA_R: return ruleRStart();
		case DOUBLE_QUOTES: return matchString();
		case ALPHA_H: return ruleHStart();
		case ALPHA_Q: return ruleQStart();
		
		case DIGIT: return ruleDigitStart();
		case ALPHA: return ruleAlphaStart();
		
		case OPEN_PARENS: return matchTokenFromStartPos(DeeTokens.OPEN_PARENS, 1);
		case CLOSE_PARENS: return matchTokenFromStartPos(DeeTokens.CLOSE_PARENS, 1);
		case OPEN_BRACE: return matchTokenFromStartPos(DeeTokens.OPEN_BRACE, 1);
		case CLOSE_BRACE: return matchTokenFromStartPos(DeeTokens.CLOSE_BRACE, 1);
		case OPEN_BRACKET: return matchTokenFromStartPos(DeeTokens.OPEN_BRACKET, 1);
		case CLOSE_BRACKET: return matchTokenFromStartPos(DeeTokens.CLOSE_BRACKET, 1);
		
		case SINGLE_QUOTES: return matchCharacterLiteral();
		case QUESTION: return matchTokenFromStartPos(DeeTokens.QUESTION, 1);
		case COMMA: return matchTokenFromStartPos(DeeTokens.COMMA, 1);
		case SEMICOLON: return matchTokenFromStartPos(DeeTokens.SEMICOLON, 1);
		case COLON: return matchTokenFromStartPos(DeeTokens.COLON, 1);
		case DOLLAR: return matchTokenFromStartPos(DeeTokens.DOLLAR, 1);
		case AT: return matchTokenFromStartPos(DeeTokens.AT, 1);
		
		case DOT: return ruleDotStart();
		
		case PLUS: return rule3Choices('=', DeeTokens.PLUS_ASSIGN, '+', DeeTokens.INCREMENT, DeeTokens.PLUS);
		case MINUS: return rule3Choices('=', DeeTokens.MINUS_ASSIGN, '-', DeeTokens.DECREMENT, DeeTokens.MINUS);
		case STAR: return rule2Choices('=', DeeTokens.MULT_ASSIGN, DeeTokens.STAR);
		case MOD: return rule2Choices('=', DeeTokens.MOD_ASSIGN, DeeTokens.MOD);
		
		case AMPERSAND: 
			return rule3Choices('=', DeeTokens.AND_ASSIGN, '&', DeeTokens.LOGICAL_AND, DeeTokens.AND);
		case VBAR: 
			return rule3Choices('=', DeeTokens.OR_ASSIGN, '|', DeeTokens.LOGICAL_OR, DeeTokens.OR);
		case CARET: return ruleCaretStart();
		case EQUAL: return rule3Choices('=', DeeTokens.EQUALS, '>', DeeTokens.LAMBDA, DeeTokens.ASSIGN);
		case TILDE: return rule2Choices('=', DeeTokens.CONCAT_ASSIGN, DeeTokens.CONCAT);
		
		case LESS_THAN: return ruleLessStart();
		case GREATER_THAN: return ruleGreaterStart();
		case EXCLAMATION: return ruleExclamation();
		
		case BAD_TOKEN: return matchError();
		
		}
		throw assertUnreachable();
	}
	
	protected final boolean consumeRuleCategoryOnce(CharRuleCategory ruleCategory) {
		CharRuleCategory currentCharCategory = getCharCategory(lookAhead());
		if(currentCharCategory == ruleCategory) {
			pos++;
			return true;
		}
		return false;
	}
	
	protected final int consumeRuleCategorySequence(CharRuleCategory ruleCategory) {
		int count = 0;
		while(true) {
			CharRuleCategory currentCharCategory = getCharCategory(lookAhead());
			if(currentCharCategory == ruleCategory) {
				pos++;
				count++;
				continue;
			}
			return count;
		}
	}
	
	/* --------------------------- Matching --------------------------- */
	
	protected final Void matchError() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.BAD_TOKEN);
		while(true) {
			pos++;
			if(getCharCategory(lookAhead()) == CharRuleCategory.BAD_TOKEN) {
				continue;
			} else {
				return endMatchWithError(DeeTokens.INVALID_TOKEN, DeeLexerErrors.INVALID_CHARACTERS);
			}
		}
	}
	
	protected final Void matchEOFCharacter() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.EOF_CHARS);
		return createEOFToken();
	}
	
	/** EOF token will consist of not only initial EOF marker but everything afterwards until true end of file. */
	protected final Void createEOFToken() {
		return matchTokenFromStartPos(DeeTokens.EOF, source.length() - tokenStartPos);
	}
	
	protected final Void matchEndOfLine() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.EOL);
		if(lookAhead() == '\r' && lookAhead(1) == '\n') {
			pos += 2;
		} else {
			pos += 1;
		}
		return endMatch(DeeTokens.LINE_END);
	}
	
	protected final Void matchWhiteSpace() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.WHITESPACE);
		pos++;
		consumeRuleCategorySequence(CharRuleCategory.WHITESPACE);
		return endMatch(DeeTokens.WHITESPACE);
	}
	
	protected final Void ruleHashStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.HASH);
		// Note that shebang will not be recognized if lexer input has a UTF BOM
		if(pos == 0 && lookAhead(1) == '!') {
			pos += 2;
			seekToNewline();
			return endMatch(DeeTokens.SCRIPT_LINE_INTRO);
		} else {
			pos += 1;
			return ruleHashPragmaTokens();
		}
	}
	
	protected final Void ruleAlphaStart() {
		if(pos == 0 && lookAhead() == 0xFEFF) {
			return matchTokenFromStartPos(DeeTokens.WHITESPACE, 1); // UTF Byte Order Mark (BOM)
		}
		assertTrue(getCharCategory(lookAhead()).canBeIdentifierStart);
		
		// Note, according to D spec, not all non-ASCII characters are valid as identifier characters
		// but for simplification we ignore that for lexing. 
		// Perhaps this can be analized later in a lexing semantics phase.
		boolean asciiOnly = readIdentifierPartChars();
		if(!asciiOnly) {
			return endMatch(DeeTokens.IDENTIFIER);
		}
		String idValue = source.substring(tokenStartPos, pos);
		DeeTokens keywordToken = DeeLexerKeywordHelper.getKeywordToken(idValue);
		if(keywordToken != null) {
			if(keywordToken == DeeTokens.EOF) {
				return createEOFToken();
			}
			return endMatch(keywordToken);
		}
		return endMatch(DeeTokens.IDENTIFIER);
	}
	
	/** Advance position until lookahead is not valid identifier part.
	 * Returns whether all scanned characters where ASCII or not. */
	protected final boolean readIdentifierPartChars() {
		boolean asciiOnly = true;
		do {
			int ch = lookAhead();
			CharRuleCategory charCategory = getCharCategory(ch);
			if(!charCategory.canBeIdentifierPart) {
				break;
			}
			if(ch > ASCII_LIMIT) {
				asciiOnly = false;
			}
			pos++;
		} while(true);
		return asciiOnly;
	}
	
	protected static final String[] SEEKUNTIL_MULTICOMMENTS = { "+/", "/+" };
	
	protected final Void ruleSlashStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.SLASH);
		
		pos++;
		
		if(lookAhead() == '*') {
			pos++;
			DeeTokens commentType = DeeTokens.COMMENT_MULTI;
			if(lookAhead() == '*' && lookAhead(1) != '/')
				commentType = DeeTokens.DOCCOMMENT_MULTI;
			
			int result = seekTo("*/");
			if(result == 0) {
				return endMatch(commentType);
			} else {
				return endMatchWithError(commentType, DeeLexerErrors.COMMENT_NOT_TERMINATED);
			}
		} else if(lookAhead() == '+') {
			pos++;
			DeeTokens commentType = DeeTokens.COMMENT_NESTED;
			if(lookAhead() == '+' && lookAhead(1) != '/')
				commentType = DeeTokens.DOCCOMMENT_NESTED;
			
			int nestingLevel = 1;
			do {
				int result = seekTo(SEEKUNTIL_MULTICOMMENTS);
				
				if(result == 0) { // "+/"
					nestingLevel--;
				} else if(result == 1) { // "/+"
					nestingLevel++;
				} else {
					assertTrue(result == -1);
					return endMatchWithError(commentType, DeeLexerErrors.COMMENTNESTED_NOT_TERMINATED);
				}
			} while (nestingLevel > 0);
			
			return endMatch(commentType);
			
		} else if(lookAhead() == '/') {
			pos++;
			DeeTokens commentType = lookAhead() == '/' ? DeeTokens.DOCCOMMENT_LINE : DeeTokens.COMMENT_LINE; 
			seekToNewlineOrEOFCharsRule(); // Note that EOF Chars are also a valid terminators for this rule
			return endMatch(commentType);
		} else if(lookAhead() == '=') {
			pos++;
			return endMatch(DeeTokens.DIV_ASSIGN);
		} else {
			return endMatch(DeeTokens.DIV);
		}
	}
	
	protected final void seekToNewlineOrEOFCharsRule() {
		while(true) {
			int ch = lookAhead();
			if(ch == EOF) {
				return;
			}
			pos++;
			if(ch == '\r') {
				if(lookAhead() == '\n') {
					pos++;
				}
				return;
			} else if(ch == '\n' || getCharCategory(ch) == CharRuleCategory.EOF_CHARS) {
				return;
			}
		}
	}
	
	protected final Void matchWYSIWYGString() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.GRAVE_ACCENT);
		return matchVerbatimString('`', DeeTokens.STRING_WYSIWYG);
	}
	
	/** Match a string without any escape sequences. */
	protected final Void matchVerbatimString(char quoteChar, DeeTokens stringToken) {
		pos++;
		
		int result = seekTo(quoteChar);
		if(result == 0) {
			ruleStringPostFix();
			return endMatch(stringToken);
		} else {
			assertTrue(result == -1);
			return endMatchWithError(stringToken, DeeLexerErrors.STRING_NOT_TERMINATED__REACHED_EOF);
		}
	}
	
	protected final void ruleStringPostFix() {
		int ch = lookAhead();
		switch(ch) {
		case 'c': pos++; break;
		case 'w': pos++; break;
		case 'd': pos++; break;
		}
	}
	
	protected final Void ruleRStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.ALPHA_R);
		
		if(lookAhead(1) == '"') {
			pos++; 
			return matchVerbatimString('"', DeeTokens.STRING_WYSIWYG);
		}
		return ruleAlphaStart(); 
	}
	
	
	protected final Void matchString() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.DOUBLE_QUOTES);
		
		pos++;
		while(true) {
			int ch = lookAhead();
			
			if(ch == '"') {
				pos++;
				ruleStringPostFix();
				return endMatch(DeeTokens.STRING_DQ);
			} else if(ch == EOF) {
				// TODO , maybe recover using EOL?
				return endMatchWithError(DeeTokens.STRING_DQ, DeeLexerErrors.STRING_NOT_TERMINATED__REACHED_EOF);
			} else if(ch == '\\') {
				if (lookAhead(1) == '"' || lookAhead(1) == '\\') {
					pos += 2;
					continue;
				}
				// We ignore the other escape sequences rules since they are not important for lexing
				// see http://dlang.org/lex.html#EscapeSequence
			}
			pos++;
		}
	}
	
	protected final Void ruleHStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.ALPHA_H);
		
		if(lookAhead(1) == '"') {
			pos++; 
			return matchVerbatimString('"', DeeTokens.STRING_HEX);
		} else {
			return ruleAlphaStart();
		}
	}
	
	protected final Void ruleQStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.ALPHA_Q);
		
		if(lookAhead(1) == '"') {
			return matchDelimString();
		} else if(lookAhead(1) == '{') {
			return matchTokenString();
		} else {
			return ruleAlphaStart(); 
		}
	}
	
	protected final Void matchDelimString() {
		pos+=2;
		int ch = lookAhead();
		
		CharRuleCategory charCategory = getCharCategory(ch); 
		
		switch(charCategory) {
		case EOF: return endMatchWithError(DeeTokens.STRING_DELIM, DeeLexerErrors.STRING_DELIM_NO_DELIMETER);
		case OPEN_PARENS: return matchSimpleDelimString('(',')');
		case OPEN_BRACKET: return matchSimpleDelimString('[',']');
		case OPEN_BRACE: return matchSimpleDelimString('{','}'); 
		case LESS_THAN: return matchSimpleDelimString('<','>');
		
		default:
			if(charCategory.canBeIdentifierStart) {
				return matchHereDocDelimString_FromIdStart();
			} else {
				return matchSimpleDelimString((char)ch, (char)ch);
			}
		}
	}
	
	protected final Void matchSimpleDelimString(char openDelim, char closeDelim) {
		assertTrue(lookAhead() == openDelim);
		pos++;
		int nestingLevel = 1;
		
		do {
			int result = seekTo(closeDelim, openDelim);
			// note, closeDelim can be equal to openDelim, in which case result == 1 should never happen 
			
			if(result == 0) { // closeDelim
				nestingLevel--;
			} else if(result == 1) { // openDelim
				nestingLevel++;
			} else {
				assertTrue(result == -1);
				return endMatchWithError(DeeTokens.STRING_DELIM, DeeLexerErrors.STRING_NOT_TERMINATED__REACHED_EOF);
			}
		} while (nestingLevel > 0);
		
		if(lookAhead() == '"') {
			pos++;
			return endMatch(DeeTokens.STRING_DELIM);
		} else {
			seekTo('"');
			return endMatchWithError(DeeTokens.STRING_DELIM, DeeLexerErrors.STRING_DELIM_NOT_PROPERLY_TERMINATED);
		}
	}
	
	protected final Void matchHereDocDelimString_FromIdStart() {
		int idStartPos = pos;
		pos++; // Advance first char of identifier
		readIdentifierPartChars();
		String hereDocId = source.subSequence(idStartPos, pos).toString(); // Optimization note: allocation here
		
		if(getCharCategory(lookAhead()) != CharRuleCategory.EOL) {
			seekHereDocEndDelim(hereDocId);
			return endMatchWithError(DeeTokens.STRING_DELIM, DeeLexerErrors.STRING_DELIM_ID_NOT_PROPERLY_FORMED);
		}
		
		int result = seekHereDocEndDelim(hereDocId);
		if(result == -1) {
			return endMatchWithError(DeeTokens.STRING_DELIM, DeeLexerErrors.STRING_NOT_TERMINATED__REACHED_EOF);
		}
		assertTrue(result == 0);
		return endMatch(DeeTokens.STRING_DELIM);
	}
	
	protected final int seekHereDocEndDelim(String hereDocId) {
		int result;
		while(true) {
			result = seekToNewline();
			if(result == -1) {
				break;
			}
			if(inputMatchesSequence(hereDocId)) {
				pos += hereDocId.length();
				if(lookAhead() == '"') {
					pos++;
					result = 0;
					break;
				}
			}
		}
		return result;
	}
	
	protected final Void matchTokenString() {
		pos+=2;
		
		int tokenStringStartPos = tokenStartPos;
		tokenStartPos = pos;
		
		int nestingLevel = 1;
		do {
			Token token = next();
			if(token.type == DeeTokens.OPEN_BRACE) {
				nestingLevel++;
			} else if (token.type == DeeTokens.CLOSE_BRACE) {
				nestingLevel--;
			} else if (token.type == DeeTokens.EOF) {
				tokenStartPos = tokenStringStartPos;
				return endMatchWithError(DeeTokens.STRING_TOKENS, DeeLexerErrors.STRING_NOT_TERMINATED__REACHED_EOF);
			}
		} while(nestingLevel > 0);
		
		tokenStartPos = tokenStringStartPos;
		return endMatch(DeeTokens.STRING_TOKENS);
	}
	
	protected final Void matchCharacterLiteral() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.SINGLE_QUOTES);
		
		pos++;
		while(true) {
			int lookahead = lookAhead();
			CharRuleCategory charCategory = getCharCategory(lookahead);
			
			if(charCategory == CharRuleCategory.SINGLE_QUOTES) {
				pos++;
				if(pos == tokenStartPos + 2) {
					return endMatchWithError(DeeTokens.CHARACTER, DeeLexerErrors.CHAR_LITERAL_EMPTY);
				}
				
				return endMatch(DeeTokens.CHARACTER);
			} else if (charCategory == CharRuleCategory.EOF) {
				return endMatchWithError(DeeTokens.CHARACTER, 
					DeeLexerErrors.CHAR_LITERAL_NOT_TERMINATED__REACHED_EOF);
			} else if (charCategory == CharRuleCategory.EOL) {
				seekToNewline();
				return endMatchWithError(DeeTokens.CHARACTER, 
					DeeLexerErrors.CHAR_LITERAL_NOT_TERMINATED__REACHED_EOL);
			} else if (lookahead == '\\') {
				if (lookAhead(1) == '\'' || lookAhead(1) == '\\') {
					pos += 2;
					continue;
				} else {
					// Again, we ignore the other escape sequence rules
				}
			}
			pos++;
		}
	}
	
	protected static enum EInt_Literal_Type  {
		BINARY, OCTAL, DECIMAL, HEX
	}
	
	protected final Void ruleDigitStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.DIGIT);
		
		EInt_Literal_Type literalType = EInt_Literal_Type.DECIMAL;
		boolean invalidDigitFound = false;
		boolean hasAtLeastOneDigit = true;
		int maxDigitChar = '9';

		int firstChar = lookAhead();
		
		
		if(firstChar == '0') {
			if(lookAhead(1) == 'x' || lookAhead(1) == 'X') {
				pos++;
				literalType = EInt_Literal_Type.HEX;
				hasAtLeastOneDigit = false;
			} else if(lookAhead(1) == 'b' || lookAhead(1) == 'B') {
				pos++;
				literalType = EInt_Literal_Type.BINARY;
				maxDigitChar = '1';
				hasAtLeastOneDigit = false;
			} else {
				literalType = EInt_Literal_Type.OCTAL;
				maxDigitChar = '7';
			}
		}
		
		
		while(true) {
			pos++;
			
			int ch = lookAhead();
			
			if(getCharCategory(ch) == CharRuleCategory.DIGIT) {
				hasAtLeastOneDigit = true;
				if(ch > maxDigitChar) {
					invalidDigitFound = true;
				}
				continue;
			}
			if(ch == '_') {
				continue;
			}
			if(literalType == EInt_Literal_Type.HEX && isHexDigit(ch)) {
				hasAtLeastOneDigit = true;
				continue;
			}
			
			break;
		}
		
		if(literalType == EInt_Literal_Type.OCTAL && pos == tokenStartPos + 1) {
			literalType = EInt_Literal_Type.DECIMAL; // Zero literal is a decimal literal.
		}
		
		boolean hasIntegerSuffix = readIntegerSuffix();
		
		if(literalType != EInt_Literal_Type.OCTAL && literalType != EInt_Literal_Type.BINARY 
			&& hasIntegerSuffix == false) {
			
			boolean isHex = literalType == EInt_Literal_Type.HEX;
			int ch = lookAhead();
			// Watch out for special spec exception for stuff like "1..2" :
			if(ch == '.' && lookAhead(1) != '.') { 
				return matchFloatLiteral_FromDecimalPoint(isHex);
			}
			if(ch == 'f' || ch == 'F' || ch == 'L' || ch == 'i' 
				|| (isHex && (ch == 'P' || ch == 'p'))
				|| (!isHex && (ch == 'E' || ch == 'e'))
				) {
				return matchFloatLiteral_AfterFractionalPart(isHex, false);
			}
		}
		
		switch (literalType) {
		case BINARY: return createIntegerToken(DeeTokens.INTEGER_BINARY, invalidDigitFound, hasAtLeastOneDigit);
		case OCTAL: return createIntegerToken(DeeTokens.INTEGER_OCTAL, invalidDigitFound, hasAtLeastOneDigit);
		case DECIMAL: return endMatch(DeeTokens.INTEGER_DECIMAL);
		case HEX: return createIntegerToken(DeeTokens.INTEGER_HEX, false, hasAtLeastOneDigit);
		}
		throw assertUnreachable();
	}
	
	protected final Void createIntegerToken(DeeTokens deeToken, boolean invalidDigitFound, 
		boolean hasAtLeastOneDigit) {
		if(!hasAtLeastOneDigit) {
			return endMatchWithError(deeToken, DeeLexerErrors.INT_LITERAL__HAS_NO_DIGITS);
		}
		if(invalidDigitFound) {
			return endMatchWithError(deeToken, deeToken == DeeTokens.INTEGER_BINARY ? 
				DeeLexerErrors.INT_LITERAL_BINARY__INVALID_DIGITS :
				DeeLexerErrors.INT_LITERAL_OCTAL__INVALID_DIGITS
				);
		}
		return endMatch(deeToken);
	}
	
	protected final boolean readIntegerSuffix() {
		int ch = lookAhead();
		if(ch == 'L') {
			pos++;
			if(lookAhead() == 'u' || lookAhead() == 'U') {
				pos++;
			}
			return true;
			
		} else if(ch == 'u' || ch == 'U') {
			pos++;
			if(lookAhead() == 'L') {
				pos++;
			}
			return true;
		}
		return false;
	}
	
	protected final static boolean isHexDigit(int ch) {
		return (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
	}
	
	protected final Void matchFloatLiteral_FromDecimalPoint(boolean isHex) {
		boolean precedingCharIsDot = true;
		while(true) {
			pos++;
			
			int ch = lookAhead();
			
			if(getCharCategory(ch) == CharRuleCategory.DIGIT) {
				precedingCharIsDot = false;
				continue;
			}
			if(isHex && isHexDigit(ch)) {
				precedingCharIsDot = false;
				continue;
			}
			if((isHex || !precedingCharIsDot) && ch == '_') {  
				precedingCharIsDot = false;
				continue;
			}
			
			break;
		}
		
		return matchFloatLiteral_AfterFractionalPart(isHex, precedingCharIsDot);
	}
	
	protected final Void matchFloatLiteral_AfterFractionalPart(boolean isHex, boolean precedingCharIsDot) {
		boolean exponentHasDigits = true;
		boolean hasExponent = false;
		
		int ch = lookAhead();
		if(	( isHex && (ch == 'P' || ch == 'p')) ||
			(!isHex && (ch == 'E' || ch == 'e') && !precedingCharIsDot)) {
			pos++;
			if(lookAhead() == '+' || lookAhead() == '-') {
				pos++;
			}
			hasExponent = true;
			exponentHasDigits = readDecimalDigitsOrUnderscore();
			precedingCharIsDot = false;
		}
		
		ch = lookAhead();
		if((isHex || !precedingCharIsDot) && (ch == 'f' || ch == 'F' || ch == 'L')) {
			pos++;
		}
		if((isHex || !precedingCharIsDot) && lookAhead() == 'i') {
			pos++;
		}
		
		if(isHex) {
			if(hasExponent == false) {
				return endMatchWithError(DeeTokens.FLOAT_HEX, DeeLexerErrors.FLOAT_LITERAL__HEX_HAS_NO_EXP);
			}
			if(!exponentHasDigits) {
				return endMatchWithError(DeeTokens.FLOAT_HEX, DeeLexerErrors.FLOAT_LITERAL__EXP_HAS_NO_DIGITS);
			} else {
				return endMatch(DeeTokens.FLOAT_HEX);
			}
		} else {
			if(!exponentHasDigits) {
				return endMatchWithError(DeeTokens.FLOAT_DECIMAL, DeeLexerErrors.FLOAT_LITERAL__EXP_HAS_NO_DIGITS);
			} else {
				if(precedingCharIsDot && getCharCategory(lookAhead()).canBeIdentifierStart) {
					pos--; // Don't consume dot as part of the float if ahead can be identifier
					assertTrue(lookAhead() == '.');
					return endMatch(DeeTokens.INTEGER_DECIMAL);
				}
				return endMatch(DeeTokens.FLOAT_DECIMAL);
			}
		}
	}
	
	protected final boolean readDecimalDigitsOrUnderscore() {
		boolean hasAtLeastOneDigit = false;
		while(true) {
			int ch = lookAhead();
			
			if(getCharCategory(ch) == CharRuleCategory.DIGIT || ch == '_') {
				pos++;
				if(ch != '_') {
					hasAtLeastOneDigit = true;
				}
				continue;
			}
			break;
		}
		return hasAtLeastOneDigit;
	}
	
	
	protected final Void ruleDotStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.DOT);
		
		int lookahead_1 = lookAhead(1);
		if(getCharCategory(lookahead_1) == CharRuleCategory.DIGIT) {
			return matchFloatLiteral_FromDecimalPoint(false);
		}
		
		if(lookahead_1 == '.') {
			if(lookAhead(2) == '.') {
				return matchTokenFromStartPos(DeeTokens.TRIPLE_DOT, 3);
			}
			return matchTokenFromStartPos(DeeTokens.DOUBLE_DOT, 2);
		}
		return matchTokenFromStartPos(DeeTokens.DOT, 1);
	}
	
	protected final Void ruleCaretStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.CARET);
		
		if(lookAhead(1) == '^') {
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.POW_ASSIGN, 3);
			}
			return matchTokenFromStartPos(DeeTokens.POW, 2);
		} else 
			return rule2Choices('=', DeeTokens.XOR_ASSIGN, DeeTokens.XOR); 
	}
	
	protected final Void ruleLessStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.LESS_THAN);
		
		if(lookAhead(1) == '=') {
			return matchTokenFromStartPos(DeeTokens.LESS_EQUAL, 2);
		} else if(lookAhead(1) == '<') {
			// <<
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.LEFT_SHIFT_ASSIGN, 3);
			}
			return matchTokenFromStartPos(DeeTokens.LEFT_SHIFT, 2);
		} else if(lookAhead(1) == '>') {
			// <>
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.LESS_GREATER_EQUAL, 3);
			}
			return matchTokenFromStartPos(DeeTokens.LESS_GREATER, 2);
		}
		return matchTokenFromStartPos(DeeTokens.LESS_THAN, 1);
	}
	
	protected final Void ruleGreaterStart() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.GREATER_THAN);
		
		if(lookAhead(1) == '=') {
			return matchTokenFromStartPos(DeeTokens.GREATER_EQUAL, 2);
		} else if(lookAhead(1) == '>') {
			// >>
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.RIGHT_SHIFT_ASSIGN, 3);
			} else if(lookAhead(2) == '>') {
				// >>>
				if(lookAhead(3) == '=') {
					return matchTokenFromStartPos(DeeTokens.TRIPLE_RSHIFT_ASSIGN, 4);
				} 
				return matchTokenFromStartPos(DeeTokens.TRIPLE_RSHIFT, 3);
			} 
			return matchTokenFromStartPos(DeeTokens.RIGHT_SHIFT, 2);
		} 
		return matchTokenFromStartPos(DeeTokens.GREATER_THAN, 1);
	}
	
	protected final Void ruleExclamation() {
		assertTrue(getCharCategory(lookAhead()) == CharRuleCategory.EXCLAMATION);
		
		if(lookAhead(1) == '=') {
			return matchTokenFromStartPos(DeeTokens.NOT_EQUAL, 2);
		} else if(lookAhead(1) == '<') {
			// !<
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.UNORDERED_G, 3);
			} else if(lookAhead(2) == '>') {
				// !<>
				if(lookAhead(3) == '=') {
					return matchTokenFromStartPos(DeeTokens.UNORDERED, 4);
				} 
				return matchTokenFromStartPos(DeeTokens.UNORDERED_E, 3);
			} 
			return matchTokenFromStartPos(DeeTokens.UNORDERED_GE, 2);
		} else if(lookAhead(1) == '>') {
			// !>
			if(lookAhead(2) == '=') {
				return matchTokenFromStartPos(DeeTokens.UNORDERED_L, 3);
			}
			return matchTokenFromStartPos(DeeTokens.UNORDERED_LE, 2);
		}
		return matchTokenFromStartPos(DeeTokens.NOT, 1);
	}
	
	protected final Void ruleHashPragmaTokens() {
		if(inputMatchesSequence("line") && getCharCategory(lookAhead(4)) == CharRuleCategory.WHITESPACE) {
			return matchSpecialTokenLine();
		}
		seekToNewline();
		return endMatchWithError(DeeTokens.SPECIAL_TOKEN_LINE, DeeLexerErrors.SPECIAL_TOKEN_INVALID);
	}
	
	protected static final String[] SEEKUNTIL_DOUBLEQUOTES_OR_NL = { "\"", "\r\n", "\r", "\n", };
	
	protected final Void matchSpecialTokenLine() {
		pos+=4;
		
		if(consumeRuleCategorySequence(CharRuleCategory.WHITESPACE) == 0) {
			assertFail();
		}
		if(consumeRuleCategorySequence(CharRuleCategory.DIGIT) == 0) {
			seekToNewline();
			return endMatchWithError(DeeTokens.SPECIAL_TOKEN_LINE, DeeLexerErrors.SPECIAL_TOKEN_LINE_BAD_FORMAT); 
		}
		if(consumeRuleCategorySequence(CharRuleCategory.WHITESPACE) == 0) {
			// It's ok
		}
		
		if(consumeRuleCategoryOnce(CharRuleCategory.DOUBLE_QUOTES) == false) {
			return matchSpecialTokenLine_FromLineEnd();
		}
		
		if(seekTo(SEEKUNTIL_DOUBLEQUOTES_OR_NL) != 0) {
			return endMatchWithError(DeeTokens.SPECIAL_TOKEN_LINE, DeeLexerErrors.SPECIAL_TOKEN_LINE_BAD_FORMAT); 
		}
		
		return matchSpecialTokenLine_FromLineEnd();
	}
	
	protected final Void matchSpecialTokenLine_FromLineEnd() {
		if(readNewlineOrEOF() == -1) {
			seekToNewline(); // BM: This is not according to DMD I think.
			return endMatchWithError(DeeTokens.SPECIAL_TOKEN_LINE, DeeLexerErrors.SPECIAL_TOKEN_LINE_BAD_FORMAT); 
		}
		
		return endMatch(DeeTokens.SPECIAL_TOKEN_LINE);
	}
	
}