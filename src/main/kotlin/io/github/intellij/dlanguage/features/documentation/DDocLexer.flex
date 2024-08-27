package io.github.intellij.dlanguage.features.documentation;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static io.github.intellij.dlanguage.features.documentation.DDocElementTypes.*;
import static io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.*;

%%

%{

  private int macroParenCount;
  private boolean nestedStyle;
  private char embeddedCodeDelimiter;

  // TODO support nested comments support
  // TODO support for ddoc (no leading star)
  public _DDocLexer() {
      this((java.io.Reader)null);
      macroParenCount = 0;
    }

    public void goTo(int offset) {
        zzCurrentPos = zzMarkedPos = zzStartRead = offset;
        zzAtEOF = false;
    }

    private void backExcludingNewLine() {
        int i = 1;
        while (yycharat(yylength() - i) != '#') {
            i++;
        }
        yypushback(i - 1);
    }
%}

%public
%class _DDocLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

DIGIT=[0-9]
ALPHA=[:jletter:]

MULTILINE_DOC_COMMENT_BEGIN="/*""*"+
MULTILINE_DOC_COMMENT_BEGIN_NESTED = "/+""+"+
DOC_COMMENT_BEGIN="//""/"+
DOC_COMMENT_END="*"+"/"
DOC_COMMENT_END_NESTED="+"+"/"

IDENTIFIER=({ALPHA}|{DIGIT}|"_")+

WHITE_DOC_SPACE_CHAR=[\ \t\f\n\r]
WHITE_DOC_SPACE_NO_LR=[\ \t\f]

INLINE_CODE_DELIMITER="`"
HEADING="#"{1,6}
NON_HEADING="######""#"+

HORIZONTAL_RULE=("- -"" -"+)|(("_"" "?"_")(" "?"_")+)|(("*"" "?"*")(" "?"*")+)
SIMPLE_EMPHASIS="*"~([^\\]"*")
DOUBLE_EMPHASIS="**"~([^\\]"**")
EMBEDDED_CODE_DELIMITER="``""`"+|"~~""~"+|"--""-"+


%state COMMENT_DATA_START, COMMENT_DATA, DOC_MACRO_START, DOC_MACRO, HEADING

// exclusive state (cannot match with rules from other states)
%xstate EMBEDDED_CODE, EMBEDDED_CODE_START

// Assume that comments decorators are removed

%%

<YYINITIAL> {
  {MULTILINE_DOC_COMMENT_BEGIN} { yybegin(COMMENT_DATA_START); nestedStyle = false; return DDOC_COMMENT_START; }
  {MULTILINE_DOC_COMMENT_BEGIN_NESTED} { yybegin(COMMENT_DATA_START); nestedStyle = true; return DDOC_COMMENT_START; }
  {DOC_COMMENT_BEGIN} { yybegin(COMMENT_DATA_START); return DDOC_COMMENT_START; }
  {DOC_COMMENT_END} | {DOC_COMMENT_END_NESTED} { return DDOC_COMMENT_END; }
}
<COMMENT_DATA_START, EMBEDDED_CODE_START> {WHITE_DOC_SPACE_CHAR}+ { return DDOC_WHITESPACE; }
<COMMENT_DATA, EMBEDDED_CODE, HEADING> {
  {WHITE_DOC_SPACE_NO_LR}+ { return DDOC_COMMENT_DATA; }
  [\n\r]+{WHITE_DOC_SPACE_CHAR}* { return DDOC_WHITESPACE; }
}

<COMMENT_DATA_START, COMMENT_DATA, DOC_MACRO> "$(" {
  macroParenCount++;
  yybegin(DOC_MACRO_START);
  return DDOC_MACRO_OPEN;
}

<DOC_MACRO_START> {IDENTIFIER} { yybegin(DOC_MACRO); return DDOC_MACRO_IDENTIFIER; }
<DOC_MACRO> {
  "," { return DDOC_MACRO_COMA; }
  "=" { return DDOC_MACRO_EQUALS; }
  "\\" { return DDOC_MACRO_BACKSLASH; }
  "$"({DIGIT}|"+") { return DDOC_MACRO_ARGUMENT; }
  {INLINE_CODE_DELIMITER}[^"`"\n\r]*{INLINE_CODE_DELIMITER} { return DDOC_INLINE_CODE; }
}
<DOC_MACRO_START, DOC_MACRO> {
  {WHITE_DOC_SPACE_CHAR}+ { return DDOC_WHITESPACE; }
  ")" {
    macroParenCount--;
    if (macroParenCount == 0) {
      yybegin(COMMENT_DATA);
    }
    return DDOC_MACRO_END;
  }
}
<DOC_MACRO> [^] { return DDOC_MACRO_ARGUMENT; }

<EMBEDDED_CODE_START> {
  > { return DDOC_QUOTE_CHAR; }
  {EMBEDDED_CODE_DELIMITER} {
    if (yycharat(yylength() - 1) == embeddedCodeDelimiter) {
        //yybegin(COMMENT_DATA);
        return DDOC_EMBEDDED_CODE_DELIMITER;
    }
    yybegin(EMBEDDED_CODE);
    return DDOC_EMBEDDED_CODE_CONTENT;
  }
}
<EMBEDDED_CODE, EMBEDDED_CODE_START> {
  [^] { yybegin(EMBEDDED_CODE); return DDOC_EMBEDDED_CODE_CONTENT; }
}

<HEADING> {
  {INLINE_CODE_DELIMITER}[^"`"\n\r]*{INLINE_CODE_DELIMITER} { return DDOC_INLINE_CODE; }
  " "{HEADING}[\n\r] {
            yybegin(COMMENT_DATA);
            backExcludingNewLine();
            return DDOC_HEADING_CHARS;
        }
  {HEADING}[\n\r] {
          yybegin(COMMENT_DATA);
          backExcludingNewLine();
          int i = 1;
          while(yycharat(yylength() - i) == '#') {
              i++;
          }
          // to handle the `# #\n` case
          if (yycharat(yylength() - i) == ' ') {
            return DDOC_HEADING_CHARS;
          }
          return DDOC_COMMENT_DATA;
      }
  [^] { return DDOC_COMMENT_DATA; }
}

<COMMENT_DATA_START> {
  > { return DDOC_QUOTE_CHAR; }
  {HEADING}" " { yybegin(HEADING); return DDOC_HEADING_CHARS; }
  {HEADING}[\n\r] { backExcludingNewLine(); return DDOC_HEADING_CHARS; }
  {EMBEDDED_CODE_DELIMITER} { yybegin(EMBEDDED_CODE); embeddedCodeDelimiter = yycharat(yylength() - 1); return DDOC_EMBEDDED_CODE_DELIMITER; }
}
<COMMENT_DATA_START, COMMENT_DATA> {
  : { yybegin(COMMENT_DATA); return DDOC_COLON; }
  "!" { yybegin(COMMENT_DATA); return DDOC_EXCLAMATION_MARK; }
  "[" { yybegin(COMMENT_DATA); return DDOC_LEFT_BRACKET; }
  "]" { yybegin(COMMENT_DATA); return DDOC_RIGHT_BRACKET; }
  "(" { yybegin(COMMENT_DATA); return DDOC_LEFT_PARENTHESES; }
  ")" { yybegin(COMMENT_DATA); return DDOC_RIGHT_PARENTHESES; }
  {HORIZONTAL_RULE} { yybegin(COMMENT_DATA); return DDOC_HORIZONTAL_RULE; }
  {DOUBLE_EMPHASIS} {yybegin(COMMENT_DATA); return DDOC_DOUBLE_EMPHASIS; }
  {SIMPLE_EMPHASIS} {yybegin(COMMENT_DATA); return DDOC_SIMPLE_EMPHASIS; }  // FIXME: handle tricky cases (bold inside italic)
  {INLINE_CODE_DELIMITER}[^"`"\n\r]*{INLINE_CODE_DELIMITER} { yybegin(COMMENT_DATA); return DDOC_INLINE_CODE; }
  {NON_HEADING} { yybegin(COMMENT_DATA); return DDOC_COMMENT_DATA; }
}
{DOC_COMMENT_END} { return nestedStyle ? DDOC_COMMENT_DATA: DDOC_COMMENT_END; }
{DOC_COMMENT_END_NESTED} { return !nestedStyle ? DDOC_COMMENT_DATA: DDOC_COMMENT_END; }
. { yybegin(COMMENT_DATA); return DDOC_COMMENT_DATA; }
[^] { return DDOC_BAD_CHARACTER; }
