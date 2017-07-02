// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageRelExpression extends PsiElement {

  @Nullable
  DLanguageAddExpression_ getAddExpression_();

  @Nullable
  DLanguageMulExpression_ getMulExpression_();

  @Nullable
  DLanguageShiftExpression_ getShiftExpression_();

  @Nullable
  DLanguageUnaryExpression getUnaryExpression();

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
  PsiElement getOpUnord();

  @Nullable
  PsiElement getOpUnordEq();

}
