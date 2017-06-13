// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageLambda, ...)
  //methods are not found in DPsiImplUtil

}
