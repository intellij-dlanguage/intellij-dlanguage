package io.github.intellij.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static io.github.intellij.dlanguage.psi.DlangTypes.*;
import io.github.intellij.dlanguage.psi.DlangTypes;

%%

%{

  private int nestedCommentDepth = 0;
  private int nestedDocDepth = 0;
  private int tokenStringDepth = 0;

  public DlangLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class DlangLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

WHITE_SPACE_CHAR = [\ \t\f]
NEW_LINE = [\n\r]+

LETTER = [:letter:]
DIGIT = [:digit:]
ID = (_|{LETTER}) (_|{DIGIT}|{LETTER})*

LINE_COMMENT="//".*
//BLOCK_COMMENT="/"\*(.|\n)*\*"/"

BLOCK_COMMENT_START = "/*"
BLOCK_COMMENT_END = "*/"

SHEBANG = "#!" ([^\r\n])* (\r|\n|\r\n)?

/* JFlex doesn't support recursive rules. So NESTING_BLOCK_COMMENT doesn't support nesting now. */
NESTING_BLOCK_COMMENT_START = \/\+
NESTING_BLOCK_COMMENT_END = \+\/
/*
NESTING_BLOCK_COMMENT = "/+" {NESTING_BLOCK_COMMENT_CONTENT} "+/"
NESTING_BLOCK_COMMENT_CONTENT = ( [^+] | "+"+ [^/] )*
*/


HEX_DIGIT = [0-9abcdefABCDEF]
HEX_DIGIT_US = {HEX_DIGIT} | _
OCTAL_DIGIT = [0-7]
NAMED_CHARACTER_ENTITY = "&" {ID} ";"

WYSIWYG_STRING = "r"\" [^\"]* \" {STRING_POSTFIX}?
ALTERNATE_WYSIWYG_STRING = ` [^`]* ` {STRING_POSTFIX}?
DOUBLE_QUOTED_STRING = \" ( [^\\\"] |{ESCAPE_SEQUENCE})* \" {STRING_POSTFIX}?
DELIMITED_STRING = ({DELIMITED_STRING_SQ_BR} | {DELIMITED_STRING_PARENTH}
                  | {DELIMITED_STRING_ANGLE_PARENTH} | {DELIMITED_STRING_BRACE}) {STRING_POSTFIX}?

DELIMITED_STRING_SQ_BR =         q\"\[ ([^\]] | \][^\"])* \]\"
DELIMITED_STRING_PARENTH =       q\"\( ([^\)] | \)[^\"])* \)\"
DELIMITED_STRING_ANGLE_PARENTH = q\"\< ([^\>] | \>[^\"])* \>\"
DELIMITED_STRING_BRACE =         q\"\{ ([^}]  |  }[^\"])* }\"

TOKEN_STRING_START = q\{
TOKEN_CLOSE_CURLY = \}
TOKEN_OPEN_CURLY = \{
TOKEN_STRING_CONTENT = [^]

STRING_POSTFIX = [cwd]
ESCAPE_SEQUENCE = {ESCAPE_SEQUENCE_SPEC_CHAR} | {ESCAPE_SEQUENCE_HEX_OCTAL}
                | {ESCAPE_SEQUENCE_UNICODE} | ("\\" {NAMED_CHARACTER_ENTITY})
ESCAPE_SEQUENCE_SPEC_CHAR = "\\\'" | "\\\"" | "\\\?" | "\\\\" | "\\0" | "\\a"
                          | "\\b"  | "\\f"  | "\\n"  | "\\r"  | "\\t" | "\\v"
ESCAPE_SEQUENCE_HEX_OCTAL = ("\\x" {HEX_DIGIT} {HEX_DIGIT}) | ("\\" {OCTAL_DIGIT})
              | ("\\" {OCTAL_DIGIT} {OCTAL_DIGIT}) | ("\\" {OCTAL_DIGIT} {OCTAL_DIGIT} {OCTAL_DIGIT})
ESCAPE_SEQUENCE_UNICODE = ("\\u" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT})
              | ("\\U" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT})

CHARACTER_LITERAL = \' ( [^\r\n\t\f\\] | {ESCAPE_SEQUENCE} ) \'

INTEGER_LITERAL = {INTEGER} {INTEGER_SUFFIX}?
INTEGER = {DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}
INTEGER_SUFFIX =  L | u | U | Lu | LU | uL | UL
DECIMAL_INTEGER = 0 | ({NON_ZERO_DIGIT} {DECIMAL_DIGITS_US}?)
BINARY_INTEGER = (0[bB]) {BINARY_DIGITS_NO_SINGLE_US}
HEXADECIMAL_INTEGER = {HEX_PREFIX} {HEX_DIGITS_NO_SINGLE_US}
NON_ZERO_DIGIT = [1-9]
DECIMAL_DIGITS = {DECIMAL_DIGIT}+
DECIMAL_DIGITS_US = {DECIMAL_DIGIT_US}+
DECIMAL_DIGITS_NO_SINGLE_US = ({DECIMAL_DIGIT} {DECIMAL_DIGITS_US}?) | ({DECIMAL_DIGITS_US} {DECIMAL_DIGIT})
DECIMAL_DIGITS_NO_STARTING_US = {DECIMAL_DIGIT} {DECIMAL_DIGITS_US}?
DECIMAL_DIGIT = 0 | {NON_ZERO_DIGIT}
DECIMAL_DIGIT_US = {DECIMAL_DIGIT} | _
BINARY_DIGITS_NO_SINGLE_US = {BINARY_DIGITS_US}? {BINARY_DIGIT} {BINARY_DIGITS_US}?
BINARY_DIGITS_US = {BINARY_DIGIT_US}+
BINARY_DIGIT = [01]
BINARY_DIGIT_US = {BINARY_DIGIT} | _
HEX_DIGITS_US = {HEX_DIGIT_US}+
HEX_DIGITS_NO_SINGLE_US = ({HEX_DIGIT} {HEX_DIGITS_US}?) | ({HEX_DIGITS_US} {HEX_DIGIT})
HEX_DIGITS_NO_STARTING_US = {HEX_DIGIT} {HEX_DIGITS_US}?


FLOAT_LITERAL = ({FLOAT} {SUFFIX}?) | ({INTEGER} {FLOAT_SUFFIX}) | ({INTEGER} {IMAGINARY_SUFFIX})
                | ({INTEGER} {FLOAT_SUFFIX} {IMAGINARY_SUFFIX}) | ({INTEGER} {REAL_SUFFIX} {IMAGINARY_SUFFIX})
FLOAT = {DECIMAL_FLOAT} | {HEX_FLOAT}
DECIMAL_FLOAT = ({LEADING_DECIMAL} \. ({DECIMAL_DIGITS_NO_STARTING_US} {DECIMAL_EXPONENT}?)?)
                | (\. {DECIMAL_DIGITS_NO_STARTING_US} {DECIMAL_EXPONENT}?)
                | ({LEADING_DECIMAL} {DECIMAL_EXPONENT})
DECIMAL_EXPONENT = [eE] [\+\-]? {DECIMAL_DIGITS_NO_SINGLE_US}

HEX_FLOAT = {HEX_PREFIX} {HEX_DIGITS_NO_SINGLE_US}? (\. {HEX_DIGITS_NO_STARTING_US})? {HEX_EXPONENT}
HEX_PREFIX = 0[xX]
HEX_EXPONENT = [pP] [\+\-]? {DECIMAL_DIGITS_NO_SINGLE_US}

SUFFIX = ({FLOAT_SUFFIX} {IMAGINARY_SUFFIX}?) | ({REAL_SUFFIX} {IMAGINARY_SUFFIX}?) | {IMAGINARY_SUFFIX}
FLOAT_SUFFIX = [fF]
REAL_SUFFIX = L
IMAGINARY_SUFFIX = i
LEADING_DECIMAL = {DECIMAL_INTEGER} | (0 {DECIMAL_DIGITS_NO_SINGLE_US})

LINE_DOC="///".*

BLOCK_DOC_START = "/**"
BLOCK_DOC_END = "*/"

NESTING_BLOCK_DOC_START = "/++"
NESTING_BLOCK_DOC_END = "+/"

%state WAITING_VALUE, NESTING_COMMENT_CONTENT, TOKEN_STRING_CONTENT, BLOCK_COMMENT_CONTENT
%state NESTING_DOC_CONTENT, BLOCK_DOC_CONTENT

%%

<YYINITIAL> {WHITE_SPACE_CHAR}+ { return com.intellij.psi.TokenType.WHITE_SPACE; }
<YYINITIAL> {NEW_LINE}+         { return com.intellij.psi.TokenType.WHITE_SPACE; }

<TOKEN_STRING_CONTENT> {
    {TOKEN_OPEN_CURLY} {
        tokenStringDepth++;
    }
    {TOKEN_CLOSE_CURLY} {
        tokenStringDepth--;
        if(tokenStringDepth == 0){
            yybegin(YYINITIAL);
            return DlangTypes.TOKEN_STRING;
        }
    }
    {TOKEN_STRING_START} {
        tokenStringDepth++;
    }
    {TOKEN_STRING_CONTENT} {
    }
}
<YYINITIAL> {TOKEN_STRING_START} {
    yybegin(TOKEN_STRING_CONTENT);
    tokenStringDepth = 1;
}

<YYINITIAL> {NESTING_BLOCK_COMMENT_START}{NESTING_BLOCK_COMMENT_END} {
    // Match this one before to prevent /++/ being consited as doc
    return NESTING_BLOCK_COMMENT;
}

<YYINITIAL> {NESTING_BLOCK_DOC_START} {
    yybegin(NESTING_DOC_CONTENT);
    nestedDocDepth = 1;
}

<YYINITIAL> {BLOCK_COMMENT_START}{BLOCK_COMMENT_END} {
    // Match this one before to prevent /**/ being consited as doc
    return DlangTypes.BLOCK_COMMENT;
}

<YYINITIAL> {BLOCK_DOC_START} {
          yybegin(BLOCK_DOC_CONTENT);
      }

<YYINITIAL> {NESTING_BLOCK_COMMENT_START} {
    yybegin(NESTING_COMMENT_CONTENT);
    nestedCommentDepth = 1;
}

<YYINITIAL> {BLOCK_COMMENT_START} {
          yybegin(BLOCK_COMMENT_CONTENT);
      }

<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }
<YYINITIAL> {INTEGER_LITERAL} { return INTEGER_LITERAL; }
<YYINITIAL> {INTEGER_LITERAL}/[^[\.[a-z][A-Z]]] { return INTEGER_LITERAL; }
<YYINITIAL> {FLOAT_LITERAL}/[^[\.[a-z][A-Z]]] {return FLOAT_LITERAL;}
<YYINITIAL> {DOUBLE_QUOTED_STRING} { return DOUBLE_QUOTED_STRING; }
<YYINITIAL> {WYSIWYG_STRING} { return WYSIWYG_STRING; }
<YYINITIAL> {ALTERNATE_WYSIWYG_STRING} { return ALTERNATE_WYSIWYG_STRING; }
<YYINITIAL> {DOUBLE_QUOTED_STRING} { return DOUBLE_QUOTED_STRING; }
<YYINITIAL> {DELIMITED_STRING} { return DELIMITED_STRING; }

<NESTING_COMMENT_CONTENT> {
    {NESTING_BLOCK_COMMENT_START} {
        nestedCommentDepth += 1;
    }

    \/? {NESTING_BLOCK_COMMENT_END}	{
        nestedCommentDepth -= 1;
        assert(nestedCommentDepth >= 0);
        if(nestedCommentDepth == 0) {
            yybegin(YYINITIAL); //Exit nesting comment block
            return DlangTypes.NESTING_BLOCK_COMMENT;
        }
    }
    \/\/        {}
    \n|\/|\+    {}
    [^/+\n]+    {}
}

<BLOCK_COMMENT_CONTENT> {
    {BLOCK_COMMENT_START} {}

    \/? {BLOCK_COMMENT_END} {
        yybegin(YYINITIAL);
        return DlangTypes.BLOCK_COMMENT;
    }
    \/\/        {}
    \n|\/|\*	{}
    [^/*\n]+	{}
}

<NESTING_DOC_CONTENT> {
    {NESTING_BLOCK_DOC_START} {
        nestedDocDepth += 1;
    }

    \/? {NESTING_BLOCK_DOC_END} {
        nestedDocDepth -= 1;
        assert(nestedDocDepth >= 0);
        if(nestedDocDepth == 0) {
            yybegin(YYINITIAL); //Exit nesting doc block
            return DlangTypes.NESTING_BLOCK_DOC;
        }
    }
    \/\/        {}
    \n|\/|\+    {}
    [^/+\n]+    {}
}

<BLOCK_DOC_CONTENT> {
    {BLOCK_DOC_START} {
    }

    \/? {BLOCK_DOC_END}	{
       yybegin(YYINITIAL);
       return DlangTypes.BLOCK_DOC;
    }
    \/\/        {}
    \n|\/|\*    {}
    [^/*\n]+    {}
}

//todo add typedef

<YYINITIAL> "module"                   { return KW_MODULE; }
<YYINITIAL> "import"                   { return KW_IMPORT; }
<YYINITIAL> "static"                   { return KW_STATIC; }
<YYINITIAL> "bool"                     { return KW_BOOL; }
<YYINITIAL> "byte"                     { return KW_BYTE; }
<YYINITIAL> "ubyte"                    { return KW_UBYTE; }
<YYINITIAL> "short"                    { return KW_SHORT; }
<YYINITIAL> "ushort"                   { return KW_USHORT; }
<YYINITIAL> "int"                      { return KW_INT; }
<YYINITIAL> "uint"                     { return KW_UINT; }
<YYINITIAL> "long"                     { return KW_LONG; }
<YYINITIAL> "ulong"                    { return KW_ULONG; }
<YYINITIAL> "char"                     { return KW_CHAR; }
<YYINITIAL> "wchar"                    { return KW_WCHAR; }
<YYINITIAL> "dchar"                    { return KW_DCHAR; }
<YYINITIAL> "float"                    { return KW_FLOAT; }
<YYINITIAL> "double"                   { return KW_DOUBLE; }
<YYINITIAL> "real"                     { return KW_REAL; }
<YYINITIAL> "ifloat"                   { return KW_IFLOAT; }
<YYINITIAL> "idouble"                  { return KW_IDOUBLE; }
<YYINITIAL> "ireal"                    { return KW_IREAL; }
<YYINITIAL> "cfloat"                   { return KW_CFLOAT; }
<YYINITIAL> "cdouble"                  { return KW_CDOUBLE; }
<YYINITIAL> "creal"                    { return KW_CREAL; }
<YYINITIAL> "cent"                     { return KW_CENT; }
<YYINITIAL> "ucent"                    { return KW_UCENT; }
<YYINITIAL> "void"                     { return KW_VOID; }
<YYINITIAL> "typeof"                   { return KW_TYPEOF; }
<YYINITIAL> "const"                    { return KW_CONST; }
<YYINITIAL> "immutable"                { return KW_IMMUTABLE; }
<YYINITIAL> "shared"                   { return KW_SHARED; }
<YYINITIAL> "inout"                    { return KW_INOUT; }
<YYINITIAL> "delegate"                 { return KW_DELEGATE; }
<YYINITIAL> "function"                 { return KW_FUNCTION; }
<YYINITIAL> "nothrow"                  { return KW_NOTHROW; }
<YYINITIAL> "pure"                     { return KW_PURE; }
<YYINITIAL> "this"                     { return KW_THIS; }
<YYINITIAL> "__FILE__"                 { return KW___FILE__; }
<YYINITIAL> "__MODULE__"               { return KW___MODULE__; }
<YYINITIAL> "__LINE__"                 { return KW___LINE__; }
<YYINITIAL> "__FUNCTION__"             { return KW___FUNCTION__; }
<YYINITIAL> "__PRETTY_FUNCTION__"      { return KW___PRETTY_FUNCTION__; }
<YYINITIAL> "abstract"                 { return KW_ABSTRACT; }
<YYINITIAL> "alias"                    { return KW_ALIAS; }
<YYINITIAL> "auto"                     { return KW_AUTO; }
<YYINITIAL> "deprecated"               { return KW_DEPRECATED; }
<YYINITIAL> "enum"                     { return KW_ENUM; }
<YYINITIAL> "extern"                   { return KW_EXTERN; }
<YYINITIAL> "final"                    { return KW_FINAL; }
<YYINITIAL> "override"                 { return KW_OVERRIDE; }
<YYINITIAL> "__gshared"                { return KW___GSHARED; }
<YYINITIAL> "scope"                    { return KW_SCOPE; }
<YYINITIAL> "synchronized"             { return KW_SYNCHRONIZED; }
<YYINITIAL> "return"                   { return KW_RETURN; }
<YYINITIAL> "super"                    { return KW_SUPER; }
<YYINITIAL> "align"                    { return KW_ALIGN; }
<YYINITIAL> "pragma"                   { return KW_PRAGMA; }
<YYINITIAL> "package"                  { return KW_PACKAGE; }
<YYINITIAL> "private"                  { return KW_PRIVATE; }
<YYINITIAL> "protected"                { return KW_PROTECTED; }
<YYINITIAL> "public"                   { return KW_PUBLIC; }
<YYINITIAL> "export"                   { return KW_EXPORT; }
<YYINITIAL> "delete"                   { return KW_DELETE; }
<YYINITIAL> "null"                     { return KW_NULL; }
<YYINITIAL> "true"                     { return KW_TRUE; }
<YYINITIAL> "false"                    { return KW_FALSE; }
<YYINITIAL> "new"                      { return KW_NEW; }
<YYINITIAL> "typeid"                   { return KW_TYPEID; }
<YYINITIAL> "is"                       { return KW_IS; }
//<YYINITIAL> "!is"/[^a-z]               { return KW_NOT_IS; }
<YYINITIAL> "struct"                   { return KW_STRUCT; }
<YYINITIAL> "union"                    { return KW_UNION; }
<YYINITIAL> "class"                    { return KW_CLASS; }
<YYINITIAL> "interface"                { return KW_INTERFACE; }
<YYINITIAL> "__parameters"             { return KW___PARAMETERS; }
<YYINITIAL> "__DATE__"                 { return KW___DATE__; }
<YYINITIAL> "__EOF__"                  { return KW___EOF__; }
<YYINITIAL> "__FILE__"                 { return KW___FILE__; }
<YYINITIAL> "__FILE_FULL_PATH__"       { return KW___FILE_FULL_PATH__; }
<YYINITIAL> "__FUNCTION__"             { return KW___FUNCTION__; }
<YYINITIAL> "__gshared"                { return KW___GSHARED; }
<YYINITIAL> "__LINE__"                 { return KW___LINE__; }
<YYINITIAL> "__MODULE__"               { return KW___MODULE__; }
<YYINITIAL> "__parameters"             { return KW___PARAMETERS; }
<YYINITIAL> "__PRETTY_FUNCTION__"      { return KW___PRETTY_FUNCTION__; }
<YYINITIAL> "__TIME__"                 { return KW___TIME__; }
<YYINITIAL> "__TIMESTAMP__"            { return KW___TIMESTAMP__; }
<YYINITIAL> "__traits"                 { return KW___TRAITS; }
<YYINITIAL> "__vector"                 { return KW___VECTOR; }
<YYINITIAL> "__VENDOR__"               { return KW___VENDOR__; }
<YYINITIAL> "__VERSION__"              { return KW___VERSION__; }
<YYINITIAL> "in"                       { return KW_IN; }
//<YYINITIAL> "!in"/[^a-z]               { return KW_NOT_IN; }
<YYINITIAL> "asm"                      { return KW_ASM; }
<YYINITIAL> "assert"                   { return KW_ASSERT; }
<YYINITIAL> "case"                     { return KW_CASE; }
<YYINITIAL> "cast"                     { return KW_CAST; }
<YYINITIAL> "ref"                      { return KW_REF; }
<YYINITIAL> "break"                    { return KW_BREAK; }
<YYINITIAL> "continue"                 { return KW_CONTINUE; }
<YYINITIAL> "do"                       { return KW_DO; }
<YYINITIAL> "else"                     { return KW_ELSE; }
<YYINITIAL> "for"                      { return KW_FOR; }
<YYINITIAL> "foreach"                  { return KW_FOREACH; }
<YYINITIAL> "foreach_reverse"          { return KW_FOREACH_REVERSE; }
<YYINITIAL> "goto"                     { return KW_GOTO; }
<YYINITIAL> "if"                       { return KW_IF; }
<YYINITIAL> "catch"                    { return KW_CATCH; }
<YYINITIAL> "finally"                  { return KW_FINALLY; }
<YYINITIAL> "switch"                   { return KW_SWITCH; }
<YYINITIAL> "throw"                    { return KW_THROW; }
<YYINITIAL> "try"                      { return KW_TRY; }
<YYINITIAL> "default"                  { return KW_DEFAULT; }
<YYINITIAL> "while"                    { return KW_WHILE; }
<YYINITIAL> "with"                     { return KW_WITH; }
<YYINITIAL> "version"                  { return KW_VERSION; }
<YYINITIAL> "debug"                    { return KW_DEBUG; }
<YYINITIAL> "mixin"                    { return KW_MIXIN; }
<YYINITIAL> "invariant"                { return KW_INVARIANT; }
<YYINITIAL> "template"                 { return KW_TEMPLATE; }
<YYINITIAL> "lazy"                     { return KW_LAZY; }
<YYINITIAL> "out"                      { return KW_OUT; }
//<YYINITIAL> "nogc"                     { return KW_NOGC; }//not a reserved word
<YYINITIAL> "__traits"                 { return KW___TRAITS; }
<YYINITIAL> "unittest"                 { return KW_UNITTEST; }
<YYINITIAL> ";"                        { return OP_SCOLON; }
<YYINITIAL> ":"                        { return OP_COLON; }
<YYINITIAL> "="                        { return OP_EQ; }
<YYINITIAL> ","                        { return OP_COMMA; }
<YYINITIAL> "("                        { return OP_PAR_LEFT; }
<YYINITIAL> ")"                        { return OP_PAR_RIGHT; }
<YYINITIAL> "["                        { return OP_BRACKET_LEFT; }
<YYINITIAL> "]"                        { return OP_BRACKET_RIGHT; }
<YYINITIAL> "{"                        { return OP_BRACES_LEFT; }
<YYINITIAL> "}"                        { return OP_BRACES_RIGHT; }
<YYINITIAL> "*"                        { return OP_ASTERISK; }
<YYINITIAL> ".."                       { return OP_DDOT; }
<YYINITIAL> "..."                      { return OP_TRIPLEDOT; }
<YYINITIAL> "@"                        { return OP_AT; }
<YYINITIAL> "+="                       { return OP_PLUS_EQ; }
<YYINITIAL> "-="                       { return OP_MINUS_EQ; }
<YYINITIAL> "*="                       { return OP_MUL_EQ; }
<YYINITIAL> "/="                       { return OP_DIV_EQ; }
<YYINITIAL> "%="                       { return OP_MOD_EQ; }
<YYINITIAL> "&="                       { return OP_AND_EQ; }
<YYINITIAL> "|="                       { return OP_OR_EQ; }
<YYINITIAL> "^="                       { return OP_XOR_EQ; }
<YYINITIAL> "~="                       { return OP_TILDA_EQ; }
<YYINITIAL> "<<="                      { return OP_SH_LEFT_EQ; }
<YYINITIAL> ">>="                      { return OP_SH_RIGHT_EQ; }
<YYINITIAL> ">>>="                     { return OP_USH_RIGHT_EQ; }
<YYINITIAL> "^^="                      { return OP_POW_EQ; }
<YYINITIAL> "?"                        { return OP_QUEST; }
<YYINITIAL> "||"                       { return OP_BOOL_OR; }
<YYINITIAL> "&&"                       { return OP_BOOL_AND; }
<YYINITIAL> "|"                        { return OP_OR; }
<YYINITIAL> "^"                        { return OP_XOR; }
<YYINITIAL> "<<"                       { return OP_SH_LEFT; }
<YYINITIAL> ">>"                       { return OP_SH_RIGHT; }
<YYINITIAL> ">>>"                      { return OP_USH_RIGHT; }
<YYINITIAL> "+"                        { return OP_PLUS; }
<YYINITIAL> "-"                        { return OP_MINUS; }
<YYINITIAL> "~"                        { return OP_TILDA; }
<YYINITIAL> "/"                        { return OP_DIV; }
<YYINITIAL> "%"                        { return OP_MOD; }
<YYINITIAL> "&"                        { return OP_AND; }
<YYINITIAL> "++"                       { return OP_PLUS_PLUS; }
<YYINITIAL> "--"                       { return OP_MINUS_MINUS; }
<YYINITIAL> "!"                        { return OP_NOT; }
<YYINITIAL> "^^"                       { return OP_POW; }
<YYINITIAL> "$"                        { return OP_DOLLAR; }
<YYINITIAL> "=="                       { return OP_EQ_EQ; }
<YYINITIAL> "!="                       { return OP_NOT_EQ; }
<YYINITIAL> "<"                        { return OP_LESS; }
<YYINITIAL> "<="                       { return OP_LESS_EQ; }
<YYINITIAL> ">"                        { return OP_GT; }
<YYINITIAL> ">="                       { return OP_GT_EQ; }
<YYINITIAL> "!<>"                      { return OP_UNORD; }
<YYINITIAL> "!<>="                     { return OP_UNORD_EQ; }
<YYINITIAL> "<>"                       { return OP_LESS_GR; }
<YYINITIAL> "<>="                      { return OP_LESS_GR_EQ; }
<YYINITIAL> "!>"                       { return OP_NOT_GR; }
<YYINITIAL> "!>="                      { return OP_NOT_GR_EQ; }
<YYINITIAL> "!<"                       { return OP_NOT_LESS; }
<YYINITIAL> "!<="                      { return OP_NOT_LESS_EQ; }
<YYINITIAL> "=>"                       { return OP_LAMBDA_ARROW; }
<YYINITIAL> "."                        { return OP_DOT; }
<YYINITIAL> {ID}                       { return ID; }
<YYINITIAL> {LINE_DOC}                 { return LINE_DOC; }
<YYINITIAL> {LINE_COMMENT}             { return LINE_COMMENT; }
<YYINITIAL> {SHEBANG}                  { return SHEBANG; }

. { return com.intellij.psi.TokenType.BAD_CHARACTER; }

