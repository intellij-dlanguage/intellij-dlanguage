// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePostfixExpression extends PsiElement {

  @NotNull
  List<DLanguageArgumentList> getArgumentListList();

  @NotNull
  List<DLanguageAssignExpression> getAssignExpressionList();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageNewExpression getNewExpression();

  @Nullable
  DLanguagePostfixExpression getPostfixExpression();

  @Nullable
  DLanguagePrimaryExpression getPrimaryExpression();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  DLanguageTypeCtors getTypeCtors();

  @Nullable
  PsiElement getOpBracketLeft();

  @Nullable
  PsiElement getOpBracketRight();

  @Nullable
  PsiElement getOpDdot();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpMinusMinus();

  @Nullable
  PsiElement getOpPlusPlus();

}
