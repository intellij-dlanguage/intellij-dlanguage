// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.impl.*;

public interface DLanguageTypes {

  IElementType CLASS_DECLARATION = new DLanguageElementType("CLASS_DECLARATION");
  IElementType CLASS_NAME = new DLanguageElementType("CLASS_NAME");
  IElementType DOC_COMMENT = new DLanguageElementType("DOC_COMMENT");
  IElementType FIELD_DECLARATION = new DLanguageElementType("FIELD_DECLARATION");
  IElementType IDENTIFIER = new DLanguageElementType("IDENTIFIER");
  IElementType INTERFACE_NAME = new DLanguageElementType("INTERFACE_NAME");
  IElementType METHOD_DECLARATION = new DLanguageElementType("METHOD_DECLARATION");
  IElementType MODIFIER = new DLanguageElementType("MODIFIER");
  IElementType PACKAGE_NAME = new DLanguageElementType("PACKAGE_NAME");
  IElementType PARAMETER = new DLanguageElementType("PARAMETER");
  IElementType PARAMETER_LIST = new DLanguageElementType("PARAMETER_LIST");
  IElementType STATEMENT = new DLanguageElementType("STATEMENT");
  IElementType STATEMENT_BLOCK = new DLanguageElementType("STATEMENT_BLOCK");
  IElementType TYPE = new DLanguageElementType("TYPE");
  IElementType TYPE_SPECIFIER = new DLanguageElementType("TYPE_SPECIFIER");

  IElementType COMMENT = new DLanguageTokenType("COMMENT");
  IElementType CRLF = new DLanguageTokenType("CRLF");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == CLASS_DECLARATION) {
        return new DLanguageClassDeclarationImpl(node);
      }
      else if (type == CLASS_NAME) {
        return new DLanguageClassNameImpl(node);
      }
      else if (type == DOC_COMMENT) {
        return new DLanguageDocCommentImpl(node);
      }
      else if (type == FIELD_DECLARATION) {
        return new DLanguageFieldDeclarationImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new DLanguageIdentifierImpl(node);
      }
      else if (type == INTERFACE_NAME) {
        return new DLanguageInterfaceNameImpl(node);
      }
      else if (type == METHOD_DECLARATION) {
        return new DLanguageMethodDeclarationImpl(node);
      }
      else if (type == MODIFIER) {
        return new DLanguageModifierImpl(node);
      }
      else if (type == PACKAGE_NAME) {
        return new DLanguagePackageNameImpl(node);
      }
      else if (type == PARAMETER) {
        return new DLanguageParameterImpl(node);
      }
      else if (type == PARAMETER_LIST) {
        return new DLanguageParameterListImpl(node);
      }
      else if (type == STATEMENT) {
        return new DLanguageStatementImpl(node);
      }
      else if (type == STATEMENT_BLOCK) {
        return new DLanguageStatementBlockImpl(node);
      }
      else if (type == TYPE) {
        return new DLanguageTypeImpl(node);
      }
      else if (type == TYPE_SPECIFIER) {
        return new DLanguageTypeSpecifierImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
