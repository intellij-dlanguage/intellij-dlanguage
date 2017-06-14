// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface DLanguageForeachRangeStatement extends PsiElement {

  @NotNull
  DLanguageForeach getForeach();

  @NotNull
  DLanguageForeachType getForeachType();

  @NotNull
  DLanguageLwrExpression getLwrExpression();

  @NotNull
  DLanguageScopeStatement getScopeStatement();

  @NotNull
  DLanguageUprExpression getUprExpression();

  @NotNull
  PsiElement getOpDdot();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @NotNull
  PsiElement getOpScolon();

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
