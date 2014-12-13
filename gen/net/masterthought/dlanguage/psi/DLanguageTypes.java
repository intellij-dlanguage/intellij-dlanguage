// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.impl.*;

public interface DLanguageTypes {

  IElementType ARGLIST = new DLanguageElementType("ARGLIST");
  IElementType BIT_EXPRESSION = new DLanguageElementType("BIT_EXPRESSION");
  IElementType CASTING_EXPRESSION = new DLanguageElementType("CASTING_EXPRESSION");
  IElementType CHARACTER = new DLanguageElementType("CHARACTER");
  IElementType CLASS_DECLARATION = new DLanguageElementType("CLASS_DECLARATION");
  IElementType CLASS_NAME = new DLanguageElementType("CLASS_NAME");
  IElementType COMPILATION_UNIT = new DLanguageElementType("COMPILATION_UNIT");
  IElementType CONSTRUCTOR_DECLARATION = new DLanguageElementType("CONSTRUCTOR_DECLARATION");
  IElementType CREATING_EXPRESSION = new DLanguageElementType("CREATING_EXPRESSION");
  IElementType DECIMAL_DIGITS = new DLanguageElementType("DECIMAL_DIGITS");
  IElementType DOC_COMMENT = new DLanguageElementType("DOC_COMMENT");
  IElementType DO_STATEMENT = new DLanguageElementType("DO_STATEMENT");
  IElementType EXPONENT_PART = new DLanguageElementType("EXPONENT_PART");
  IElementType EXPRESSION = new DLanguageElementType("EXPRESSION");
  IElementType FIELD_DECLARATION = new DLanguageElementType("FIELD_DECLARATION");
  IElementType FLOAT_LITERAL = new DLanguageElementType("FLOAT_LITERAL");
  IElementType FLOAT_TYPE_SUFFIX = new DLanguageElementType("FLOAT_TYPE_SUFFIX");
  IElementType FOR_STATEMENT = new DLanguageElementType("FOR_STATEMENT");
  IElementType IDENTIFIER = new DLanguageElementType("IDENTIFIER");
  IElementType IF_STATEMENT = new DLanguageElementType("IF_STATEMENT");
  IElementType IMPORT_STATEMENT = new DLanguageElementType("IMPORT_STATEMENT");
  IElementType INTEGER_LITERAL = new DLanguageElementType("INTEGER_LITERAL");
  IElementType INTERFACE_DECLARATION = new DLanguageElementType("INTERFACE_DECLARATION");
  IElementType INTERFACE_NAME = new DLanguageElementType("INTERFACE_NAME");
  IElementType LITERAL_EXPRESSION = new DLanguageElementType("LITERAL_EXPRESSION");
  IElementType LOGICAL_EXPRESSION = new DLanguageElementType("LOGICAL_EXPRESSION");
  IElementType METHOD_DECLARATION = new DLanguageElementType("METHOD_DECLARATION");
  IElementType MODIFIER = new DLanguageElementType("MODIFIER");
  IElementType NUMERIC_EXPRESSION = new DLanguageElementType("NUMERIC_EXPRESSION");
  IElementType PACKAGE_NAME = new DLanguageElementType("PACKAGE_NAME");
  IElementType PACKAGE_STATEMENT = new DLanguageElementType("PACKAGE_STATEMENT");
  IElementType PARAMETER = new DLanguageElementType("PARAMETER");
  IElementType PARAMETER_LIST = new DLanguageElementType("PARAMETER_LIST");
  IElementType STATEMENT = new DLanguageElementType("STATEMENT");
  IElementType STATEMENT_BLOCK = new DLanguageElementType("STATEMENT_BLOCK");
  IElementType STATIC_INITIALIZER = new DLanguageElementType("STATIC_INITIALIZER");
  IElementType STRING = new DLanguageElementType("STRING");
  IElementType STRING_EXPRESSION = new DLanguageElementType("STRING_EXPRESSION");
  IElementType SWITCH_STATEMENT = new DLanguageElementType("SWITCH_STATEMENT");
  IElementType TESTING_EXPRESSION = new DLanguageElementType("TESTING_EXPRESSION");
  IElementType TRY_STATEMENT = new DLanguageElementType("TRY_STATEMENT");
  IElementType TYPE = new DLanguageElementType("TYPE");
  IElementType TYPE_DECLARATION = new DLanguageElementType("TYPE_DECLARATION");
  IElementType TYPE_SPECIFIER = new DLanguageElementType("TYPE_SPECIFIER");
  IElementType VARIABLE_DECLARATION = new DLanguageElementType("VARIABLE_DECLARATION");
  IElementType VARIABLE_DECLARATOR = new DLanguageElementType("VARIABLE_DECLARATOR");
  IElementType VARIABLE_INITIALIZER = new DLanguageElementType("VARIABLE_INITIALIZER");
  IElementType WHILE_STATEMENT = new DLanguageElementType("WHILE_STATEMENT");

  IElementType COMMENT = new DLanguageTokenType("COMMENT");
  IElementType CRLF = new DLanguageTokenType("CRLF");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ARGLIST) {
        return new DLanguageArglistImpl(node);
      }
      else if (type == BIT_EXPRESSION) {
        return new DLanguageBitExpressionImpl(node);
      }
      else if (type == CASTING_EXPRESSION) {
        return new DLanguageCastingExpressionImpl(node);
      }
      else if (type == CHARACTER) {
        return new DLanguageCharacterImpl(node);
      }
      else if (type == CLASS_DECLARATION) {
        return new DLanguageClassDeclarationImpl(node);
      }
      else if (type == CLASS_NAME) {
        return new DLanguageClassNameImpl(node);
      }
      else if (type == COMPILATION_UNIT) {
        return new DLanguageCompilationUnitImpl(node);
      }
      else if (type == CONSTRUCTOR_DECLARATION) {
        return new DLanguageConstructorDeclarationImpl(node);
      }
      else if (type == CREATING_EXPRESSION) {
        return new DLanguageCreatingExpressionImpl(node);
      }
      else if (type == DECIMAL_DIGITS) {
        return new DLanguageDecimalDigitsImpl(node);
      }
      else if (type == DOC_COMMENT) {
        return new DLanguageDocCommentImpl(node);
      }
      else if (type == DO_STATEMENT) {
        return new DLanguageDoStatementImpl(node);
      }
      else if (type == EXPONENT_PART) {
        return new DLanguageExponentPartImpl(node);
      }
      else if (type == EXPRESSION) {
        return new DLanguageExpressionImpl(node);
      }
      else if (type == FIELD_DECLARATION) {
        return new DLanguageFieldDeclarationImpl(node);
      }
      else if (type == FLOAT_LITERAL) {
        return new DLanguageFloatLiteralImpl(node);
      }
      else if (type == FLOAT_TYPE_SUFFIX) {
        return new DLanguageFloatTypeSuffixImpl(node);
      }
      else if (type == FOR_STATEMENT) {
        return new DLanguageForStatementImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new DLanguageIdentifierImpl(node);
      }
      else if (type == IF_STATEMENT) {
        return new DLanguageIfStatementImpl(node);
      }
      else if (type == IMPORT_STATEMENT) {
        return new DLanguageImportStatementImpl(node);
      }
      else if (type == INTEGER_LITERAL) {
        return new DLanguageIntegerLiteralImpl(node);
      }
      else if (type == INTERFACE_DECLARATION) {
        return new DLanguageInterfaceDeclarationImpl(node);
      }
      else if (type == INTERFACE_NAME) {
        return new DLanguageInterfaceNameImpl(node);
      }
      else if (type == LITERAL_EXPRESSION) {
        return new DLanguageLiteralExpressionImpl(node);
      }
      else if (type == LOGICAL_EXPRESSION) {
        return new DLanguageLogicalExpressionImpl(node);
      }
      else if (type == METHOD_DECLARATION) {
        return new DLanguageMethodDeclarationImpl(node);
      }
      else if (type == MODIFIER) {
        return new DLanguageModifierImpl(node);
      }
      else if (type == NUMERIC_EXPRESSION) {
        return new DLanguageNumericExpressionImpl(node);
      }
      else if (type == PACKAGE_NAME) {
        return new DLanguagePackageNameImpl(node);
      }
      else if (type == PACKAGE_STATEMENT) {
        return new DLanguagePackageStatementImpl(node);
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
      else if (type == STATIC_INITIALIZER) {
        return new DLanguageStaticInitializerImpl(node);
      }
      else if (type == STRING) {
        return new DLanguageStringImpl(node);
      }
      else if (type == STRING_EXPRESSION) {
        return new DLanguageStringExpressionImpl(node);
      }
      else if (type == SWITCH_STATEMENT) {
        return new DLanguageSwitchStatementImpl(node);
      }
      else if (type == TESTING_EXPRESSION) {
        return new DLanguageTestingExpressionImpl(node);
      }
      else if (type == TRY_STATEMENT) {
        return new DLanguageTryStatementImpl(node);
      }
      else if (type == TYPE) {
        return new DLanguageTypeImpl(node);
      }
      else if (type == TYPE_DECLARATION) {
        return new DLanguageTypeDeclarationImpl(node);
      }
      else if (type == TYPE_SPECIFIER) {
        return new DLanguageTypeSpecifierImpl(node);
      }
      else if (type == VARIABLE_DECLARATION) {
        return new DLanguageVariableDeclarationImpl(node);
      }
      else if (type == VARIABLE_DECLARATOR) {
        return new DLanguageVariableDeclaratorImpl(node);
      }
      else if (type == VARIABLE_INITIALIZER) {
        return new DLanguageVariableInitializerImpl(node);
      }
      else if (type == WHILE_STATEMENT) {
        return new DLanguageWhileStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
