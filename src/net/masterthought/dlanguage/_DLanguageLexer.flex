package net.masterthought.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

%%

%{
  public _DLanguageLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _DLanguageLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode


//WHITE_SPACE = [\ \t\f]
EOL=\r|\n|\r\n
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+
//NEW_LINE = [\n\r]+
//EOL = \r|\n|\r\n

//WHITE_SPACE=({EOL}|{NEW_LINE})

LETTER = [:letter:]
DIGIT = [:digit:]

//CHARACTER=[^\r\n]

IDENTIFIER = (_|{LETTER}) (_|{DIGIT}|{LETTER})*

HEX_DIGIT=[0-9abcdefABCDEF_]
OCTAL_DIGIT=[0-7]
NAMEDCHARACTERENTITY="&" {IDENTIFIER} ";"

STRING_POSTFIX = [cwd]
ESCAPE_SEQUENCE = {ESCAPE_SEQUENCE_SPEC_CHAR} | {ESCAPE_SEQUENCE_HEX_OCTAL}
                | {ESCAPE_SEQUENCE_UNICODE} | ("\\" {NAMEDCHARACTERENTITY})
ESCAPE_SEQUENCE_SPEC_CHAR = "\\\'" | "\\\"" | "\\\?" | "\\\\" | "\\0" | "\\a"
                          | "\\b"  | "\\f"  | "\\n"  | "\\r"  | "\\t" | "\\v"
ESCAPE_SEQUENCE_HEX_OCTAL = ("\\x" {HEX_DIGIT} {HEX_DIGIT}) | ("\\" {OCTAL_DIGIT})
              | ("\\" {OCTAL_DIGIT} {OCTAL_DIGIT}) | ("\\" {OCTAL_DIGIT} {OCTAL_DIGIT} {OCTAL_DIGIT})
ESCAPE_SEQUENCE_UNICODE = ("\\u" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT})
              | ("\\U" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT})

WYSIWYG_STRING = "r"\" [^\"]* \" {STRING_POSTFIX}?
ALTERNATEWYSIWYGSTRING = ` [^`]* ` {STRING_POSTFIX}?
DOUBLEQUOTEDSTRING = \" ( [^\\\"] |{ESCAPE_SEQUENCE})* \" {STRING_POSTFIX}?
HEXSTRING = x\" ([0-9a-fA-F][0-9a-fA-F] | [\r\n\s])* \" {STRING_POSTFIX}?

CHARACTER_LITERAL = \' ( [^\r\n\t\f\\] | {ESCAPE_SEQUENCE} ) \'

INTEGER_LITERAL = ({DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}) {INTEGER_SUFFIX}?
INTEGER_SUFFIX =  L | u | U | Lu | LU | uL | UL
DECIMAL_INTEGER = 0 | ([1-9] [0-9_]*)
BINARY_INTEGER = 0[bB][01_]+
HEXADECIMAL_INTEGER = 0[xX] [0-9a-fA-F] [0-9a-fA-F_]*


/* Implement DECIMAL_DIGITS_NO_SINGLE_US, HEX_DIGITS_NO_SINGLE_US */
FLOAT_LITERAL = ( ({DECIMAL_FLOAT} | {HEX_FLOAT}) [fFL]? i? ) | ( ({DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}) [fFL]? i)
DECIMAL_FLOAT = ( {DECIMAL_FLOAT_SIMPLE} | {DECIMAL_FLOAT_EXPONENT} | {DECIMAL_FLOAT_FIRST_DOT}
                | {DECIMAL_FLOAT_FIRST_DOT_EXPONENT} | {DECIMAL_FLOAT_NO_DOT_EXPONENT} )
DECIMAL_FLOAT_SIMPLE = [0-9] [0-9_]* \. ([0] | [1-9_] [0-9_]*)
DECIMAL_FLOAT_EXPONENT = [0-9_]+ \. [0-9_]+ {DECIMAL_EXPONENT}
DECIMAL_FLOAT_FIRST_DOT = \. ([0] | [1-9_] [0-9_]*)
DECIMAL_FLOAT_FIRST_DOT_EXPONENT = \. ([0] | [1-9] [0-9]*) {DECIMAL_EXPONENT}
DECIMAL_FLOAT_NO_DOT_EXPONENT = [0-9] [0-9_]* {DECIMAL_EXPONENT}

DECIMAL_EXPONENT = [eE][\+\-]? [0-9_]+

HEX_FLOAT = 0[xX] ([0-9a-fA-F]* \.)? [0-9a-fA-F]+ {HEX_EXPONENT}
HEX_EXPONENT = [pP][\+\-]? [0-9]+

//LINE_COMMENT="//".*
//BLOCK_COMMENT="/"\*(.|\n)*\*"/"


BLOCK_COMMENT = "/*" {BLOCK_COMMENT_CONTENT} "*/"
BLOCK_COMMENT_CONTENT = ( [^*] | "*"+ [^/] )*
//LINE_COMMENT = "/""/" {CHARACTER}*

%%
<YYINITIAL> {
  {WHITE_SPACE}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }
//  {NEW_LINE}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }
  {EOL}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }
  {LINE_WS}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "="                           { return ASSIGN; }
  "@"                           { return AT; }
  "&"                           { return BITAND; }
  "&="                          { return BITANDEQUAL; }
  "|"                           { return BITOR; }
  "|="                          { return BITOREQUAL; }
  "~="                          { return CATEQUAL; }
  ":"                           { return COLON; }
  ","                           { return COMMA; }
  "--"                          { return DECREMENT; }
  "/"                           { return DIV; }
  "/="                          { return DIVEQUAL; }
  "$"                           { return DOLLAR; }
  "."                           { return DOT; }
  "=="                          { return EQUAL; }
  "=>"                          { return GOESTO; }
  ">"                           { return GREATER; }
  ">="                          { return GREATEREQUAL; }
  "#"                           { return HASH; }
  "++"                          { return INCREMENT; }
  "{"                           { return LBRACE; }
  "["                           { return LBRACKET; }
  "<"                           { return LESS; }
  "<="                          { return LESSEQUAL; }
  "<>="                         { return LESSEQUALGREATER; }
  "<>"                          { return LESSORGREATER; }
  "&&"                          { return LOGICAND; }
  "||"                          { return LOGICOR; }
  "("                           { return LPAREN; }
  "-"                           { return MINUS; }
  "-="                          { return MINUSEQUAL; }
  "%"                           { return MOD; }
  "%="                          { return MODEQUAL; }
  "*="                          { return MULEQUAL; }
  "!"                           { return NOT; }
  "!="                          { return NOTEQUAL; }
  "!>"                          { return NOTGREATER; }
  "!>="                         { return NOTGREATEREQUAL; }
  "!<"                          { return NOTLESS; }
  "!<="                         { return NOTLESSEQUAL; }
  "!<>"                         { return NOTLESSEQUALGREATER; }
  "+"                           { return PLUS; }
  "+="                          { return PLUSEQUAL; }
  "^^"                          { return POW; }
  "^^="                         { return POWEQUAL; }
  "}"                           { return RBRACE; }
  "]"                           { return RBRACKET; }
  ")"                           { return RPAREN; }
  ""                            { return SEMICOLON; }
  "<<"                          { return SHIFTLEFT; }
  "<<="                         { return SHIFTLEFTEQUAL; }
  ">>"                          { return SHIFTRIGHT; }
  ">>="                         { return SHIFTRIGHTEQUAL; }
  ".."                          { return SLICE; }
  "*"                           { return STAR; }
  "?"                           { return TERNARY; }
  "~"                           { return TILDE; }
  "!<>="                        { return UNORDERED; }
  ">>>"                         { return UNSIGNEDSHIFTRIGHT; }
  ">>>="                        { return UNSIGNEDSHIFTRIGHTEQUAL; }
  "..."                         { return VARARG; }
  "^"                           { return XOR; }
  "^="                          { return XOREQUAL; }
  "bool"                        { return BOOL; }
  "byte"                        { return BYTE; }
  "cdouble"                     { return CDOUBLE; }
  "cent"                        { return CENT; }
  "cfloat"                      { return CFLOAT; }
  "char"                        { return CHAR; }
  "creal"                       { return CREAL; }
  "dchar"                       { return DCHAR; }
  "double"                      { return DOUBLE; }
  "float"                       { return FLOAT; }
  "function"                    { return FUNCTION; }
  "idouble"                     { return IDOUBLE; }
  "ifloat"                      { return IFLOAT; }
  "int"                         { return INT; }
  "ireal"                       { return IREAL; }
  "long"                        { return LONG; }
  "real"                        { return REAL; }
  "short"                       { return SHORT; }
  "ubyte"                       { return UBYTE; }
  "ucent"                       { return UCENT; }
  "uint"                        { return UINT; }
  "ulong"                       { return ULONG; }
  "ushort"                      { return USHORT; }
  "void"                        { return VOID; }
  "wchar"                       { return WCHAR; }
  "align"                       { return ALIGN; }
  "deprecated"                  { return TDEPRECATED; }
  "extern"                      { return EXTERN; }
  "pragma"                      { return PRAGMA; }
  "export"                      { return EXPORT; }
  "package"                     { return PACKAGE; }
  "private"                     { return PRIVATE; }
  "protected"                   { return PROTECTED; }
  "public"                      { return PUBLIC; }
  "abstract"                    { return ABSTRACT; }
  "auto"                        { return AUTO; }
  "const"                       { return CONST; }
  "final"                       { return FINAL; }
  "__gshared"                   { return GSHARED; }
  "immutable"                   { return IMMUTABLE; }
  "inout"                       { return INOUT; }
  "scope"                       { return SCOPE; }
  "shared"                      { return SHARED; }
  "static"                      { return STATIC; }
  "synchronized"                { return SYNCHRONIZED; }
  "alias"                       { return ALIAS; }
  "asm"                         { return ASM; }
  "assert"                      { return ASSERT; }
  "body"                        { return BODY; }
  "break"                       { return BREAK; }
  "case"                        { return CASE; }
  "cast"                        { return CAST; }
  "catch"                       { return TCATCH; }
  "class"                       { return DCLASS; }
  "continue"                    { return CONTINUE; }
  "debug"                       { return DEBUG; }
  "default"                     { return DEFAULT; }
  "delegate"                    { return DELEGATE; }
  "delete"                      { return DELETE; }
  "do"                          { return DO; }
  "else"                        { return ELSE; }
  "enum"                        { return ENUM; }
  "false"                       { return FALSE; }
  "finally"                     { return TFINALLY; }
  "foreach"                     { return FOREACH; }
  "foreach_reverse"             { return FOREACH_REVERSE; }
  "for"                         { return FOR; }
  "goto"                        { return GOTO; }
  "if"                          { return IF; }
  "import"                      { return IMPORT; }
  "in"                          { return IN; }
  "interface"                   { return INTERFACE; }
  "invariant"                   { return TINVARIANT; }
  "is"                          { return IS; }
  "lazy"                        { return LAZY; }
  "macro"                       { return MACRO; }
  "mixin"                       { return MIXIN; }
  "module"                      { return TMODULE; }
  "new"                         { return NEW; }
  "nothrow"                     { return NOTHROW; }
  "null"                        { return NULL; }
  "out"                         { return OUT; }
  "override"                    { return OVERRIDE; }
  "pure"                        { return PURE; }
  "ref"                         { return REF; }
  "return"                      { return RETURN; }
  "struct"                      { return STRUCT; }
  "super"                       { return SUPER; }
  "switch"                      { return SWITCH; }
  "template"                    { return TEMPLATE; }
  "this"                        { return THIS; }
  "throw"                       { return THROW; }
  "true"                        { return TRUE; }
  "try"                         { return TRY; }
  "typedef"                     { return TYPEDEF; }
  "typeid"                      { return TYPEID; }
  "typeof"                      { return TYPEOF; }
  "union"                       { return UNION; }
  "unittest"                    { return TUNITTEST; }
  "version"                     { return VERSION; }
  "volatile"                    { return VOLATILE; }
  "while"                       { return WHILE; }
  "with"                        { return WITH; }
  "__DATE__"                    { return SPECIALDATE; }
  "__EOF__"                     { return SPECIALEOF; }
  "__TIME__"                    { return SPECIALTIME; }
  "__TIMESTAMP__"               { return SPECIALIMESTAMP; }
  "__VENDOR__"                  { return SPECIALVENDOR; }
  "__VERSION__"                 { return SPECIALVERSION; }
  "__FILE__"                    { return SPECIALFILE; }
  "__LINE__"                    { return SPECIALLINE; }
  "__MODULE__"                  { return SPECIALMODULE; }
  "__FUNCTION__"                { return SPECIALFUNCTION; }
  "__PRETTY_FUNCTION__"         { return SPECIALPRETTYFUNCTION; }
  "__traits"                    { return TRAITS; }
  "__parameters"                { return TPARAMETERS; }
  "__vector"                    { return TVECTOR; }

  {IDENTIFIER}                  { return IDENTIFIER; }



  {INTEGER_LITERAL}        { return INTEGER_LITERAL; }
  {FLOAT_LITERAL}        { return FLOAT_LITERAL; }
  {CHARACTER_LITERAL}        { return CHARACTER_LITERAL; }
  {NAMEDCHARACTERENTITY}        { return NAMEDCHARACTERENTITY; }
  {WYSIWYG_STRING}              { return WYSIWYG_STRING; }
  {ALTERNATEWYSIWYGSTRING}      { return ALTERNATEWYSIWYGSTRING; }
  {DOUBLEQUOTEDSTRING}          { return DOUBLEQUOTEDSTRING; }
  {HEXSTRING}                   { return HEXSTRING; }
//  {LINE_COMMENT}                { return LINE_COMMENT; }
  {BLOCK_COMMENT}               { return BLOCK_COMMENT; }
//          [^]                       { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
. { return TokenType.BAD_CHARACTER; }