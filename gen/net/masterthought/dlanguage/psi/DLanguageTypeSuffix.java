// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTypeSuffix extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributeList();

  @Nullable
  DLanguageParameters getParameters();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwDelegate();

  @Nullable
  PsiElement getKwFunction();

  @Nullable
  PsiElement getOpAsterisk();

  @Nullable
  PsiElement getOpBracketLeft();

  @Nullable
  PsiElement getOpBracketRight();

  @Nullable
  PsiElement getOpDdot();

}
