package io.github.intellij.dlanguage.sdlang.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import static io.github.intellij.dlanguage.sdlang.psi.SDLangElementTypes.*;

%%

%{

  public _SDLangLexer() {
      this((java.io.Reader)null);
  }
%}

%public
%class _SDLangLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

LETTER = [:letter:]
DIGIT = [:digit:]
IDENTIFIER = ({LETTER}|_)({LETTER}|{DIGIT}|_|-|\.|\$)*

REGULAR_STRING = \"((\\\")|(\\\n)|[^\"\r\n])*\"?
RAW_STRING = \`[^\`]*\`?

CHARACTER = \'[^\'\n]*\'?

INTEGER = {DIGIT}+
LONG_INT = {INTEGER}(l|L)
FLOAT = {INTEGER}\.{INTEGER}(F|f)
DOUBLE = {INTEGER}\.{INTEGER}(d|D)?
DECIMAL = {INTEGER}\.{INTEGER}(bd|BD)
BOOLEAN_TRUE = true|on
BOOLEAN_FALSE = false|off
DATE = {DIGIT}{DIGIT}{DIGIT}{DIGIT}\/{DIGIT}{DIGIT}?\/{DIGIT}{DIGIT}
DATE_TIME = ({DATE}" "{DIGIT}{DIGIT}:{DIGIT}{DIGIT}) ((:{DIGIT}{DIGIT}) (\.{DIGIT}{DIGIT}{DIGIT})?)? (-({LETTER}|{DIGIT}|:|\+|-)+)?
TIME_SPAN = (-?{DIGIT}+d:)?{DIGIT}{DIGIT}:{DIGIT}{DIGIT}:{DIGIT}{DIGIT}(\.{DIGIT}{DIGIT}{DIGIT})?
BINARY = \[ \s? [a-zA-Z0-9+/=]+ \s? \]
NULL = null

LINE_COMMENT = ("//"|#|--).*
BLOCK_COMMENT="/"\*([^*]|\*+[^*/])*(\*+"/")?

%%

<YYINITIAL> {
  \s+                         { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "{"                         { return L_CURLY; }
  "}"                         { return R_CURLY; }
  ":"                         { return COLON; }
  ";"                         { return SEMICOLON; }
  "="                         { return EQUALS; }

  {LINE_COMMENT}              { return LINE_COMMENT; }
  {BLOCK_COMMENT}             { return BLOCK_COMMENT; }
  {BOOLEAN_TRUE}              { return TRUE; }
  {BOOLEAN_FALSE}             { return FALSE; }
  {NULL}                      { return NULL; }
  {REGULAR_STRING}            { return REGULAR_STRING; }
  {RAW_STRING}                { return RAW_STRING; }
  {CHARACTER}                 { return CHARACTER; }
  {IDENTIFIER}                { return IDENTIFIER; }
  {DECIMAL}                   { return DECIMAL; }
  {DOUBLE}                    { return DOUBLE; }
  {FLOAT}                     { return FLOAT; }
  {LONG_INT}                  { return LONG_INT; }
  {INTEGER}                   { return INTEGER; }
  {DATE_TIME}                 { return DATE_TIME; }
  {DATE}                      { return DATE; }
  {TIME_SPAN}                 { return TIME; }
  {BINARY}                    { return BINARY; }
}
[^]                           { return TokenType.BAD_CHARACTER; }
