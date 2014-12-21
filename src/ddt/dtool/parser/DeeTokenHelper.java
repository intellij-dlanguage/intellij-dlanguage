package ddt.dtool.parser;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeeTokenHelper {
	
	public static final List<DeeTokens> keyWords_All;
	
	public static final List<DeeTokens> keyWords_control;
	public static final List<DeeTokens> keyWords_literalValues;
	public static final List<DeeTokens> keyWords_nativeTypes;
	
	static {
		ArrayList<DeeTokens> allKeyWords_init = new ArrayList<>();
		
		ArrayList<DeeTokens> controlKeyWords_init = new ArrayList<>();
		ArrayList<DeeTokens> literalValueKeyWords_init = new ArrayList<>();
		ArrayList<DeeTokens> nativeTypeKeyWords_init = new ArrayList<>();
		
		for (DeeTokens tokenType : DeeTokens.values()) {
			if(isKeyword(tokenType)) {
				allKeyWords_init.add(tokenType);
				
				switch(getTokenTypeCategory(tokenType)) {
				case CONTROL:
					controlKeyWords_init.add(tokenType);
					break;
				case LITERAL_VALUE:
					literalValueKeyWords_init.add(tokenType);
					break;
				case NATIVE_TYPE:
					nativeTypeKeyWords_init.add(tokenType);
					break;
				}
			}
		}
		
		keyWords_All = Collections.unmodifiableList(allKeyWords_init);
		
		keyWords_control = Collections.unmodifiableList(controlKeyWords_init);
		keyWords_literalValues = Collections.unmodifiableList(literalValueKeyWords_init);
		keyWords_nativeTypes = Collections.unmodifiableList(nativeTypeKeyWords_init);
	}
	
	public static boolean isKeyword(DeeTokens tokenType) {
		TokensCategory category = getTokenTypeCategory(tokenType);
		return 
			category == TokensCategory.NATIVE_TYPE ||
			category == TokensCategory.CONTROL ||
			category == TokensCategory.LITERAL_VALUE;
	}
	
	public static enum TokensCategory { NATIVE_TYPE, CONTROL, LITERAL_VALUE, }
	
	public static TokensCategory getTokenTypeCategory(DeeTokens tokenType) {
		// BM: Add other categories for remaining tokens? They are not nedeed at the moment. 
		// But nonetheless we list all DeeTokens in switch to make sure we dont miss any
		
		switch (tokenType) {
		
		case GROUP_COMMENT:
		case GROUP_STRING:
		case GROUP_INTEGER:
		case GROUP_FLOAT:
		case GROUP_ATTRIBUTE_KW:
		case GROUP_PRIMITIVE_KW:
		case GROUP_PROTECTION_KW:
			return null; // Return null for artificial grouping tokens

		case EOF:
			return null;
			
		case INVALID_TOKEN:
			return null;
			
		
		case LINE_END:
		case WHITESPACE:
			return null;
			
		case COMMENT_MULTI: 
		case COMMENT_NESTED: 
		case COMMENT_LINE:
		case DOCCOMMENT_MULTI: 
		case DOCCOMMENT_NESTED:
		case DOCCOMMENT_LINE:
			return null; // COMMENT
		
		case SCRIPT_LINE_INTRO:
			return null;
		case SPECIAL_TOKEN_LINE:
			return null;
			
		case IDENTIFIER:
			return null;
		
		case KW_BOOL:
		case KW_VOID:
		case KW_BYTE: 
		case KW_UBYTE:
		case KW_SHORT: 
		case KW_USHORT:
		case KW_INT: 
		case KW_UINT: 
		case KW_LONG: 
		case KW_ULONG:
		case KW_CHAR: 
		case KW_WCHAR: 
		case KW_DCHAR:
		case KW_FLOAT: 
		case KW_DOUBLE: 
		case KW_REAL:
		case KW_IFLOAT: 
		case KW_IDOUBLE: 
		case KW_IREAL:  
		case KW_CFLOAT: 
		case KW_CDOUBLE: 
		case KW_CREAL:
		case KW_CENT: 
		case KW_UCENT:  			
		return TokensCategory.NATIVE_TYPE;
		
		case KW_PRIVATE: 
		case KW_PACKAGE: 
		case KW_PROTECTED:
		case KW_PUBLIC: 
		case KW_EXPORT:
		case KW_ABSTRACT:
			
		case KW_CONST:
		case KW_IMMUTABLE:
		case KW_INOUT: 
		case KW_SHARED:
			
		case KW_DEPRECATED: 
		case KW_FINAL: 
		case KW_NOTHROW: 
		case KW_OVERRIDE: 
		case KW_PURE: 
		case KW_SCOPE: 
		case KW_STATIC: 
		case KW_SYNCHRONIZED:
		case KW_REF:
		case KW_AUTO:
			
		case KW_ALIAS: 
		case KW_ALIGN: 
		case KW_ASM: 
		case KW_ASSERT:
		case KW_BODY: 
		case KW_BREAK: 
		case KW_CASE: 
		case KW_CAST: 
		case KW_CATCH: 
		case KW_CLASS: 
		case KW_CONTINUE:
		case KW_DEBUG: 
		case KW_DEFAULT: 
		case KW_DELEGATE: 
		case KW_DELETE:  
		case KW_DO: 
		case KW_ELSE: 
		case KW_ENUM: 
		case KW_EXTERN:
		case KW_FINALLY: 
		case KW_FOR:
		case KW_FOREACH: 
		case KW_FOREACH_REVERSE:
		case KW_FUNCTION:
		case KW_GOTO:
		case KW_IF:
		case KW_IMPORT:
		case KW_IN:
		case KW_INTERFACE:
		case KW_INVARIANT:
		case KW_IS:
		case KW_LAZY:
		case KW_MACRO:
		case KW_MIXIN:
		case KW_MODULE:
		case KW_NEW:
		case KW_OUT:
		case KW_PRAGMA:
		case KW_RETURN:
		case KW_STRUCT:
		case KW_SWITCH:
		case KW_TEMPLATE:
		case KW_THROW:
		case KW_TRY:
		case KW_TYPEDEF:
		case KW_TYPEID:
		case KW_TYPEOF:
		case KW_UNION:
		case KW_UNITTEST:
		case KW_VERSION:
		case KW_VOLATILE:
		case KW_WHILE:
		case KW_WITH:
			
		case KW___TRAITS:
		case KW___GSHARED: 
		case KW___THREAD:
		case KW___VECTOR:
			
			return TokensCategory.CONTROL;
			
		case KW_THIS: 
		case KW_SUPER:
		case KW_TRUE:
		case KW_FALSE: 
		case KW_NULL:
			
		case KW___FILE__: 
		case KW___LINE__: 
		case KW___MODULE__: 
		case KW___FUNCTION__: 
		case KW___PRETTY_FUNCTION__:
		
		case KW___DATE__:
		case KW___TIME__:
		case KW___TIMESTAMP__:
		case KW___VENDOR__:
		case KW___VERSION__:			
			
			return TokensCategory.LITERAL_VALUE;

		case STRING_WYSIWYG:
		case STRING_DQ: 
		case STRING_HEX: 
		case STRING_DELIM: 
		case STRING_TOKENS:
			return null;
			
		case CHARACTER:
			return null;
			
		case INTEGER_DECIMAL: 
		case INTEGER_BINARY: 
		case INTEGER_OCTAL: 
		case INTEGER_HEX:
			return null;
			
		case FLOAT_DECIMAL: 
		case FLOAT_HEX:
			return null;
			
		case OPEN_PARENS:
		case CLOSE_PARENS:
		case OPEN_BRACE:
		case CLOSE_BRACE:
		case OPEN_BRACKET:
		case CLOSE_BRACKET:
		case SEMICOLON:
		case COLON:
			return null;
			
		case QUESTION:
		case COMMA:
		case DOLLAR:
		case AT:
			
		case DOT: 
		case DOUBLE_DOT: 
		case TRIPLE_DOT:
			
		case MINUS:
		case MINUS_ASSIGN:
		case DECREMENT: 
		case PLUS: 
		case PLUS_ASSIGN: 
		case INCREMENT:	
		case DIV: 
		case DIV_ASSIGN: 
		case STAR: 
		case MULT_ASSIGN: 
		case MOD: 
		case MOD_ASSIGN:
		case POW: 
		case POW_ASSIGN: 
			
		case AND: 
		case AND_ASSIGN: 
		case LOGICAL_AND: 
		case OR: 
		case OR_ASSIGN: 
		case LOGICAL_OR: 
		case XOR: 
		case XOR_ASSIGN:
		case CONCAT: 
		case CONCAT_ASSIGN:
			
		case LAMBDA:
			
		case ASSIGN: 
		case EQUALS:
		case NOT: 
		case NOT_EQUAL:
			
		case LESS_THAN: 
		case LESS_EQUAL: 
		case GREATER_THAN: 
		case GREATER_EQUAL:
		case LESS_GREATER: 
		case LESS_GREATER_EQUAL:
		case UNORDERED_E: 
		case UNORDERED: 
		case UNORDERED_GE: 
		case UNORDERED_G: 
		case UNORDERED_LE: 
		case UNORDERED_L:
			
		case LEFT_SHIFT: 
		case LEFT_SHIFT_ASSIGN: 
		case RIGHT_SHIFT: 
		case RIGHT_SHIFT_ASSIGN: 
		case TRIPLE_RSHIFT:
		case TRIPLE_RSHIFT_ASSIGN:
			return null;
			
		} throw assertUnreachable();
	}
	
}