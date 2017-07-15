package net.masterthought.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

%%

%{

  private int nestedCommentDepth = 0;
  private int blockCommentDepth = 0;

  public DLanguageHighlightingLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class DLanguageHighlightingLexer
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
DECIMAL_FLOAT_SIMPLE = [0-9] [0-9_]* \. ([0] | [1-9_] [0-9_]*)
DECIMAL_FLOAT_EXPONENT = [0-9_]+ \. [0-9_]+ {DECIMAL_EXPONENT}
DECIMAL_FLOAT_FIRST_DOT = \. ([0] | [1-9_] [0-9_]*)
DECIMAL_FLOAT_FIRST_DOT_EXPONENT = \. ([0] | [1-9] [0-9]*) {DECIMAL_EXPONENT}
DECIMAL_FLOAT_NO_DOT_EXPONENT = [0-9] [0-9_]* {DECIMAL_EXPONENT}

DECIMAL_EXPONENT = [eE][\+\-]? [0-9_]+

HEX_FLOAT = 0[xX] ([0-9a-fA-F]* \.)? [0-9a-fA-F]+ {HEX_EXPONENT}
HEX_EXPONENT = [pP][\+\-]? [0-9]+

MODULE_DEFINITION = ([a-z_][a-zA-Z_0-9']+(\.[a-zA-Z_0-9']*)*)|[a-z]|[A-Z][a-zA-Z_0-9']*(\.[A-Z][a-zA-Z_0-9']*)*\.[a-z][a-zA-Z_0-9']*

STRING = ({WYSIWYG_STRING} |
          {ALTERNATE_WYSIWYG_STRING} |
          {DOUBLE_QUOTED_STRING} |
          {HEX_STRING} |
          {DELIMITED_STRING}
)

NUMBER = ({INTEGER_LITERAL} |
          {FLOAT_LITERAL} |
          {DECIMAL_FLOAT} |
          {DECIMAL_FLOAT_SIMPLE} |
          {DECIMAL_FLOAT_EXPONENT} |
          {DECIMAL_FLOAT_FIRST_DOT} |
          {DECIMAL_FLOAT_NO_DOT_EXPONENT} |
          {DECIMAL_EXPONENT} |
          {HEX_FLOAT} |
          {HEX_EXPONENT}
          )

BASIC_TYPES = ( bool |
                byte |
                ubyte |
                short |
                ushort |
                int |
                uint |
                long |
                ulong |
                char |
                wchar |
                dchar |
                float |
                double |
                real |
                ifloat |
                idouble |
                ireal |
                cfloat |
                cdouble |
                creal |
                auto |
                enum |
                string |
                void )

KEYWORD = ({BASIC_TYPES} |
           module |
           import |
           static |
           typeof |
           const |
           immutable |
           shared |
           inout |
           delegate |
           function |
           nothrow |
           pure |
           this |
           __FILE__ |
           __MODULE__ |
           __LINE__ |
           __FUNCTION__ |
           __PRETTY_FUNCTION__ |
           abstract |
           alias |
           deprecated |
           extern |
           final |
           override |
           __gshared |
           scope |
           synchronized |
           return |
           super |
           align |
           pragma |
           package |
           private |
           protected |
           public |
           export |
           delete |
           null |
           true |
           false |
           new |
           typeid |
           is |
           struct |
           union |
           class |
           interface |
           __parameters |
           in |
           asm |
           assert |
           case |
           cast |
           ref |
           break |
           continue |
           do |
           else |
           for |
           foreach |
           foreach_reverse |
           goto |
           if |
           catch |
           finally |
           switch |
           throw |
           try |
           default |
           while |
           with |
           version |
           debug |
           mixin |
           invariant |
           body |
           template |
           lazy |
           out |
           nogc |
           __traits |
           unittest)

PARENTHESES = ("(" | ")")

BRACES = ("{" | "}")

BRACKETS = ("[" | "]")

COMMA = ","

SEMICOLON = ";"

DOT = "."

OPERATOR = (":"    |
            "="    |
            "*"    |
            ".."   |
            "..."  |
            "@"    |
            "+="   |
            "-="   |
            "*="   |
            "/="   |
            "%="   |
            "&="   |
            "|="   |
            "^="   |
            "~="   |
            "<<="  |
            ">>="  |
            ">>>=" |
            "^^="  |
            "?"    |
            "||"   |
            "&&"   |
            "|"    |
            "^"    |
            "<<"   |
            ">>"   |
            ">>>"  |
            "+"    |
            "-"    |
            "~"    |
            "/"    |
            "%"    |
            "&"    |
            "++"   |
            "--"   |
            "!"    |
            "^^"   |
            "$"    |
            "=="   |
            "!="   |
            "<"    |
            "<="   |
            ">"    |
            ">="   |
            "!<>=" |
            "!<>"  |
            "<>"   |
            "<>="  |
            "!>"   |
            "!>="  |
            "!<"   |
            "!<="  |
            "=>")

FUNCTION_DEFINITION = {ID}\(.*\)([^;]|[\s]*|[\r]*|[\n]*)

%state WAITING_VALUE, NESTING_COMMENT_CONTENT BLOCK_COMMENT_CONTENT MODULE_VALUE FUNCTION_VALUE


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

//<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }


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

// module import
<YYINITIAL> module {
		yybegin(MODULE_VALUE);
		return DLanguageTypes.KEYWORD;
	}

<MODULE_VALUE> {
   {WHITE_SPACE_CHAR}* { return com.intellij.psi.TokenType.WHITE_SPACE; }
   {MODULE_DEFINITION} { yybegin(YYINITIAL); return DLanguageTypes.MODULE_DEFINITION; }
   [\n\r]    { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE; }
}

// function







//<YYINITIAL> {ID}\(\)({WHITE_SPACE_CHAR}*|{NEW_LINE}*)*\{ {
//        yypushback(yylength()-2);
//		yybegin(FUNCTION_VALUE);
//	}
//
//<FUNCTION_VALUE> {
//      {ID} { yybegin(YYINITIAL); return DLanguageTypes.FUNCTION_DEFINITION; }
//      [\n\r]    { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE; }
//}

//<YYINITIAL> {ID} \(.*\)({WHITE_SPACE_CHAR}|{NEW_LINE})*\{ { return FUNCTION_DEFINITION; }
//<YYINITIAL> {ID} \(.*\)({WHITE_SPACE_CHAR}|{NEW_LINE})*\{ { return FUNCTION_DEFINITION; }


//<FUNCTION_VALUE2>  { yybegin(FUNCTION_VALUE2); }
//<FUNCTION_VALUE> {ID} { yybegin(FUNCTION_VALUE2); }

//<YYINITIAL> {ID} \(.*\)({WHITE_SPACE_CHAR}|{NEW_LINE})*\{  {
//          yybegin(YYINITIAL);
//            String theMatch = yytext().toString();
//           if(theMatch.contains("){")){
//             return DLanguageTypes.FUNCTION_DEFINITION;
//           }
//          }




//<FUNCTION_VALUE>{
//  ({WHITE_SPACE_CHAR}*|{NEW_LINE}*)* { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE; }
//  \(\) {}
// {ID} { yybegin(YYINITIAL); return DLanguageTypes.FUNCTION_DEFINITION; }
//}



<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }
<YYINITIAL> {STRING} { return STRING; }
<YYINITIAL> {NUMBER} { return NUMBER; }
<YYINITIAL> {KEYWORD} { return KEYWORD; }
<YYINITIAL> {OPERATOR} { return OPERATOR; }
<YYINITIAL> {PARENTHESES} { return PARENTHESES; }
<YYINITIAL> {BRACES} { return BRACES; }
<YYINITIAL> {BRACKETS} { return BRACKETS; }
<YYINITIAL> {COMMA} { return COMMA; }
<YYINITIAL> {SEMICOLON} { return SEMICOLON; }
<YYINITIAL> {DOT} { return DOT; }


<YYINITIAL> {ID}                       { return ID; }

<YYINITIAL> {ID}{PARENTHESES}{PARENTHESES}{BRACES} {  yypushback(yylength()); yybegin(FUNCTION_VALUE); }
<FUNCTION_VALUE> {
      {ID} { yybegin(YYINITIAL); return DLanguageTypes.FUNCTION_DEFINITION; }
      }

<YYINITIAL> {LINE_COMMENT}             { return LINE_COMMENT; }
<YYINITIAL> {SHEBANG}                  { return SHEBANG; }

. { return com.intellij.psi.TokenType.BAD_CHARACTER; }

