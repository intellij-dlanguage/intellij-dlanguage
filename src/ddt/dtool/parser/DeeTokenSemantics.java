package ddt.dtool.parser;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.List;
import java.util.Map;

import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast_actual.ParserErrorTypes;
import ddt.melnorme.utilbox.misc.Pair;
import ddt.dtool.ast.declarations.AttribProtection.EProtection;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;
import ddt.dtool.parser.common.Token;
import ddt.dtool.util.NewUtils;

public class DeeTokenSemantics {
	
	public static void checkTokenErrors(Token token, List<ParserError> lexerErrors) {
		if(token.getType() == DeeTokens.INVALID_TOKEN) {
			lexerErrors.add(createError(ParserErrorTypes.INVALID_TOKEN_CHARACTERS, token, null));
			return;
		}
		
		if(token.getError() != null) {
			lexerErrors.add(createError(ParserErrorTypes.MALFORMED_TOKEN, token, token.getError()));
			return;
		}
		
		// Check token content validity  TODO: strings, unicode escapes, HTML entities, etc.
		switch (token.getType()) {
		case CHARACTER:
			assertTrue(token.getSourceValue().length() > 2);
			if(token.getSourceValue().length() == 3)
				break;
			if(token.getSourceValue().charAt(1) == '\\') {
				break;
			}
			lexerErrors.add(createError(ParserErrorTypes.MALFORMED_TOKEN, token, 
				DeeLexerErrors.CHAR_LITERAL_SIZE_GREATER_THAN_ONE));
			break;
		default:
			break;
		}
	}
	
	public static ParserError createError(ParserErrorTypes errorType, IToken token, Object msgData) {
		return new ParserError(errorType, token.getSourceRange(), token.getSourceValue(), msgData);
	}
	
	public static EProtection getProtectionFromToken(DeeTokens token) {
		switch(token) {
		case KW_PRIVATE: return EProtection.PRIVATE;
		case KW_PACKAGE: return EProtection.PACKAGE;
		case KW_PROTECTED: return EProtection.PROTECTED;
		case KW_PUBLIC: return EProtection.PUBLIC;
		case KW_EXPORT: return EProtection.EXPORT;
		default: return null;
		}
	}
	
	protected static final Map<String, Boolean> traitsIdMapper = NewUtils.newHashMap(
		Pair.create("isAbstractClass", true),
		Pair.create("isArithmetic", true),
		Pair.create("isAssociativeArray", true),
		Pair.create("isFinalClass", true),
		Pair.create("isPOD", true),
		Pair.create("isNested", true),
		Pair.create("isFloating", true),
		Pair.create("isIntegral", true),
		Pair.create("isScalar", true),
		Pair.create("isStaticArray", true),
		Pair.create("isUnsigned", true),
		Pair.create("isVirtualFunction", true),
		Pair.create("isVirtualMethod", true),
		Pair.create("isAbstractFunction", true),
		Pair.create("isFinalFunction", true),
		Pair.create("isStaticFunction", true),
		Pair.create("isRef", true),
		Pair.create("isOut", true),
		Pair.create("isLazy", true),
		Pair.create("hasMember", true),
		Pair.create("identifier", true),
		Pair.create("getAttributes", true),
		Pair.create("getMember", true),
		Pair.create("getOverloads", true),
		Pair.create("getProtection", true),
		Pair.create("getVirtualFunctions", true),
		Pair.create("getVirtualMethods", true),
		Pair.create("parent", true),
		Pair.create("classInstanceSize", true),
		Pair.create("allMembers", true),
		Pair.create("derivedMembers", true),
		Pair.create("isSame", true),
		Pair.create("compiles", true)
	);
	
	public static ParserError checkTraitsId(BaseLexElement traitsId) {
		if(traitsId.getMissingError() != null) {
			return traitsId.getMissingError();
		}
		
		if(traitsIdMapper.get(traitsId.getSourceValue()) != null) {
			return null;
		}
		return createError(ParserErrorTypes.INVALID_TRAITS_ID, traitsId, null);
	}
	
	protected static final Map<String, Boolean> attribIdMapper = NewUtils.newHashMap(
		Pair.create("property", true),
		Pair.create("safe", true),
		Pair.create("trusted", true),
		Pair.create("system", true),
		Pair.create("disable", true)
	);
	
	public static boolean isPredefinedAttribId(BaseLexElement attribId) {
		return attribIdMapper.get(attribId.getSourceValue()) != null;
	}
	
	public static boolean tokenIsDocComment(Token token) {
		return tokenTypeIsDocComment(token.type);
	}
	
	public static boolean tokenTypeIsDocComment(DeeTokens type) {
		return type == DeeTokens.DOCCOMMENT_LINE ||
			type == DeeTokens.DOCCOMMENT_NESTED || type == DeeTokens.DOCCOMMENT_MULTI;
	}

}