// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageLambda extends PsiElement {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageParameterAttributes getParameterAttributes();

  @Nullable
  DLanguageParameterMemberAttributes getParameterMemberAttributes();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwDelegate();

  @Nullable
  PsiElement getKwFunction();

  @NotNull
  PsiElement getOpLambdaArrow();

}
