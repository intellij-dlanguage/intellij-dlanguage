// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateAliasParameter extends PsiElement {

  @NotNull
  List<DLanguageAddExpression_> getAddExpression_List();

  @NotNull
  List<DLanguageAndExxpression_> getAndExxpression_List();

  @NotNull
  List<DLanguageConditionalExpression_> getConditionalExpression_List();

  @NotNull
  List<DLanguageEqualExpression> getEqualExpressionList();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageIdentityExpression> getIdentityExpressionList();

  @NotNull
  List<DLanguageInExpression> getInExpressionList();

  @NotNull
  List<DLanguageMulExpression_> getMulExpression_List();

  @NotNull
  List<DLanguageOrOrExpression> getOrOrExpressionList();

  @NotNull
  List<DLanguageRelExpression> getRelExpressionList();

  @NotNull
  List<DLanguageShiftExpression_> getShiftExpression_List();

  @NotNull
  List<DLanguageType> getTypeList();

  @NotNull
  List<DLanguageUnaryExpression> getUnaryExpressionList();

  @NotNull
  List<DLanguageXorExpression_> getXorExpression_List();

  @NotNull
  PsiElement getKwAlias();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpEq();

}
