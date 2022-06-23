package io.github.intellij.dlanguage;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.psi.DlangTypes;
import static io.github.intellij.dlanguage.psi.DlangTypes.*;

%%

%{

  private int nestedCommentDepth = 0;
  private int nestedDocDepth = 0;
  private char stringDelimiter;
  private boolean stringDelimiterClosed;

  public DHighlightingLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class DHighlightingLexer
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
LINE_DOC="///".*
//BLOCK_COMMENT="/"\*(.|\n)*\*"/"

BLOCK_COMMENT_START = "/*"
BLOCK_COMMENT_END = "*/"

//DOC_COMMENT_START = "/**"
//DOC_COMMENT_END = "*/"

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
DELIMITED_STRING = q{DELIMITED_STRING_CONTENT} {STRING_POSTFIX}?
DELIMITED_STRING_CONTENT = (\"\( ~(\)\")) | (\"\[ ~(\]\")) | (\"\{ ~(\}\")) | (\"\< ~(\>\")) |
                   (\"{ID} {NEW_LINE} ~({NEW_LINE} {ID}\"))
ALTERNATIVE_DELIMITED_STRING_START = q\"[^[:letter:][:number:]_[" "]]


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


MODULE_DEFINITION = ([a-z_][a-zA-Z_0-9']+(\.[a-zA-Z_0-9']*)*)|[a-z]|[A-Z][a-zA-Z_0-9']*(\.[A-Z][a-zA-Z_0-9']*)*\.[a-z][a-zA-Z_0-9']*
AT_ATTRIBUTE = "@" {ID}

STRING = ({WYSIWYG_STRING} |
          {ALTERNATE_WYSIWYG_STRING} |
          {DOUBLE_QUOTED_STRING} |
          {DELIMITED_STRING}
)

NUMBER = ({INTEGER_LITERAL} | {FLOAT_LITERAL})

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
           template |
           lazy |
           out |
           nogc |
           __traits |
           unittest)

PARENTHESES_LEFT = ("(")

PARENTHESES_RIGHT = (")")

BRACES_LEFT = ("{")

BRACES_RIGHT = ("}")

BRACKETS_LEFT = ("[")

BRACKETS_RIGHT = ( "]")

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



BLOCK_DOC_START = "/**"
BLOCK_DOC_END = "*/"

NESTING_BLOCK_DOC_START = "/++"
NESTING_BLOCK_DOC_END = "+/"

%state WAITING_VALUE, NESTING_COMMENT_CONTENT BLOCK_COMMENT_CONTENT MODULE_VALUE FUNCTION_VALUE
%state NESTING_DOC_CONTENT, BLOCK_DOC_CONTENT, ALTERNATE_DELIMITED_STRING


%%

<YYINITIAL> {WHITE_SPACE_CHAR}+ { return com.intellij.psi.TokenType.WHITE_SPACE; }
<YYINITIAL> {NEW_LINE}+         { return com.intellij.psi.TokenType.WHITE_SPACE; }

<YYINITIAL> {NESTING_BLOCK_COMMENT_START}{NESTING_BLOCK_COMMENT_END} {
    // Match this one before to prevent /++/ being consited as doc
    return NESTING_BLOCK_COMMENT;
}

<YYINITIAL> {NESTING_BLOCK_DOC_START} {
    yybegin(NESTING_DOC_CONTENT);
    nestedDocDepth = 1;
    return DlangTypes.NESTING_BLOCK_DOC;
}

<YYINITIAL> {BLOCK_COMMENT_START}{BLOCK_COMMENT_END} {
    // Match this one before to prevent /**/ being consited as doc
    return DlangTypes.BLOCK_COMMENT;
}

<YYINITIAL> {BLOCK_DOC_START} {
          yybegin(BLOCK_DOC_CONTENT);
          return DlangTypes.BLOCK_DOC;
      }


<YYINITIAL> {NESTING_BLOCK_COMMENT_START} {
		yybegin(NESTING_COMMENT_CONTENT);
		nestedCommentDepth = 1;
		return DlangTypes.NESTING_BLOCK_COMMENT;
	}

<YYINITIAL> {BLOCK_COMMENT_START} {
		yybegin(BLOCK_COMMENT_CONTENT);
		return DlangTypes.BLOCK_COMMENT;
	}


//<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }


<ALTERNATE_DELIMITED_STRING> {
    {STRING_POSTFIX} {
          if (stringDelimiterClosed) {
              yybegin(YYINITIAL);
              return STRING;
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
              return STRING;
          }
          yybegin(YYINITIAL);
          return com.intellij.psi.TokenType.BAD_CHARACTER;
      }

    [^] {
          if(stringDelimiterClosed) {
              yypushback(1);
              yybegin(YYINITIAL);
              return STRING;
          }
      }

}

<NESTING_COMMENT_CONTENT> {
	{NESTING_BLOCK_COMMENT_START}	{
		nestedCommentDepth += 1;
		return DlangTypes.NESTING_BLOCK_COMMENT;
	}

	\/? {NESTING_BLOCK_COMMENT_END}	{
		nestedCommentDepth -= 1;
		if(nestedCommentDepth == 0) {
			yybegin(YYINITIAL); //Exit nesting comment block
		}
		return DlangTypes.NESTING_BLOCK_COMMENT;
	}
	\/\/        {return DlangTypes.NESTING_BLOCK_COMMENT;}
	\n|\/|\+	{return DlangTypes.NESTING_BLOCK_COMMENT;}
	[^/+\n]+	{return DlangTypes.NESTING_BLOCK_COMMENT;}
}

<BLOCK_COMMENT_CONTENT> {
	{BLOCK_COMMENT_START}	{
		return DlangTypes.BLOCK_COMMENT;
	}

	\/? {BLOCK_COMMENT_END}	{
    	yybegin(YYINITIAL);
		return DlangTypes.BLOCK_COMMENT;
	}
	\/\/        {return DlangTypes.BLOCK_COMMENT;}
	\n|\/|\*	{return DlangTypes.BLOCK_COMMENT;}
	[^/*\n]+	{return DlangTypes.BLOCK_COMMENT;}
}


<NESTING_DOC_CONTENT> {
	{NESTING_BLOCK_DOC_START}	{
		nestedDocDepth += 1;
		return DlangTypes.NESTING_BLOCK_DOC;
	}

	\/? {NESTING_BLOCK_DOC_END}	{
		nestedDocDepth -= 1;
        assert(nestedDocDepth >= 0);
		if(nestedDocDepth == 0) {
			yybegin(YYINITIAL); //Exit nesting doc block
		}
		return DlangTypes.NESTING_BLOCK_DOC;
	}
	\/\/        {return DlangTypes.NESTING_BLOCK_DOC;}
	\n|\/|\+	{return DlangTypes.NESTING_BLOCK_DOC;}
	[^/+\n]+	{return DlangTypes.NESTING_BLOCK_DOC;}
}

<BLOCK_DOC_CONTENT> {
	{BLOCK_DOC_START}	{
		return DlangTypes.BLOCK_DOC;
	}

	\/? {BLOCK_DOC_END}	{
		yybegin(YYINITIAL);
		return DlangTypes.BLOCK_DOC;
	}
	\/\/        {return DlangTypes.BLOCK_DOC;}
	\n|\/|\*	{return DlangTypes.BLOCK_DOC;}
	[^/*\n]+	{return DlangTypes.BLOCK_DOC;}
}

// module import
<YYINITIAL> module {
		yybegin(MODULE_VALUE);
		return DlangTypes.KEYWORD;
	}

<MODULE_VALUE> {
   {WHITE_SPACE_CHAR}* { return com.intellij.psi.TokenType.WHITE_SPACE; }
   {MODULE_DEFINITION} { yybegin(YYINITIAL); return DlangTypes.MODULE_DEFINITION; }
   [\n\r]    { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE; }
}

// function







//<YYINITIAL> {ID}\(\)({WHITE_SPACE_CHAR}*|{NEW_LINE}*)*\{ {
//        yypushback(yylength()-2);
//		yybegin(FUNCTION_VALUE);
//	}
//
//<FUNCTION_VALUE> {
//      {ID} { yybegin(YYINITIAL); return DlangTypes.FUNCTION_DEFINITION; }
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
//             return DlangTypes.FUNCTION_DEFINITION;
//           }
//          }




//<FUNCTION_VALUE>{
//  ({WHITE_SPACE_CHAR}*|{NEW_LINE}*)* { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE; }
//  \(\) {}
// {ID} { yybegin(YYINITIAL); return DlangTypes.FUNCTION_DEFINITION; }
//}



<YYINITIAL> {CHARACTER_LITERAL} { return CHARACTER_LITERAL; }
<YYINITIAL> {STRING} { return STRING; }
<YYINITIAL> {NUMBER} { return NUMBER; }
<YYINITIAL> {KEYWORD} { return KEYWORD; }
<YYINITIAL> {OPERATOR} { return OPERATOR; }
<YYINITIAL> {PARENTHESES_LEFT} { return PARENTHESES_LEFT; }
<YYINITIAL> {PARENTHESES_RIGHT} { return PARENTHESES_RIGHT; }
<YYINITIAL> {BRACES_LEFT} { return BRACES_LEFT; }
<YYINITIAL> {BRACES_RIGHT} { return BRACES_RIGHT; }
<YYINITIAL> {BRACKETS_LEFT} { return BRACKETS_LEFT; }
<YYINITIAL> {BRACKETS_RIGHT} { return BRACKETS_RIGHT; }
<YYINITIAL> {COMMA} { return COMMA; }
<YYINITIAL> {SEMICOLON} { return SEMICOLON; }
<YYINITIAL> {DOT} { return DOT; }
<YYINITIAL> {AT_ATTRIBUTE} { return AT_ATTRIBUTE; }

<YYINITIAL> {ALTERNATIVE_DELIMITED_STRING_START} {
    stringDelimiter = yycharat(yylength()-1);
    stringDelimiterClosed = false;
    yybegin(ALTERNATE_DELIMITED_STRING);
  }


<YYINITIAL> {ID}                       { return ID; }

<YYINITIAL> {ID}{PARENTHESES_RIGHT}{PARENTHESES_RIGHT}{BRACES_RIGHT}{PARENTHESES_LEFT}{PARENTHESES_LEFT}{BRACES_LEFT} {  yypushback(yylength()); yybegin(FUNCTION_VALUE); }
<FUNCTION_VALUE> {
      {ID} { yybegin(YYINITIAL); return DlangTypes.FUNCTION_DEFINITION; }
      }

<YYINITIAL> {LINE_DOC}                 { return LINE_DOC; }
<YYINITIAL> {LINE_COMMENT}             { return LINE_COMMENT; }
<YYINITIAL> {SHEBANG}                  { return SHEBANG; }

. { return com.intellij.psi.TokenType.BAD_CHARACTER; }

