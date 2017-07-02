// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateValueParameter extends PsiElement {

  @NotNull
  List<DLanguageAddExpression_> getAddExpression_List();

  @NotNull
  List<DLanguageAndExxpression_> getAndExxpression_List();

  @Nullable
  DLanguageConditionalExpression_ getConditionalExpression_();

  @NotNull
  List<DLanguageEqualExpression> getEqualExpressionList();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageIdentityExpression> getIdentityExpressionList();

  @NotNull
  List<DLanguageInExpression> getInExpressionList();

  @NotNull
  List<DLanguageMulExpression_> getMulExpression_List();

  @Nullable
  DLanguageOrOrExpression getOrOrExpression();

  @NotNull
  List<DLanguageRelExpression> getRelExpressionList();

  @NotNull
  List<DLanguageShiftExpression_> getShiftExpression_List();

  @NotNull
  List<DLanguageUnaryExpression> getUnaryExpressionList();

  @NotNull
  List<DLanguageXorExpression_> getXorExpression_List();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpEq();

}
