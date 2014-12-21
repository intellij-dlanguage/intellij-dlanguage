package net.masterthought.dlanguage.psi;


import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import ddt.dtool.parser.DeeTokens;

import java.util.HashMap;
import java.util.Map;

/**
 * Type of tokens valid for the D language, mapping ParserD types to
 * DElementType objects.
 * Dynamically creates one instance of a DElementType for each ParserD type as
 * valueOf() is called.  Certain tokens are created ahead of time, to allow for easier
 * reference elsewhere - such as comment and whitespace tokens.
 * <p/>
 * TODO: use parent psi.TokenType values?
 */
public abstract class DLanguageTokenType implements com.intellij.psi.TokenType {

    private static Map<DeeTokens, DLanguageElementType> tokens;


    // Some explicit tokens
//    public static final IElementType UNKNOWN = valueOf("Unknown");

    public static final DLanguageElementType KW_MODULE = valueOf(DeeTokens.KW_MODULE);
    public static final DLanguageElementType KW_IMPORT = valueOf(DeeTokens.KW_IMPORT);
    public static final DLanguageElementType LINE_END = valueOf(DeeTokens.LINE_END);
    public static final DLanguageElementType SEMICOLON = valueOf(DeeTokens.SEMICOLON);

    public static final DLanguageElementType LINE_COMMENT = valueOf(DeeTokens.COMMENT_LINE);
    public static final DLanguageElementType DOC_LINE_COMMENT = valueOf(DeeTokens.DOCCOMMENT_LINE);
    public static final DLanguageElementType BLOCK_COMMENT = valueOf(DeeTokens.COMMENT_MULTI);
    public static final DLanguageElementType BLOCK_COMMENT_NEST = valueOf(DeeTokens.COMMENT_NESTED);
    public static final DLanguageElementType DOC_COMMENT = valueOf(DeeTokens.DOCCOMMENT_MULTI);
    public static final DLanguageElementType DOC_COMMENT_NEST = valueOf(DeeTokens.DOCCOMMENT_NESTED);


    public static final DLanguageElementType CHAR_LITERAL = valueOf(DeeTokens.KW_CHAR);
    public static final DLanguageElementType STRING_LITERAL = valueOf(DeeTokens.STRING_DQ);
    public static final DLanguageElementType WSTR_LITERAL = valueOf(DeeTokens.KW_DCHAR);
    public static final DLanguageElementType DSTR_LITERAL = valueOf(DeeTokens.KW_WCHAR);


    public static final DLanguageElementType OPEN_PARENS = valueOf(DeeTokens.OPEN_PARENS);
    public static final DLanguageElementType CLOSE_PARENS = valueOf(DeeTokens.CLOSE_PARENS);
    public static final DLanguageElementType OPEN_BRACE = valueOf(DeeTokens.OPEN_BRACE);
    public static final DLanguageElementType CLOSE_BRACE = valueOf(DeeTokens.CLOSE_BRACE);
    public static final DLanguageElementType OPEN_BRACKET = valueOf(DeeTokens.OPEN_BRACKET);
    public static final DLanguageElementType CLOSE_BRACKET = valueOf(DeeTokens.CLOSE_BRACKET);


    public static final DLanguageElementType IDENTIFIER = valueOf(DeeTokens.IDENTIFIER);

    // Token sets for special treatment while parsing
    public static final TokenSet WHITESPACES = TokenSet.create(valueOf(DeeTokens.EOF),
            valueOf(DeeTokens.WHITESPACE));

    public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT,
            DOC_LINE_COMMENT,
            BLOCK_COMMENT,
            BLOCK_COMMENT_NEST,
            DOC_COMMENT,
            DOC_COMMENT_NEST);
    public static final TokenSet STRING_LITERALS = TokenSet.create(
            valueOf(DeeTokens.STRING_WYSIWYG),
            valueOf(DeeTokens.STRING_DELIM),
            CHAR_LITERAL,
            WSTR_LITERAL,
            DSTR_LITERAL,
            STRING_LITERAL);

    public static final TokenSet INTEGER_LITERALS = TokenSet.create(valueOf(DeeTokens.INTEGER_DECIMAL),
            valueOf(DeeTokens.INTEGER_BINARY),
            valueOf(DeeTokens.INTEGER_OCTAL),
            valueOf(DeeTokens.INTEGER_HEX));

    public static final TokenSet FLOAT_LITERALS = TokenSet.create(valueOf(DeeTokens.FLOAT_DECIMAL),
            valueOf(DeeTokens.FLOAT_HEX));

    public static final TokenSet PARENS = TokenSet.create(OPEN_PARENS,CLOSE_PARENS);
    public static final TokenSet BRACE = TokenSet.create(OPEN_BRACE,CLOSE_BRACE);
    public static final TokenSet BRACKET = TokenSet.create(OPEN_BRACKET,CLOSE_BRACKET);

    public static final TokenSet KEYWORD;
    public static final TokenSet OPERATOR;

    static {
        KEYWORD = TokenSet.create(
                valueOf(DeeTokens.KW_ABSTRACT),
                valueOf(DeeTokens.KW_ALIAS),
                valueOf(DeeTokens.KW_ALIGN),
                valueOf(DeeTokens.KW_ASM),
                valueOf(DeeTokens.KW_ASSERT),
                valueOf(DeeTokens.KW_AUTO),
                valueOf(DeeTokens.KW_BODY),
                valueOf(DeeTokens.KW_BOOL),
                valueOf(DeeTokens.KW_BREAK),
                valueOf(DeeTokens.KW_BYTE),
                valueOf(DeeTokens.KW_CASE),
                valueOf(DeeTokens.KW_CAST),
                valueOf(DeeTokens.KW_CATCH),
                valueOf(DeeTokens.KW_CDOUBLE),
                valueOf(DeeTokens.KW_CENT),
                valueOf(DeeTokens.KW_CFLOAT),
                valueOf(DeeTokens.KW_CHAR),
                valueOf(DeeTokens.KW_CLASS),
                valueOf(DeeTokens.KW_CONST),
                valueOf(DeeTokens.KW_CONTINUE),
                valueOf(DeeTokens.KW_CREAL),
                valueOf(DeeTokens.KW_DCHAR),
                valueOf(DeeTokens.KW_DEBUG),
                valueOf(DeeTokens.KW_DEFAULT),
                valueOf(DeeTokens.KW_DELEGATE),
                valueOf(DeeTokens.KW_DELETE),
                valueOf(DeeTokens.KW_DEPRECATED),
                valueOf(DeeTokens.KW_DO),
                valueOf(DeeTokens.KW_DOUBLE),
                valueOf(DeeTokens.KW_ELSE),
                valueOf(DeeTokens.KW_ENUM),
                valueOf(DeeTokens.KW_EXPORT),
                valueOf(DeeTokens.KW_EXTERN),
                valueOf(DeeTokens.KW_FALSE),
                valueOf(DeeTokens.KW_FINAL),
                valueOf(DeeTokens.KW_FINALLY),
                valueOf(DeeTokens.KW_FLOAT),
                valueOf(DeeTokens.KW_FOR),
                valueOf(DeeTokens.KW_FOREACH),
                valueOf(DeeTokens.KW_FOREACH_REVERSE),
                valueOf(DeeTokens.KW_FUNCTION),
                valueOf(DeeTokens.KW_GOTO),
                valueOf(DeeTokens.KW_IDOUBLE),
                valueOf(DeeTokens.KW_IF),
                valueOf(DeeTokens.KW_IFLOAT),
                valueOf(DeeTokens.KW_IMMUTABLE),
                valueOf(DeeTokens.KW_IMPORT),
                valueOf(DeeTokens.KW_IN),
                valueOf(DeeTokens.KW_INOUT),
                valueOf(DeeTokens.KW_INT),
                valueOf(DeeTokens.KW_INTERFACE),
                valueOf(DeeTokens.KW_INVARIANT),
                valueOf(DeeTokens.KW_IREAL),
                valueOf(DeeTokens.KW_IS),
                valueOf(DeeTokens.KW_LAZY),
                valueOf(DeeTokens.KW_LONG),
                valueOf(DeeTokens.KW_MACRO),
                valueOf(DeeTokens.KW_MIXIN),
                KW_MODULE,
                valueOf(DeeTokens.KW_NEW),
                valueOf(DeeTokens.KW_NOTHROW),
                valueOf(DeeTokens.KW_NULL),
                valueOf(DeeTokens.KW_OUT),
                valueOf(DeeTokens.KW_OVERRIDE),
                valueOf(DeeTokens.KW_PACKAGE),
                valueOf(DeeTokens.KW_PRAGMA),
                valueOf(DeeTokens.KW_PRIVATE),
                valueOf(DeeTokens.KW_PROTECTED),
                valueOf(DeeTokens.KW_PUBLIC),
                valueOf(DeeTokens.KW_PURE),
                valueOf(DeeTokens.KW_REAL),
                valueOf(DeeTokens.KW_REF),
                valueOf(DeeTokens.KW_RETURN),
                valueOf(DeeTokens.KW_SCOPE),
                valueOf(DeeTokens.KW_SHARED),
                valueOf(DeeTokens.KW_SHORT),
                valueOf(DeeTokens.KW_STATIC),
                valueOf(DeeTokens.KW_STRUCT),
                valueOf(DeeTokens.KW_SUPER),
                valueOf(DeeTokens.KW_SWITCH),
                valueOf(DeeTokens.KW_SYNCHRONIZED),
                valueOf(DeeTokens.KW_TEMPLATE),
                valueOf(DeeTokens.KW_THIS),
                valueOf(DeeTokens.KW_THROW),
                valueOf(DeeTokens.KW_TRUE),
                valueOf(DeeTokens.KW_TRY),
                valueOf(DeeTokens.KW_TYPEDEF),
                valueOf(DeeTokens.KW_TYPEID),
                valueOf(DeeTokens.KW_TYPEOF),
                valueOf(DeeTokens.KW_UBYTE),
                valueOf(DeeTokens.KW_UCENT),
                valueOf(DeeTokens.KW_UINT),
                valueOf(DeeTokens.KW_ULONG),
                valueOf(DeeTokens.KW_UNION),
                valueOf(DeeTokens.KW_UNITTEST),
                valueOf(DeeTokens.KW_USHORT),
                valueOf(DeeTokens.KW_VERSION),
                valueOf(DeeTokens.KW_VOID),
                valueOf(DeeTokens.KW_VOLATILE),
                valueOf(DeeTokens.KW_WCHAR),
                valueOf(DeeTokens.KW_WHILE),
                valueOf(DeeTokens.KW_WITH),
                valueOf(DeeTokens.KW___FILE__),
                valueOf(DeeTokens.KW___LINE__),
                valueOf(DeeTokens.KW___GSHARED),
                valueOf(DeeTokens.KW___TRAITS),
                valueOf(DeeTokens.KW___VECTOR)
        );
    }

    static {
        OPERATOR = TokenSet.create(
                valueOf(DeeTokens.ASSIGN),
                valueOf(DeeTokens.PLUS),
                valueOf(DeeTokens.MINUS),
                valueOf(DeeTokens.STAR),
                valueOf(DeeTokens.DIV),
                valueOf(DeeTokens.MOD),
                valueOf(DeeTokens.COLON),
                valueOf(DeeTokens.DOUBLE_DOT),
                valueOf(DeeTokens.SEMICOLON),
                valueOf(DeeTokens.QUESTION),
                valueOf(DeeTokens.DOLLAR),
                valueOf(DeeTokens.COMMA),
                valueOf(DeeTokens.DOT),
                valueOf(DeeTokens.GREATER_THAN),
                valueOf(DeeTokens.LESS_THAN),
                valueOf(DeeTokens.NOT),
                valueOf(DeeTokens.LOGICAL_AND),
                valueOf(DeeTokens.LOGICAL_OR),
                valueOf(DeeTokens.CONCAT),
                valueOf(DeeTokens.AND),
                valueOf(DeeTokens.OR),
                valueOf(DeeTokens.XOR),
                valueOf(DeeTokens.INCREMENT),
                valueOf(DeeTokens.DECREMENT),
                valueOf(DeeTokens.EQUALS),
                valueOf(DeeTokens.NOT_EQUAL),
                valueOf(DeeTokens.GREATER_EQUAL),
                valueOf(DeeTokens.LESS_EQUAL),
                valueOf(DeeTokens.LEFT_SHIFT),
                valueOf(DeeTokens.PLUS_ASSIGN),
                valueOf(DeeTokens.MINUS_ASSIGN),
                valueOf(DeeTokens.MULT_ASSIGN),
                valueOf(DeeTokens.DIV_ASSIGN),
                valueOf(DeeTokens.MOD_ASSIGN),
                valueOf(DeeTokens.AND_ASSIGN),
                valueOf(DeeTokens.OR_ASSIGN),
                valueOf(DeeTokens.XOR_ASSIGN),
                valueOf(DeeTokens.LEFT_SHIFT_ASSIGN),
                valueOf(DeeTokens.CONCAT_ASSIGN),
                valueOf(DeeTokens.RIGHT_SHIFT_ASSIGN),
                valueOf(DeeTokens.TRIPLE_RSHIFT_ASSIGN),
                valueOf(DeeTokens.POW),
                valueOf(DeeTokens.POW_ASSIGN),
                valueOf(DeeTokens.UNORDERED),
                valueOf(DeeTokens.UNORDERED_E),
                valueOf(DeeTokens.LESS_GREATER),
                valueOf(DeeTokens.LESS_GREATER_EQUAL),
                valueOf(DeeTokens.UNORDERED_GE),
                valueOf(DeeTokens.UNORDERED_L),
                valueOf(DeeTokens.UNORDERED_LE),
                valueOf(DeeTokens.UNORDERED_G),
                valueOf(DeeTokens.RIGHT_SHIFT),
                valueOf(DeeTokens.TRIPLE_RSHIFT),
                valueOf(DeeTokens.TRIPLE_DOT),
                valueOf(DeeTokens.AT),
                valueOf(DeeTokens.LAMBDA)
        );
    }


    // Find the DElementType for the given ParseD token type.
    // Multiple calls with the same type will result in the same DElementType instance being returned.
    public static synchronized DLanguageElementType valueOf(DeeTokens type) {
        if (type == null) {
            return null;
        }
        if (tokens == null) {
            tokens = new HashMap<DeeTokens, DLanguageElementType>();
        }

        // Check the cache map for a matching token
        DLanguageElementType tokenType = tokens.get(type);

        // If not in the map yet, create one and shove it in
        if (tokenType == null) {
            tokenType = new DLanguageElementType(type);
            tokens.put(type, tokenType);
        }

        return tokenType;
    }

    // TODO: less ugliness
    public static TokenSet findSet(IElementType type) {
        TokenSet set = null;
        if (COMMENTS.contains(type)) {
            set = COMMENTS;
        }
        if (PARENS.contains(type)) {
            set = PARENS;
        }
        if (BRACE.contains(type)) {
            set = BRACE;
        }
        if (BRACKET.contains(type)) {
            set = BRACKET;
        }
        if (OPERATOR.contains(type)) {
            set = OPERATOR;
        }
        if (KEYWORD.contains(type)) {
            set = KEYWORD;
        }
        if (STRING_LITERALS.contains(type)) {
            set = STRING_LITERALS;
        }
        if (INTEGER_LITERALS.contains(type)) {
            set = INTEGER_LITERALS;
        }
        if (FLOAT_LITERALS.contains(type)) {
            set = FLOAT_LITERALS;
        }
        if (WHITESPACES.contains(type)) {
            set = WHITESPACES;
        }
        return set;
    }

}
