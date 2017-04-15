package net.masterthought.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

%%

%{

  private int nestedCommentDepth = 0;
  private int blockCommentDepth = 0;

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


HEX_DIGIT = [0-9abcdefABCDEF_]
OCTAL_DIGIT = [0-7]
NAMED_CHARACTER_ENTITY = "&" {ID} ";"

WYSIWYG_STRING = "r"\" [^\"]* \" {STRING_POSTFIX}?
ALTERNATE_WYSIWYG_STRING = ` [^`]* ` {STRING_POSTFIX}?
DOUBLE_QUOTED_STRING = \" ( [^\\\"] |{ESCAPE_SEQUENCE})* \" {STRING_POSTFIX}?
HEX_STRING = x\" ([0-9a-fA-F][0-9a-fA-F] | [\r\n\s])* \" {STRING_POSTFIX}?
DELIMITED_STRING = ({DELIMITED_STRING_SQ_BR} | {DELIMITED_STRING_PARENTH}
                  | {DELIMITED_STRING_ANGLE_PARENTH} | {DELIMITED_STRING_BRACE}) {STRING_POSTFIX}?

DELIMITED_STRING_SQ_BR =         q\"\[ ([^\]] | \][^\"])* \]\"
DELIMITED_STRING_PARENTH =       q\"\( ([^\)] | \)[^\"])* \)\"
DELIMITED_STRING_ANGLE_PARENTH = q\"\< ([^\>] | \>[^\"])* \>\"
DELIMITED_STRING_BRACE =         q\"\{ ([^}]  |  }[^\"])* }\"

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

INTEGER_LITERAL = ({DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}) {INTEGER_SUFFIX}?
INTEGER_SUFFIX =  L | u | U | Lu | LU | uL | UL
DECIMAL_INTEGER = 0 | ([1-9] [0-9_]*)
BINARY_INTEGER = 0[bB][01_]+
HEXADECIMAL_INTEGER = 0[xX] [0-9a-fA-F] [0-9a-fA-F_]*


/* Implement DECIMAL_DIGITS_NO_SINGLE_US, HEX_DIGITS_NO_SINGLE_US */
FLOAT_LITERAL = ( ({DECIMAL_FLOAT} | {HEX_FLOAT}) [fFL]? i? ) | ( ({DECIMAL_INTEGER} | {BINARY_INTEGER} | {HEXADECIMAL_INTEGER}) [fFL]? i)
DECIMAL_FLOAT = ( {DECIMAL_FLOAT_SIMPLE} | {DECIMAL_FLOAT_EXPONENT} | {DECIMAL_FLOAT_FIRST_DOT}
                | {DECIMAL_FLOAT_FIRST_DOT_EXPONENT} | {DECIMAL_FLOAT_NO_DOT_EXPONENT} )
DECIMAL_FLOAT_SIMPLE = [0-9] [0-9_]* \. ([0] | [1-9_] [0-9_]*)?
DECIMAL_FLOAT_EXPONENT = [0-9_]+ \. [0-9_]+ {DECIMAL_EXPONENT}
DECIMAL_FLOAT_FIRST_DOT = \. ([0] | [1-9_] [0-9_]*)
DECIMAL_FLOAT_FIRST_DOT_EXPONENT = \. ([0] | [1-9] [0-9]*) {DECIMAL_EXPONENT}
DECIMAL_FLOAT_NO_DOT_EXPONENT = [0-9] [0-9_]* {DECIMAL_EXPONENT}

DECIMAL_EXPONENT = [eE][\+\-]? [0-9_]+

HEX_FLOAT = 0[xX] ([0-9a-fA-F]* \.)? [0-9a-fA-F]+ {HEX_EXPONENT}
HEX_EXPONENT = [pP][\+\-]? [0-9]+

%state WAITING_VALUE, NESTING_COMMENT_CONTENT BLOCK_COMMENT_CONTENT

%%

<YYINITIAL> {WHITE_SPACE_CHAR}+ { return com.intellij.psi.TokenType.WHITE_SPACE; }
<YYINITIAL> {NEW_LINE}+         { return com.intellij.psi.TokenType.WHITE_SPACE; }

<YYINITIAL> {NESTING_BLOCK_COMMENT_START} {
		yybegin(NESTING_COMMENT_CONTENT);
		nestedCommentDepth = 1;
		return DLanguageTypes.NESTING_BLOCK_COMMENT;
	}

<YYINITIAL> {BLOCK_COMMENT_START} {
		yybegin(BLOCK_COMMENT_CONTENT);
		blockCommentDepth = 1;
		return DLanguageTypes.BLOCK_COMMENT;
	}

<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }
<YYINITIAL> {INTEGER_LITERAL} { return INTEGER_LITERAL; }
<YYINITIAL> {FLOAT_LITERAL} { return FLOAT_LITERAL; }
<YYINITIAL> {DOUBLE_QUOTED_STRING} { return DOUBLE_QUOTED_STRING; }
<YYINITIAL> {WYSIWYG_STRING} { return WYSIWYG_STRING; }
<YYINITIAL> {ALTERNATE_WYSIWYG_STRING} { return ALTERNATE_WYSIWYG_STRING; }
<YYINITIAL> {DOUBLE_QUOTED_STRING} { return DOUBLE_QUOTED_STRING; }
<YYINITIAL> {HEX_STRING} { return HEX_STRING; }
<YYINITIAL> {DELIMITED_STRING} { return DELIMITED_STRING; }

<NESTING_COMMENT_CONTENT> {
	{NESTING_BLOCK_COMMENT_START}	{
		nestedCommentDepth += 1;
		return DLanguageTypes.NESTING_BLOCK_COMMENT;
	}

	{NESTING_BLOCK_COMMENT_END}	{
		nestedCommentDepth -= 1;
		if(nestedCommentDepth == 0) {
			yybegin(YYINITIAL); //Exit nesting comment block
		}
		return DLanguageTypes.NESTING_BLOCK_COMMENT;
	}
	\n|\/|\+	{return DLanguageTypes.NESTING_BLOCK_COMMENT;}
	[^/+\n]+	{return DLanguageTypes.NESTING_BLOCK_COMMENT;}
}

<BLOCK_COMMENT_CONTENT> {
	{BLOCK_COMMENT_START}	{
		blockCommentDepth += 1;
		return DLanguageTypes.BLOCK_COMMENT;
	}

	{BLOCK_COMMENT_END}	{
		blockCommentDepth -= 1;
		if(blockCommentDepth == 0) {
			yybegin(YYINITIAL); //Exit nesting comment block
		}
		return DLanguageTypes.BLOCK_COMMENT;
	}
	\n|\/|\*	{return DLanguageTypes.BLOCK_COMMENT;}
	[^/*\n]+	{return DLanguageTypes.BLOCK_COMMENT;}
}


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
<YYINITIAL> "!is"                      { return KW_NOT_IS; }
<YYINITIAL> "struct"                   { return KW_STRUCT; }
<YYINITIAL> "union"                    { return KW_UNION; }
<YYINITIAL> "class"                    { return KW_CLASS; }
<YYINITIAL> "interface"                { return KW_INTERFACE; }
<YYINITIAL> "__parameters"             { return KW___PARAMETERS; }
<YYINITIAL> "in"                       { return KW_IN; }
<YYINITIAL> "!in"                      { return KW_NOT_IN; }
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
<YYINITIAL> "body"                     { return KW_BODY; }
<YYINITIAL> "template"                 { return KW_TEMPLATE; }
<YYINITIAL> "lazy"                     { return KW_LAZY; }
<YYINITIAL> "out"                      { return KW_OUT; }
<YYINITIAL> "nogc"                     { return KW_NOGC; }
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
<YYINITIAL> "!<>="                     { return OP_UNORD; }
<YYINITIAL> "!<>"                      { return OP_UNORD_EQ; }
<YYINITIAL> "<>"                       { return OP_LESS_GR; }
<YYINITIAL> "<>="                      { return OP_LESS_GR_EQ; }
<YYINITIAL> "!>"                       { return OP_NOT_GR; }
<YYINITIAL> "!>="                      { return OP_NOT_GR_EQ; }
<YYINITIAL> "!<"                       { return OP_NOT_LESS; }
<YYINITIAL> "!<="                      { return OP_NOT_LESS_EQ; }
<YYINITIAL> "=>"                       { return OP_LAMBDA_ARROW; }
<YYINITIAL> "."                        { return OP_DOT; }
<YYINITIAL> {ID}                       { return ID; }
<YYINITIAL> {LINE_COMMENT}             { return LINE_COMMENT; }
<YYINITIAL> {SHEBANG}                  { return SHEBANG; }

. { return com.intellij.psi.TokenType.BAD_CHARACTER; }

