// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUnaryExpression extends PsiElement {

  @Nullable
  DLanguageCastExpression getCastExpression();

  @Nullable
  DLanguageDeleteExpression getDeleteExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguagePowExpression getPowExpression();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  DLanguageType getType();

  @Nullable
  DLanguageUnaryExpression getUnaryExpression();

  @Nullable
  PsiElement getOpAnd();

  @Nullable
  PsiElement getOpAsterisk();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpMinus();

  @Nullable
  PsiElement getOpMinusMinus();

  @Nullable
  PsiElement getOpNot();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpPlus();

  @Nullable
  PsiElement getOpPlusPlus();

  @Nullable
  PsiElement getOpPow();

  @Nullable
  PsiElement getOpTilda();

}
