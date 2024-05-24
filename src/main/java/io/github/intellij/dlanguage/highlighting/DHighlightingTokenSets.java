package io.github.intellij.dlanguage.highlighting;

import com.intellij.psi.tree.TokenSet;
import io.github.intellij.dlanguage.psi.DlangTypes;

/**
 * This class defines which DlangTypes can be used for syntax highlighting. Valid types are ones that are
 * used by the highlighting lexer.
 * @see io.github.intellij.dlanguage.lexer.DHighlightingLexer
 */
public class DHighlightingTokenSets {

    //public static final TokenSet SHEBANG = TokenSet.create(DlangTypes.SHEBANG); // todo: get highlighting lexer working with SHEBANG
    public static final TokenSet CHAR = TokenSet.create(DlangTypes.CHARACTER_LITERAL);

    public static final TokenSet LINE_COMMENT = TokenSet.create(DlangTypes.LINE_COMMENT);
    public static final TokenSet LINE_DOCUMENTATION = TokenSet.create(DlangTypes.LINE_DOC);
    public static final TokenSet BLOCK_COMMENT = TokenSet.create(DlangTypes.BLOCK_COMMENT, DlangTypes.NESTING_BLOCK_COMMENT);
    public static final TokenSet BLOCK_DOCUMENTATION = TokenSet.create(DlangTypes.BLOCK_DOC, DlangTypes.NESTING_BLOCK_DOC);

    public static final TokenSet STRING_LITERAL = TokenSet.create(DlangTypes.DOUBLE_QUOTED_STRING,
        DlangTypes.CHARACTER_LITERAL,
        DlangTypes.DELIMITED_STRING,
        DlangTypes.WYSIWYG_STRING,
        DlangTypes.ALTERNATE_WYSIWYG_STRING);
    public static final TokenSet NUMBER = TokenSet.create(DlangTypes.INTEGER_LITERAL, DlangTypes.FLOAT_LITERAL);
    public static final TokenSet SEMICOLON = TokenSet.create(DlangTypes.OP_SCOLON);
    public static final TokenSet DOT = TokenSet.create(DlangTypes.OP_DOT);
    public static final TokenSet COMMA = TokenSet.create(DlangTypes.OP_COMMA);

    public static final TokenSet PARENTHESES = TokenSet.create(DlangTypes.OP_PAR_LEFT, DlangTypes.OP_PAR_RIGHT);
    public static final TokenSet BRACES = TokenSet.create(DlangTypes.OP_BRACES_LEFT, DlangTypes.OP_BRACES_RIGHT);
    public static final TokenSet BRACKETS = TokenSet.create(DlangTypes.OP_BRACKET_LEFT, DlangTypes.OP_BRACKET_RIGHT);

    public static final TokenSet KEYWORD = TokenSet.create(
            DlangTypes.KW_ABSTRACT,
            DlangTypes.KW_ALIAS,
            DlangTypes.KW_ALIGN,
            DlangTypes.KW_ASM,
            DlangTypes.KW_ASSERT,
            DlangTypes.KW_AUTO,
            DlangTypes.KW_BOOL,
            DlangTypes.KW_BREAK,
            DlangTypes.KW_BYTE,
            DlangTypes.KW_CASE,
            DlangTypes.KW_CAST,
            DlangTypes.KW_CATCH,
            DlangTypes.KW_CDOUBLE,
//            DlangTypes.KW_CENT,
            DlangTypes.KW_CFLOAT,
            DlangTypes.KW_CHAR,
            DlangTypes.KW_CLASS,
            DlangTypes.KW_CONST,
            DlangTypes.KW_CONTINUE,
            DlangTypes.KW_CREAL,
            DlangTypes.KW_DCHAR,
            DlangTypes.KW_DEBUG,
            DlangTypes.KW_DEFAULT,
            DlangTypes.KW_DELEGATE,
            DlangTypes.KW_DELETE,
            DlangTypes.KW_DEPRECATED,
            DlangTypes.KW_DO,
            DlangTypes.KW_DOUBLE,
            DlangTypes.KW_ELSE,
            DlangTypes.KW_ENUM,
            DlangTypes.KW_EXPORT,
            DlangTypes.KW_EXTERN,
            DlangTypes.KW_FALSE,
            DlangTypes.KW_FINAL,
            DlangTypes.KW_FINALLY,
            DlangTypes.KW_FLOAT,
            DlangTypes.KW_FOR,
            DlangTypes.KW_FOREACH,
            DlangTypes.KW_FOREACH_REVERSE,
            DlangTypes.KW_FUNCTION,
            DlangTypes.KW_GOTO,
            DlangTypes.KW_IDOUBLE,
            DlangTypes.KW_IF,
            DlangTypes.KW_IFLOAT,
            DlangTypes.KW_IMMUTABLE,
            DlangTypes.KW_IMPORT,
            DlangTypes.KW_IN,
            DlangTypes.KW_INOUT,
            DlangTypes.KW_INT,
            DlangTypes.KW_INTERFACE,
            DlangTypes.KW_INVARIANT,
            DlangTypes.KW_IREAL,
            DlangTypes.KW_IS,
            DlangTypes.KW_LAZY,
            DlangTypes.KW_LONG,
//            DlangTypes.KW_MACRO,
            DlangTypes.KW_MIXIN,
            DlangTypes.KW_MODULE,
            DlangTypes.KW_NEW,
            DlangTypes.KW_NOTHROW,
            DlangTypes.KW_NULL,
            DlangTypes.KW_OUT,
            DlangTypes.KW_OVERRIDE,
            DlangTypes.KW_PACKAGE,
            DlangTypes.KW_PRAGMA,
            DlangTypes.KW_PRIVATE,
            DlangTypes.KW_PROTECTED,
            DlangTypes.KW_PUBLIC,
            DlangTypes.KW_PURE,
            DlangTypes.KW_REAL,
            DlangTypes.KW_REF,
            DlangTypes.KW_RETURN,
            DlangTypes.KW_SCOPE,
            DlangTypes.KW_SHARED,
            DlangTypes.KW_SHORT,
            DlangTypes.KW_STATIC,
            DlangTypes.KW_STRUCT,
            DlangTypes.KW_SUPER,
            DlangTypes.KW_SWITCH,
            DlangTypes.KW_SYNCHRONIZED,
            DlangTypes.KW_TEMPLATE,
            DlangTypes.KW_THIS,
            DlangTypes.KW_THROW,
            DlangTypes.KW_TRUE,
            DlangTypes.KW_TRY,
//            DlangTypes.KW_TYPEDEF,
            DlangTypes.KW_TYPEID,
            DlangTypes.KW_TYPEOF,
            DlangTypes.KW_UBYTE,
//            DlangTypes.KW_UCENT,
            DlangTypes.KW_UINT,
            DlangTypes.KW_ULONG,
            DlangTypes.KW_UNION,
            DlangTypes.KW_UNITTEST,
            DlangTypes.KW_USHORT,
            DlangTypes.KW_VERSION,
            DlangTypes.KW_VOID,
//            DlangTypes.KW_VOLATILE,
            DlangTypes.KW_WCHAR,
            DlangTypes.KW_WHILE,
            DlangTypes.KW_WITH,
            DlangTypes.KW___FILE__,
            DlangTypes.KW___LINE__,
            DlangTypes.KW___GSHARED,
            DlangTypes.KW___TRAITS
//            DlangTypes.KW___VECTOR
    );

    public static final TokenSet OPERATOR = TokenSet.create(
            DlangTypes.OP_COLON,
            DlangTypes.OP_EQ,
            DlangTypes.OP_ASTERISK,
            DlangTypes.OP_DDOT,
            DlangTypes.OP_TRIPLEDOT,
            DlangTypes.OP_AT,
            DlangTypes.OP_PLUS_EQ,
            DlangTypes.OP_MINUS_EQ,
            DlangTypes.OP_MUL_EQ,
            DlangTypes.OP_DIV_EQ,
            DlangTypes.OP_MOD_EQ,
            DlangTypes.OP_AND_EQ,
            DlangTypes.OP_OR_EQ,
            DlangTypes.OP_XOR_EQ,
            DlangTypes.OP_TILDA_EQ,
            DlangTypes.OP_SH_LEFT_EQ,
            DlangTypes.OP_SH_RIGHT_EQ,
            DlangTypes.OP_USH_RIGHT_EQ,
            DlangTypes.OP_POW_EQ,
            DlangTypes.OP_QUEST,
            DlangTypes.OP_BOOL_OR,
            DlangTypes.OP_BOOL_AND,
            DlangTypes.OP_OR,
            DlangTypes.OP_XOR,
            DlangTypes.OP_SH_LEFT,
            DlangTypes.OP_SH_RIGHT,
            DlangTypes.OP_USH_RIGHT,
            DlangTypes.OP_PLUS,
            DlangTypes.OP_MINUS,
            DlangTypes.OP_TILDA,
            DlangTypes.OP_DIV,
            DlangTypes.OP_MOD,
            DlangTypes.OP_AND,
            DlangTypes.OP_PLUS_PLUS,
            DlangTypes.OP_MINUS_MINUS,
            DlangTypes.OP_NOT,
            DlangTypes.OP_POW,
            DlangTypes.OP_DOLLAR,
            DlangTypes.OP_EQ_EQ,
            DlangTypes.OP_NOT_EQ,
            DlangTypes.OP_LESS,
            DlangTypes.OP_LESS_EQ,
            DlangTypes.OP_GT,
            DlangTypes.OP_GT_EQ
    );
}
