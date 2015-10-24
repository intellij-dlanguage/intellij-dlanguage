// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUnaryExpression extends PsiElement {

  @Nullable
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguageAssertExpression getAssertExpression();

  @NotNull
  List<DLanguageAssignExpression> getAssignExpressionList();

  @Nullable
  DLanguageCastExpression getCastExpression();

  @Nullable
  DLanguageDeleteExpression getDeleteExpression();

  @Nullable
  DLanguageFunctionCallExpression getFunctionCallExpression();

  @NotNull
  List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstanceList();

  @Nullable
  DLanguageNewExpression getNewExpression();

  @Nullable
  DLanguagePrimaryExpression getPrimaryExpression();

  @Nullable
  DLanguageType getType();

  @NotNull
  List<DLanguageUnaryExpression> getUnaryExpressionList();

  @Nullable
  PsiElement getOpAnd();

  @Nullable
  PsiElement getOpAsterisk();

  @Nullable
  PsiElement getOpBracketLeft();

  @Nullable
  PsiElement getOpBracketRight();

  @Nullable
  PsiElement getOpDdot();

  @Nullable
  PsiElement getOpMinus();

  @Nullable
  PsiElement getOpNot();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpPlus();

  @Nullable
  PsiElement getOpTilda();

}
