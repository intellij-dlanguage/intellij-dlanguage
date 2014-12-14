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
  {WHITE_SPACE}      { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "+"                { return OP_PLUS; }
  "COMMENT"          { return COMMENT; }
  "CRLF"             { return CRLF; }
  "public"           { return PUBLIC; }
  "private"          { return PRIVATE; }
  "transient"        { return TRANSIENT; }


  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
