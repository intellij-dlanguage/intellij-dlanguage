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
  private char stringDelimiter;
  private String stringDelimiter2;
  private boolean stringDelimiterClosed;

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
NEW_LINE = [\n\r]
WHITE_SPACE = ({WHITE_SPACE_CHAR}|{NEW_LINE})+


LETTER = [:letter:]
DIGIT = [:digit:]
ID = (_|{LETTER}) (_|{DIGIT}|{LETTER})*


LINE_COMMENT="//".*
//BLOCK_COMMENT="/"\*(.|\n)*\*"/"

BLOCK_COMMENT_START = "/*"
BLOCK_COMMENT_END = "*/"

/* JFlex doesn't support recursive rules. So NESTING_BLOCK_COMMENT doesn't support nesting now. */
NESTING_BLOCK_COMMENT_START = \/\+
NESTING_BLOCK_COMMENT_END = \+\/
/*
NESTING_BLOCK_COMMENT = "/+" {NESTING_BLOCK_COMMENT_CONTENT} "+/"
NESTING_BLOCK_COMMENT_CONTENT = ( [^+] | "+"+ [^/] )*
*/


SHEBANG = "#!" ([^\r\n])* (\r|\n|\r\n)?


HEX_DIGIT = [0-9abcdefABCDEF]
HEX_DIGIT_US = {HEX_DIGIT} | _
OCTAL_DIGIT = [0-7]
NAMED_CHARACTER_ENTITY = "&" {ID} ";"

WYSIWYG_STRING = "r"\" [^\"]* (\" {STRING_POSTFIX}?)?
ALTERNATE_WYSIWYG_STRING = ` [^`]* (` {STRING_POSTFIX}?)?
DOUBLE_QUOTED_STRING = \" ([^\\\"] | {ESCAPE_SEQUENCE})* (\" {STRING_POSTFIX}?)?
DELIMITED_STRING = q{DELIMITED_STRING_CONTENT} {STRING_POSTFIX}?
DELIMITED_STRING_CONTENT = (\"\( ~(\)\")) | (\"\[ ~(\]\")) | (\"\{ ~(\}\")) | (\"\< ~(\>\"))
ALTERNATIVE_DELIMITED_STRING_START = q\".
ALTERNATIVE2_DELIMITED_STRING_START = q\"[^\"\r\n]+[\r\n]

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

CHARACTER_LITERAL = \' ( [^\r\n\t\f\\] | {ESCAPE_SEQUENCE} )? \'?


INTEGER_LITERAL = {INTEGER} {INTEGER_SUFFIX}?
INTEGER = {DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}
INTEGER_SUFFIX =  L | u | U | Lu | LU | uL | UL
DECIMAL_INTEGER = 0 | ({NON_ZERO_DIGIT} {DECIMAL_DIGITS_US}?)
BINARY_INTEGER = (0[bB]) {BINARY_DIGITS_NO_SINGLE_US}
HEXADECIMAL_INTEGER = {HEX_PREFIX} {HEX_DIGITS_NO_SINGLE_US}
NON_ZERO_DIGIT = [1-9]
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
%state NESTING_DOC_CONTENT, BLOCK_DOC_CONTENT, ALTERNATE_DELIMITED_STRING, ALTERNATE2_DELIMITED_STRING

%%

<YYINITIAL> {
 {WHITE_SPACE}       { return com.intellij.psi.TokenType.WHITE_SPACE; }

 {TOKEN_STRING_START} {
    yybegin(TOKEN_STRING_CONTENT);
    tokenStringDepth = 1;
 }

 {NESTING_BLOCK_COMMENT_START}{NESTING_BLOCK_COMMENT_END} {
    // Match this one before to prevent /++/ being consited as doc
    return NESTING_BLOCK_COMMENT;
 }

 {NESTING_BLOCK_DOC_START} {
    yybegin(NESTING_DOC_CONTENT);
    nestedDocDepth = 1;
 }

 {BLOCK_COMMENT_START}{BLOCK_COMMENT_END} {
    // Match this one before to prevent /**/ being consited as doc
    return DlangTypes.BLOCK_COMMENT;
 }

 {BLOCK_DOC_START}         {yybegin(BLOCK_DOC_CONTENT);}

 {NESTING_BLOCK_COMMENT_START} {
    yybegin(NESTING_COMMENT_CONTENT);
    nestedCommentDepth = 1;
 }

 {BLOCK_COMMENT_START}      { yybegin(BLOCK_COMMENT_CONTENT);}

 {CHARACTER_LITERAL}        { return CHARACTER_LITERAL; }
 {INTEGER_LITERAL}          { return INTEGER_LITERAL; }
 {INTEGER_LITERAL}/[^[\.[a-z][A-Z]]] { return INTEGER_LITERAL; }
 {FLOAT_LITERAL}/[^[\.[a-z][A-Z]]]   {return FLOAT_LITERAL;}
 {WYSIWYG_STRING}           { return WYSIWYG_STRING; }
 {ALTERNATE_WYSIWYG_STRING} { return ALTERNATE_WYSIWYG_STRING; }
 {DELIMITED_STRING}         { return DELIMITED_STRING; }
 {DOUBLE_QUOTED_STRING}     { return DOUBLE_QUOTED_STRING; }

 {ALTERNATIVE2_DELIMITED_STRING_START} {
    stringDelimiter2 = yytext().subSequence(2, yylength() -1).toString();
    stringDelimiterClosed = false;
    yypushback(1); // to simpler match q"test\ntest";
    yybegin(ALTERNATE2_DELIMITED_STRING);
  }
 {ALTERNATIVE_DELIMITED_STRING_START} {
    stringDelimiter = yycharat(yylength()-1);
    stringDelimiterClosed = false;
    yybegin(ALTERNATE_DELIMITED_STRING);
  }
//todo add typedef

 "module"                   { return KW_MODULE; }
 "import"                   { return KW_IMPORT; }
 "static"                   { return KW_STATIC; }
 "bool"                     { return KW_BOOL; }
 "byte"                     { return KW_BYTE; }
 "ubyte"                    { return KW_UBYTE; }
 "short"                    { return KW_SHORT; }
 "ushort"                   { return KW_USHORT; }
 "int"                      { return KW_INT; }
 "uint"                     { return KW_UINT; }
 "long"                     { return KW_LONG; }
 "ulong"                    { return KW_ULONG; }
 "char"                     { return KW_CHAR; }
 "wchar"                    { return KW_WCHAR; }
 "dchar"                    { return KW_DCHAR; }
 "float"                    { return KW_FLOAT; }
 "double"                   { return KW_DOUBLE; }
 "real"                     { return KW_REAL; }
 "ifloat"                   { return KW_IFLOAT; }
 "idouble"                  { return KW_IDOUBLE; }
 "ireal"                    { return KW_IREAL; }
 "cfloat"                   { return KW_CFLOAT; }
 "cdouble"                  { return KW_CDOUBLE; }
 "creal"                    { return KW_CREAL; }
 "cent"                     { return KW_CENT; }
 "ucent"                    { return KW_UCENT; }
 "void"                     { return KW_VOID; }
 "typeof"                   { return KW_TYPEOF; }
 "const"                    { return KW_CONST; }
 "immutable"                { return KW_IMMUTABLE; }
 "shared"                   { return KW_SHARED; }
 "inout"                    { return KW_INOUT; }
 "delegate"                 { return KW_DELEGATE; }
 "function"                 { return KW_FUNCTION; }
 "nothrow"                  { return KW_NOTHROW; }
 "pure"                     { return KW_PURE; }
 "this"                     { return KW_THIS; }
 "__FILE__"                 { return KW___FILE__; }
 "__MODULE__"               { return KW___MODULE__; }
 "__LINE__"                 { return KW___LINE__; }
 "__FUNCTION__"             { return KW___FUNCTION__; }
 "__PRETTY_FUNCTION__"      { return KW___PRETTY_FUNCTION__; }
 "abstract"                 { return KW_ABSTRACT; }
 "alias"                    { return KW_ALIAS; }
 "auto"                     { return KW_AUTO; }
 "deprecated"               { return KW_DEPRECATED; }
 "enum"                     { return KW_ENUM; }
 "extern"                   { return KW_EXTERN; }
 "final"                    { return KW_FINAL; }
 "override"                 { return KW_OVERRIDE; }
 "__gshared"                { return KW___GSHARED; }
 "scope"                    { return KW_SCOPE; }
 "synchronized"             { return KW_SYNCHRONIZED; }
 "return"                   { return KW_RETURN; }
 "super"                    { return KW_SUPER; }
 "align"                    { return KW_ALIGN; }
 "pragma"                   { return KW_PRAGMA; }
 "package"                  { return KW_PACKAGE; }
 "private"                  { return KW_PRIVATE; }
 "protected"                { return KW_PROTECTED; }
 "public"                   { return KW_PUBLIC; }
 "export"                   { return KW_EXPORT; }
 "delete"                   { return KW_DELETE; }
 "null"                     { return KW_NULL; }
 "true"                     { return KW_TRUE; }
 "false"                    { return KW_FALSE; }
 "new"                      { return KW_NEW; }
 "typeid"                   { return KW_TYPEID; }
 "is"                       { return KW_IS; }
// "!is"/[^a-z]               { return KW_NOT_IS; }
 "struct"                   { return KW_STRUCT; }
 "union"                    { return KW_UNION; }
 "class"                    { return KW_CLASS; }
 "interface"                { return KW_INTERFACE; }
 "__parameters"             { return KW___PARAMETERS; }
 "__DATE__"                 { return KW___DATE__; }
 "__EOF__"                  { return KW___EOF__; }
 "__FILE__"                 { return KW___FILE__; }
 "__FILE_FULL_PATH__"       { return KW___FILE_FULL_PATH__; }
 "__FUNCTION__"             { return KW___FUNCTION__; }
 "__gshared"                { return KW___GSHARED; }
 "__LINE__"                 { return KW___LINE__; }
 "__MODULE__"               { return KW___MODULE__; }
 "__parameters"             { return KW___PARAMETERS; }
 "__PRETTY_FUNCTION__"      { return KW___PRETTY_FUNCTION__; }
 "__TIME__"                 { return KW___TIME__; }
 "__TIMESTAMP__"            { return KW___TIMESTAMP__; }
 "__traits"                 { return KW___TRAITS; }
 "__vector"                 { return KW___VECTOR; }
 "__VENDOR__"               { return KW___VENDOR__; }
 "__VERSION__"              { return KW___VERSION__; }
 "in"                       { return KW_IN; }
// "!in"/[^a-z]               { return KW_NOT_IN; }
 "asm"                      { return KW_ASM; }
 "assert"                   { return KW_ASSERT; }
 "case"                     { return KW_CASE; }
 "cast"                     { return KW_CAST; }
 "ref"                      { return KW_REF; }
 "break"                    { return KW_BREAK; }
 "continue"                 { return KW_CONTINUE; }
 "do"                       { return KW_DO; }
 "else"                     { return KW_ELSE; }
 "for"                      { return KW_FOR; }
 "foreach"                  { return KW_FOREACH; }
 "foreach_reverse"          { return KW_FOREACH_REVERSE; }
 "goto"                     { return KW_GOTO; }
 "if"                       { return KW_IF; }
 "catch"                    { return KW_CATCH; }
 "finally"                  { return KW_FINALLY; }
 "switch"                   { return KW_SWITCH; }
 "throw"                    { return KW_THROW; }
 "try"                      { return KW_TRY; }
 "default"                  { return KW_DEFAULT; }
 "while"                    { return KW_WHILE; }
 "with"                     { return KW_WITH; }
 "version"                  { return KW_VERSION; }
 "debug"                    { return KW_DEBUG; }
 "mixin"                    { return KW_MIXIN; }
 "invariant"                { return KW_INVARIANT; }
 "template"                 { return KW_TEMPLATE; }
 "lazy"                     { return KW_LAZY; }
 "out"                      { return KW_OUT; }
// "nogc"                     { return KW_NOGC; }//not a reserved word
 "__traits"                 { return KW___TRAITS; }
 "unittest"                 { return KW_UNITTEST; }
 ";"                        { return OP_SCOLON; }
 ":"                        { return OP_COLON; }
 "="                        { return OP_EQ; }
 ","                        { return OP_COMMA; }
 "("                        { return OP_PAR_LEFT; }
 ")"                        { return OP_PAR_RIGHT; }
 "["                        { return OP_BRACKET_LEFT; }
 "]"                        { return OP_BRACKET_RIGHT; }
 "{"                        { return OP_BRACES_LEFT; }
 "}"                        { return OP_BRACES_RIGHT; }
 "*"                        { return OP_ASTERISK; }
 ".."                       { return OP_DDOT; }
 "..."                      { return OP_TRIPLEDOT; }
 "@"                        { return OP_AT; }
 "+="                       { return OP_PLUS_EQ; }
 "-="                       { return OP_MINUS_EQ; }
 "*="                       { return OP_MUL_EQ; }
 "/="                       { return OP_DIV_EQ; }
 "%="                       { return OP_MOD_EQ; }
 "&="                       { return OP_AND_EQ; }
 "|="                       { return OP_OR_EQ; }
 "^="                       { return OP_XOR_EQ; }
 "~="                       { return OP_TILDA_EQ; }
 "<<="                      { return OP_SH_LEFT_EQ; }
 ">>="                      { return OP_SH_RIGHT_EQ; }
 ">>>="                     { return OP_USH_RIGHT_EQ; }
 "^^="                      { return OP_POW_EQ; }
 "?"                        { return OP_QUEST; }
 "||"                       { return OP_BOOL_OR; }
 "&&"                       { return OP_BOOL_AND; }
 "|"                        { return OP_OR; }
 "^"                        { return OP_XOR; }
 "<<"                       { return OP_SH_LEFT; }
 ">>"                       { return OP_SH_RIGHT; }
 ">>>"                      { return OP_USH_RIGHT; }
 "+"                        { return OP_PLUS; }
 "-"                        { return OP_MINUS; }
 "~"                        { return OP_TILDA; }
 "/"                        { return OP_DIV; }
 "%"                        { return OP_MOD; }
 "&"                        { return OP_AND; }
 "++"                       { return OP_PLUS_PLUS; }
 "--"                       { return OP_MINUS_MINUS; }
 "!"                        { return OP_NOT; }
 "^^"                       { return OP_POW; }
 "$"                        { return OP_DOLLAR; }
 "=="                       { return OP_EQ_EQ; }
 "!="                       { return OP_NOT_EQ; }
 "<"                        { return OP_LESS; }
 "<="                       { return OP_LESS_EQ; }
 ">"                        { return OP_GT; }
 ">="                       { return OP_GT_EQ; }
 "!>"                       { return OP_NOT_GR; }
 "!>="                      { return OP_NOT_GR_EQ; }
 "!<"                       { return OP_NOT_LESS; }
 "!<="                      { return OP_NOT_LESS_EQ; }
 "=>"                       { return OP_LAMBDA_ARROW; }
 "."                        { return OP_DOT; }
 {ID}                       { return ID; }
 {LINE_DOC}                 { return LINE_DOC; }
 {LINE_COMMENT}             { return LINE_COMMENT; }
 {SHEBANG}                  { return SHEBANG; }

. { return com.intellij.psi.TokenType.BAD_CHARACTER; }

}

// States


///////////////////////////////////////////
// Strings
///////////////////////////////////////////

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
    {TOKEN_STRING_CONTENT} {}
}

// Delimited String with mono-character delimiter
<ALTERNATE_DELIMITED_STRING> {
    {STRING_POSTFIX} {
          if (stringDelimiterClosed) {
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
      }

    \" {
          if (yycharat(yylength()-2) == stringDelimiter) {
              stringDelimiterClosed = true;
          }
      }

    <<EOF>>     {
          if (stringDelimiterClosed) {
              yypushback(1);
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
          yybegin(YYINITIAL);
          return com.intellij.psi.TokenType.BAD_CHARACTER;
      }

    [^] {
          if(stringDelimiterClosed) {
              yypushback(1);
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
      }
}

// Delimited String with delimiter in form of {ID}\n
<ALTERNATE2_DELIMITED_STRING> {
    {STRING_POSTFIX} {
          if (stringDelimiterClosed) {
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
      }

    \n{ID}\" {
          if (yytext().subSequence(1, yylength() -1).toString().equals(stringDelimiter2)) {
              stringDelimiterClosed = true;
          }
      }

    <<EOF>>     {
          if (stringDelimiterClosed) {
              yypushback(1);
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
          yybegin(YYINITIAL);
          return com.intellij.psi.TokenType.BAD_CHARACTER;
      }

    [^] {
          if(stringDelimiterClosed) {
              yypushback(1);
              yybegin(YYINITIAL);
              return DELIMITED_STRING;
          }
      }
}

///////////////////////////////////////////
// Comments
///////////////////////////////////////////

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
    <<EOF>>	{
        yybegin(YYINITIAL); //Exit nesting comment block
        return DlangTypes.NESTING_BLOCK_COMMENT;
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
    <<EOF>> {
        yybegin(YYINITIAL);
        return DlangTypes.BLOCK_COMMENT;
    }
    \/\/        {}
    \n|\/|\*	{}
    [^/*\n]+	{}
}

///////////////////////////////////////////
// Documentation
///////////////////////////////////////////

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
    <<EOF>> {
        yybegin(YYINITIAL); //Exit nesting doc block
        return DlangTypes.NESTING_BLOCK_DOC;
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
    <<EOF>>	{
       yybegin(YYINITIAL);
       return DlangTypes.BLOCK_DOC;
    }
    \/\/        {}
    \n|\/|\*    {}
    [^/*\n]+    {}
}
