// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.impl.*;

public interface DLanguageTypes {

  IElementType PROPERTY = new DLanguageElementType("PROPERTY");

  IElementType COMMENT = new DLanguageTokenType("COMMENT");
  IElementType CRLF = new DLanguageTokenType("CRLF");
  IElementType KEY = new DLanguageTokenType("KEY");
  IElementType SEPARATOR = new DLanguageTokenType("SEPARATOR");
  IElementType VALUE = new DLanguageTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == PROPERTY) {
        return new DLanguagePropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
