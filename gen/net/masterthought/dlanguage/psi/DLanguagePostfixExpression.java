// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePostfixExpression extends PsiElement {

  @Nullable
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIndexExpression getIndexExpression();

  @Nullable
  DLanguageNewExpression getNewExpression();

  @Nullable
  DLanguagePostfixExpression getPostfixExpression();

  @Nullable
  DLanguagePrimaryExpression getPrimaryExpression();

  @Nullable
  DLanguageSliceExpression getSliceExpression();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  DLanguageTypeCtors getTypeCtors();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpMinusMinus();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpPlusPlus();

}
