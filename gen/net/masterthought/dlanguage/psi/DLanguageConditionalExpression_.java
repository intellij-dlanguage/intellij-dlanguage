// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageConditionalExpression_ extends PsiElement {

  @NotNull
  List<DLanguageAddExpression_> getAddExpression_List();

  @NotNull
  List<DLanguageAndExxpression_> getAndExxpression_List();

  @NotNull
  List<DLanguageCastExpression> getCastExpressionList();

  @NotNull
  DLanguageCommaExpression getCommaExpression();

  @Nullable
  DLanguageConditionalExpression_ getConditionalExpression_();

  @NotNull
  List<DLanguageDeleteExpression> getDeleteExpressionList();

  @NotNull
  List<DLanguageEqualExpression> getEqualExpressionList();

  @NotNull
  List<DLanguageIdentifier> getIdentifierList();

  @NotNull
  List<DLanguageIdentityExpression> getIdentityExpressionList();

  @NotNull
  List<DLanguageInExpression> getInExpressionList();

  @NotNull
  List<DLanguageMulExpression_> getMulExpression_List();

  @Nullable
  DLanguageOrOrExpression getOrOrExpression();

  @NotNull
  List<DLanguagePostfixExpression> getPostfixExpressionList();

  @NotNull
  List<DLanguagePowExpression_> getPowExpression_List();

  @NotNull
  List<DLanguageRelExpression> getRelExpressionList();

  @NotNull
  List<DLanguageShiftExpression_> getShiftExpression_List();

  @NotNull
  List<DLanguageTemplateInstance> getTemplateInstanceList();

  @NotNull
  List<DLanguageType> getTypeList();

  @NotNull
  List<DLanguageTypeCtor> getTypeCtorList();

  @NotNull
  List<DLanguageXorExpression_> getXorExpression_List();

  @NotNull
  PsiElement getOpColon();

  @NotNull
  PsiElement getOpQuest();

}
