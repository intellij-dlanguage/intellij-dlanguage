package net.masterthought.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

%%

%{
  public DLanguageLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class DLanguageLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+


%%
<YYINITIAL> {
  {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "="                        { return ASSIGN; }
  "@"                        { return AT; }
  "&"                        { return BITAND; }
  "&="                       { return BITANDEQUAL; }
  "|"                        { return BITOR; }
  "|="                       { return BITOREQUAL; }
  "~="                       { return CATEQUAL; }
  " ="                       { return COLON; }
  ","                        { return COMMA; }
  "--"                       { return DECREMENT; }
  "/"                        { return DIV; }
  "/="                       { return DIVEQUAL; }
  "$"                        { return DOLLAR; }
  "."                        { return DOT; }
  "=="                       { return EQUAL; }
  "=>"                       { return GOESTO; }
  ">"                        { return GREATER; }
  ">="                       { return GREATEREQUAL; }
  "#"                        { return HASH; }
  "++"                       { return INCREMENT; }
  "{"                        { return LBRACE; }
  "["                        { return LBRACKET; }
  "<"                        { return LESS; }
  "<="                       { return LESSEQUAL; }
  "<>="                      { return LESSEQUALGREATER; }
  "<>"                       { return LESSORGREATER; }
  "&&"                       { return LOGICAND; }
  "||"                       { return LOGICOR; }
  "("                        { return LPAREN; }
  "-"                        { return MINUS; }
  "-="                       { return MINUSEQUAL; }
  "%"                        { return MOD; }
  "%="                       { return MODEQUAL; }
  "*="                       { return MULEQUAL; }
  "!"                        { return NOT; }
  "!="                       { return NOTEQUAL; }
  "!>"                       { return NOTGREATER; }
  "!>="                      { return NOTGREATEREQUAL; }
  "!<"                       { return NOTLESS; }
  "!<="                      { return NOTLESSEQUAL; }
  "!<>"                      { return NOTLESSEQUALGREATER; }
  "+"                        { return PLUS; }
  "+="                       { return PLUSEQUAL; }
  "^^"                       { return POW; }
  "^^="                      { return POWEQUAL; }
  "}"                        { return RBRACE; }
  "]"                        { return RBRACKET; }
  ")"                        { return RPAREN; }
  ""                         { return SEMICOLON; }
  "<<"                       { return SHIFTLEFT; }
  "<<="                      { return SHIFTLEFTEQUAL; }
  ">>"                       { return SHIFTRIGHT; }
  ">>="                      { return SHIFTRIGHTEQUAL; }
  ".."                       { return SLICE; }
  "*"                        { return STAR; }
  "?"                        { return TERNARY; }
  "~"                        { return TILDE; }
  "!<>="                     { return UNORDERED; }
  ">>>"                      { return UNSIGNEDSHIFTRIGHT; }
  ">>>="                     { return UNSIGNEDSHIFTRIGHTEQUAL; }
  "..."                      { return VARARG; }
  "^"                        { return XOR; }
  "^="                       { return XOREQUAL; }
  "bool"                     { return BOOL; }
  "byte"                     { return BYTE; }
  "cdouble"                  { return CDOUBLE; }
  "cent"                     { return CENT; }
  "cfloat"                   { return CFLOAT; }
  "char"                     { return CHAR; }
  "creal"                    { return CREAL; }
  "dchar"                    { return DCHAR; }
  "double"                   { return DOUBLE; }
  "float"                    { return FLOAT; }
  "function"                 { return FUNCTION; }
  "idouble"                  { return IDOUBLE; }
  "ifloat"                   { return IFLOAT; }
  "int"                      { return INT; }
  "ireal"                    { return IREAL; }
  "long"                     { return LONG; }
  "real"                     { return REAL; }
  "short"                    { return SHORT; }
  "ubyte"                    { return UBYTE; }
  "ucent"                    { return UCENT; }
  "uint"                     { return UINT; }
  "ulong"                    { return ULONG; }
  "ushort"                   { return USHORT; }
  "void"                     { return VOID; }
  "wchar"                    { return WCHAR; }
  "align"                    { return ALIGN; }
  "deprecated"               { return TDEPRECATED; }
  "extern"                   { return EXTERN; }
  "pragma"                   { return PRAGMA; }
  "export"                   { return EXPORT; }
  "package"                  { return PACKAGE; }
  "private"                  { return PRIVATE; }
  "protected"                { return PROTECTED; }
  "public"                   { return PUBLIC; }
  "abstract"                 { return ABSTRACT; }
  "auto"                     { return AUTO; }
  "const"                    { return CONST; }
  "final"                    { return FINAL; }
  "__gshared"                { return GSHARED; }
  "immutable"                { return IMMUTABLE; }
  "inout"                    { return INOUT; }
  "scope"                    { return SCOPE; }
  "shared"                   { return SHARED; }
  "static"                   { return STATIC; }
  "synchronized"             { return SYNCHRONIZED; }
  "alias"                    { return ALIAS; }
  "asm"                      { return ASM; }
  "assert"                   { return ASSERT; }
  "body"                     { return BODY; }
  "break"                    { return BREAK; }
  "case"                     { return CASE; }
  "cast"                     { return CAST; }
  "catch"                    { return TCATCH; }
  "class"                    { return CLASS; }
  "continue"                 { return CONTINUE; }
  "debug"                    { return DEBUG; }
  "default"                  { return DEFAULT; }
  "delegate"                 { return DELEGATE; }
  "delete"                   { return DELETE; }
  "do"                       { return DO; }
  "else"                     { return ELSE; }
  "enum"                     { return ENUM; }
  "false"                    { return FALSE; }
  "finally"                  { return TFINALLY; }
  "foreach"                  { return FOREACH; }
  "foreach_reverse"          { return FOREACH_REVERSE; }
  "for"                      { return FOR; }
  "goto"                     { return GOTO; }
  "if"                       { return IF; }
  "import"                   { return IMPORT; }
  "in"                       { return IN; }
  "interface"                { return INTERFACE; }
  "invariant"                { return TINVARIANT; }
  "is"                       { return IS; }
  "lazy"                     { return LAZY; }
  "macro"                    { return MACRO; }
  "mixin"                    { return MIXIN; }
  "module"                   { return TMODULE; }
  "new"                      { return NEW; }
  "nothrow"                  { return NOTHROW; }
  "null"                     { return NULL; }
  "out"                      { return OUT; }
  "override"                 { return OVERRIDE; }
  "pure"                     { return PURE; }
  "ref"                      { return REF; }
  "return"                   { return RETURN; }
  "struct"                   { return STRUCT; }
  "super"                    { return SUPER; }
  "switch"                   { return SWITCH; }
  "template"                 { return TEMPLATE; }
  "this"                     { return THIS; }
  "throw"                    { return THROW; }
  "true"                     { return TRUE; }
  "try"                      { return TRY; }
  "typedef"                  { return TYPEDEF; }
  "typeid"                   { return TYPEID; }
  "typeof"                   { return TYPEOF; }
  "union"                    { return UNION; }
  "unittest"                 { return TUNITTEST; }
  "version"                  { return VERSION; }
  "volatile"                 { return VOLATILE; }
  "while"                    { return WHILE; }
  "with"                     { return WITH; }
  "__DATE__"                 { return SPECIALDATE; }
  "__EOF__"                  { return SPECIALEOF; }
  "__TIME__"                 { return SPECIALTIME; }
  "__TIMESTAMP__"            { return SPECIALIMESTAMP; }
  "__VENDOR__"               { return SPECIALVENDOR; }
  "__VERSION__"              { return SPECIALVERSION; }
  "__FILE__"                 { return SPECIALFILE; }
  "__LINE__"                 { return SPECIALLINE; }
  "__MODULE__"               { return SPECIALMODULE; }
  "__FUNCTION__"             { return SPECIALFUNCTION; }
  "__PRETTY_FUNCTION__"      { return SPECIALPRETTYFUNCTION; }
  "__traits"                 { return TRAITS; }
  "__parameters"             { return TPARAMETERS; }
  "__vector"                 { return TVECTOR; }
  "EOF"                      { return EOF; }
  "CharacterLiteral"         { return CHARACTERLITERAL; }
  "TemplateArgumentList"     { return TEMPLATEARGUMENTLIST; }


  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
