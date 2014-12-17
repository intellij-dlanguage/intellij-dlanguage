// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUnaryExpression extends PsiElement {

  @Nullable
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguageArguments getArguments();

  @Nullable
  DLanguageAssertExpression getAssertExpression();

  @NotNull
  List<DLanguageAssignExpression> getAssignExpressionList();

  @Nullable
  DLanguageCastExpression getCastExpression();

  @Nullable
  DLanguageDeleteExpression getDeleteExpression();

  @Nullable
  DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();

  @Nullable
  DLanguageNewExpression getNewExpression();

  @Nullable
  DLanguagePrimaryExpression getPrimaryExpression();

  @Nullable
  DLanguageTemplateArguments getTemplateArguments();

  @Nullable
  DLanguageType getType();

  @Nullable
  DLanguageUnaryExpression getUnaryExpression();

  @Nullable
  PsiElement getBitAnd();

  @Nullable
  PsiElement getDecrement();

  @Nullable
  PsiElement getDot();

  @Nullable
  PsiElement getIncrement();

  @Nullable
  PsiElement getLBracket();

  @Nullable
  PsiElement getLParen();

  @Nullable
  PsiElement getMinus();

  @Nullable
  PsiElement getNot();

  @Nullable
  PsiElement getPlus();

  @Nullable
  PsiElement getRBracket();

  @Nullable
  PsiElement getRParen();

  @Nullable
  PsiElement getSlice();

  @Nullable
  PsiElement getStar();

  @Nullable
  PsiElement getTilde();

}
