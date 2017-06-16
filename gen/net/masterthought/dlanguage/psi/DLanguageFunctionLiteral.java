// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface DLanguageFunctionLiteral extends PsiElement {

  @Nullable
  DLanguageFunctionLiteralBody getFunctionLiteralBody();

  @Nullable
  DLanguageLambda getLambda();

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

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
