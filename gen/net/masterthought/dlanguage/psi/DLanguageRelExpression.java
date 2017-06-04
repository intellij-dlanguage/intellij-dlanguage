// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageRelExpression extends PsiElement {

  @Nullable
  DLanguageAddExpression_ getAddExpression_();

  @Nullable
  DLanguageCastExpression getCastExpression();

  @Nullable
  DLanguageDeleteExpression getDeleteExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageMulExpression_ getMulExpression_();

  @Nullable
  DLanguagePostfixExpression getPostfixExpression();

  @Nullable
  DLanguagePowExpression_ getPowExpression_();

  @Nullable
  DLanguageShiftExpression_ getShiftExpression_();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  DLanguageType getType();

  @Nullable
  DLanguageTypeCtor getTypeCtor();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpGt();

  @Nullable
  PsiElement getOpGtEq();

  @Nullable
  PsiElement getOpLess();

  @Nullable
  PsiElement getOpLessEq();

  @Nullable
  PsiElement getOpLessGr();

  @Nullable
  PsiElement getOpLessGrEq();

  @Nullable
  PsiElement getOpNotGr();

  @Nullable
  PsiElement getOpNotGrEq();

  @Nullable
  PsiElement getOpNotLess();

  @Nullable
  PsiElement getOpNotLessEq();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpUnord();

  @Nullable
  PsiElement getOpUnordEq();

}
