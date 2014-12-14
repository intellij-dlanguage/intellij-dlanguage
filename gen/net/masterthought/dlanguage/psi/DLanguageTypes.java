// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.impl.*;

public interface DLanguageTypes {

  IElementType MODIFIER = new DLanguageElementType("MODIFIER");

  IElementType COMMENT = new DLanguageTokenType("COMMENT");
  IElementType CRLF = new DLanguageTokenType("CRLF");
  IElementType OP_PLUS = new DLanguageTokenType("+");
  IElementType PRIVATE = new DLanguageTokenType("private");
  IElementType PUBLIC = new DLanguageTokenType("public");
  IElementType TRANSIENT = new DLanguageTokenType("transient");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == MODIFIER) {
        return new DLanguageModifierImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
