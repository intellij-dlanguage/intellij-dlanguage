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
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  DLanguageBasicType getBasicType();

  @NotNull
  List<DLanguageCastExpression> getCastExpressionList();

  @Nullable
  DLanguageConditionalExpression_ getConditionalExpression_();

  @NotNull
  DLanguageDeclarator getDeclarator();

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

  @Nullable
  DLanguageSpecialKeyword getSpecialKeyword();

  @NotNull
  List<DLanguageTemplateInstance> getTemplateInstanceList();

  @Nullable
  DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault();

  @NotNull
  List<DLanguageType> getTypeList();

  @NotNull
  List<DLanguageTypeCtor> getTypeCtorList();

  @NotNull
  List<DLanguageXorExpression_> getXorExpression_List();

  @Nullable
  PsiElement getOpEq();

}
