package net.masterthought.dlanguage.highlighting;

import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.psi.DLanguageTypes;

public class DHighlightingTokenSets {


    public static final TokenSet KEYWORD = TokenSet.create(DLanguageTypes.KEYWORD);
    public static final TokenSet NUMBER = TokenSet.create(DLanguageTypes.NUMBER);
    public static final TokenSet STRING = TokenSet.create(DLanguageTypes.STRING);
    public static final TokenSet OPERATOR = TokenSet.create(DLanguageTypes.OPERATOR);
    public static final TokenSet PARENTHESES = TokenSet.create(DLanguageTypes.PARENTHESES);
    public static final TokenSet BRACES = TokenSet.create(DLanguageTypes.BRACES);
    public static final TokenSet BRACKETS = TokenSet.create(DLanguageTypes.BRACKETS);
    public static final TokenSet COMMA = TokenSet.create(DLanguageTypes.COMMA);
    public static final TokenSet SEMICOLON = TokenSet.create(DLanguageTypes.SEMICOLON);
    public static final TokenSet DOT = TokenSet.create(DLanguageTypes.DOT);
    public static final TokenSet LINE_COMMENT = TokenSet.create(DLanguageTypes.LINE_COMMENT);
    public static final TokenSet BLOCK_COMMENT = TokenSet.create(DLanguageTypes.BLOCK_COMMENT);
    public static final TokenSet MODULE_DEFINITION = TokenSet.create(DLanguageTypes.MODULE_DEFINITION);



//    public static final TokenSet WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE);
//
//    public static final TokenSet LINE_COMMENTS = TokenSet.create(DLanguageTypes.LINE_COMMENT);
//
//    public static final TokenSet BLOCK_COMMENTS = TokenSet.create(DLanguageTypes.BLOCK_COMMENT,DLanguageTypes.NESTING_BLOCK_COMMENT);
//
//    public static final TokenSet STRING_LITERALS = TokenSet.create(DLanguageTypes.DOUBLE_QUOTED_STRING,
//            DLanguageTypes.KW_CHAR,
//            DLanguageTypes.KW_DCHAR,
//            DLanguageTypes.KW_WCHAR,
//            DLanguageTypes.STRING_LITERAL,
//            DLanguageTypes.STRING_LITERALS,
//            DLanguageTypes.HEX_STRING,
//            DLanguageTypes.CHARACTER_LITERAL,
//            DLanguageTypes.DELIMITED_STRING,
//            DLanguageTypes.WYSIWYG_STRING,
//            DLanguageTypes.ALTERNATE_WYSIWYG_STRING);
//    public static final TokenSet INTEGER_LITERALS = TokenSet.create(DLanguageTypes.INTEGER_LITERAL);
//    public static final TokenSet FLOAT_LITERALS = TokenSet.create(DLanguageTypes.FLOAT_LITERAL);
//
//    public static final TokenSet PARENS = TokenSet.create(DLanguageTypes.OP_PAR_LEFT, DLanguageTypes.OP_PAR_RIGHT);
//    public static final TokenSet BRACE = TokenSet.create(DLanguageTypes.OP_BRACES_LEFT, DLanguageTypes.OP_BRACES_RIGHT);
//    public static final TokenSet BRACKET = TokenSet.create(DLanguageTypes.OP_BRACKET_LEFT, DLanguageTypes.OP_BRACKET_RIGHT);
//
//    public static final TokenSet KEYWORD = TokenSet.create(
//            DLanguageTypes.KW_ABSTRACT,
//            DLanguageTypes.KW_ALIAS,
//            DLanguageTypes.KW_ALIGN,
//            DLanguageTypes.KW_ASM,
//            DLanguageTypes.KW_ASSERT,
//            DLanguageTypes.KW_AUTO,
//            DLanguageTypes.KW_BODY,
//            DLanguageTypes.KW_BOOL,
//            DLanguageTypes.KW_BREAK,
//            DLanguageTypes.KW_BYTE,
//            DLanguageTypes.KW_CASE,
//            DLanguageTypes.KW_CAST,
//            DLanguageTypes.KW_CATCH,
//            DLanguageTypes.KW_CDOUBLE,
////            DLanguageTypes.KW_CENT,
//            DLanguageTypes.KW_CFLOAT,
//            DLanguageTypes.KW_CHAR,
//            DLanguageTypes.KW_CLASS,
//            DLanguageTypes.KW_CONST,
//            DLanguageTypes.KW_CONTINUE,
//            DLanguageTypes.KW_CREAL,
//            DLanguageTypes.KW_DCHAR,
//            DLanguageTypes.KW_DEBUG,
//            DLanguageTypes.KW_DEFAULT,
//            DLanguageTypes.KW_DELEGATE,
//            DLanguageTypes.KW_DELETE,
//            DLanguageTypes.KW_DEPRECATED,
//            DLanguageTypes.KW_DO,
//            DLanguageTypes.KW_DOUBLE,
//            DLanguageTypes.KW_ELSE,
//            DLanguageTypes.KW_ENUM,
//            DLanguageTypes.KW_EXPORT,
//            DLanguageTypes.KW_EXTERN,
//            DLanguageTypes.KW_FALSE,
//            DLanguageTypes.KW_FINAL,
//            DLanguageTypes.KW_FINALLY,
//            DLanguageTypes.KW_FLOAT,
//            DLanguageTypes.KW_FOR,
//            DLanguageTypes.KW_FOREACH,
//            DLanguageTypes.KW_FOREACH_REVERSE,
//            DLanguageTypes.KW_FUNCTION,
//            DLanguageTypes.KW_GOTO,
//            DLanguageTypes.KW_IDOUBLE,
//            DLanguageTypes.KW_IF,
//            DLanguageTypes.KW_IFLOAT,
//            DLanguageTypes.KW_IMMUTABLE,
//            DLanguageTypes.KW_IMPORT,
//            DLanguageTypes.KW_IN,
//            DLanguageTypes.KW_INOUT,
//            DLanguageTypes.KW_INT,
//            DLanguageTypes.KW_INTERFACE,
//            DLanguageTypes.KW_INVARIANT,
//            DLanguageTypes.KW_IREAL,
//            DLanguageTypes.KW_IS,
//            DLanguageTypes.KW_LAZY,
//            DLanguageTypes.KW_LONG,
////            DLanguageTypes.KW_MACRO,
//            DLanguageTypes.KW_MIXIN,
//            DLanguageTypes.KW_MODULE,
//            DLanguageTypes.KW_NEW,
//            DLanguageTypes.KW_NOTHROW,
//            DLanguageTypes.KW_NULL,
//            DLanguageTypes.KW_OUT,
//            DLanguageTypes.KW_OVERRIDE,
//            DLanguageTypes.KW_PACKAGE,
//            DLanguageTypes.KW_PRAGMA,
//            DLanguageTypes.KW_PRIVATE,
//            DLanguageTypes.KW_PROTECTED,
//            DLanguageTypes.KW_PUBLIC,
//            DLanguageTypes.KW_PURE,
//            DLanguageTypes.KW_REAL,
//            DLanguageTypes.KW_REF,
//            DLanguageTypes.KW_RETURN,
//            DLanguageTypes.KW_SCOPE,
//            DLanguageTypes.KW_SHARED,
//            DLanguageTypes.KW_SHORT,
//            DLanguageTypes.KW_STATIC,
//            DLanguageTypes.KW_STRUCT,
//            DLanguageTypes.KW_SUPER,
//            DLanguageTypes.KW_SWITCH,
//            DLanguageTypes.KW_SYNCHRONIZED,
//            DLanguageTypes.KW_TEMPLATE,
//            DLanguageTypes.KW_THIS,
//            DLanguageTypes.KW_THROW,
//            DLanguageTypes.KW_TRUE,
//            DLanguageTypes.KW_TRY,
////            DLanguageTypes.KW_TYPEDEF,
//            DLanguageTypes.KW_TYPEID,
//            DLanguageTypes.KW_TYPEOF,
//            DLanguageTypes.KW_UBYTE,
////            DLanguageTypes.KW_UCENT,
//            DLanguageTypes.KW_UINT,
//            DLanguageTypes.KW_ULONG,
//            DLanguageTypes.KW_UNION,
//            DLanguageTypes.KW_UNITTEST,
//            DLanguageTypes.KW_USHORT,
//            DLanguageTypes.KW_VERSION,
//            DLanguageTypes.KW_VOID,
////            DLanguageTypes.KW_VOLATILE,
//            DLanguageTypes.KW_WCHAR,
//            DLanguageTypes.KW_WHILE,
//            DLanguageTypes.KW_WITH,
//            DLanguageTypes.KW___FILE__,
//            DLanguageTypes.KW___LINE__,
//            DLanguageTypes.KW___GSHARED,
//            DLanguageTypes.KW___TRAITS
////            DLanguageTypes.KW___VECTOR
//    );
//
//    public static final TokenSet OPERATOR = TokenSet.create(
//            DLanguageTypes.OP_SCOLON,
//            DLanguageTypes.OP_COLON,
//            DLanguageTypes.OP_EQ,
//            DLanguageTypes.OP_COMMA,
//            DLanguageTypes.OP_PAR_LEFT,
//            DLanguageTypes.OP_PAR_RIGHT,
//            DLanguageTypes.OP_BRACKET_LEFT,
//            DLanguageTypes.OP_BRACKET_RIGHT,
//            DLanguageTypes.OP_BRACES_LEFT,
//            DLanguageTypes.OP_BRACES_RIGHT,
//            DLanguageTypes.OP_ASTERISK,
//            DLanguageTypes.OP_DDOT,
//            DLanguageTypes.OP_TRIPLEDOT,
//            DLanguageTypes.OP_AT,
//            DLanguageTypes.OP_PLUS_EQ,
//            DLanguageTypes.OP_MINUS_EQ,
//            DLanguageTypes.OP_MUL_EQ,
//            DLanguageTypes.OP_DIV_EQ,
//            DLanguageTypes.OP_MOD_EQ,
//            DLanguageTypes.OP_AND_EQ,
//            DLanguageTypes.OP_OR_EQ,
//            DLanguageTypes.OP_XOR_EQ,
//            DLanguageTypes.OP_TILDA_EQ,
//            DLanguageTypes.OP_SH_LEFT_EQ,
//            DLanguageTypes.OP_SH_RIGHT_EQ,
//            DLanguageTypes.OP_USH_RIGHT_EQ,
//            DLanguageTypes.OP_POW_EQ,
//            DLanguageTypes.OP_QUEST,
//            DLanguageTypes.OP_BOOL_OR,
//            DLanguageTypes.OP_BOOL_AND,
//            DLanguageTypes.OP_OR,
//            DLanguageTypes.OP_XOR,
//            DLanguageTypes.OP_SH_LEFT,
//            DLanguageTypes.OP_SH_RIGHT,
//            DLanguageTypes.OP_USH_RIGHT,
//            DLanguageTypes.OP_PLUS,
//            DLanguageTypes.OP_MINUS,
//            DLanguageTypes.OP_TILDA,
//            DLanguageTypes.OP_DIV,
//            DLanguageTypes.OP_MOD,
//            DLanguageTypes.OP_AND,
//            DLanguageTypes.OP_PLUS_PLUS,
//            DLanguageTypes.OP_MINUS_MINUS,
//            DLanguageTypes.OP_NOT,
//            DLanguageTypes.OP_POW,
//            DLanguageTypes.OP_DOLLAR,
//            DLanguageTypes.OP_EQ_EQ,
//            DLanguageTypes.OP_NOT_EQ,
//            DLanguageTypes.OP_LESS,
//            DLanguageTypes.OP_LESS_EQ,
//            DLanguageTypes.OP_GT,
//            DLanguageTypes.OP_GT_EQ,
//            DLanguageTypes.OP_UNORD,
//            DLanguageTypes.OP_UNORD_EQ,
//            DLanguageTypes.LAMBDA
//    );
}
