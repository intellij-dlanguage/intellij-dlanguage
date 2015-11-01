// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

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

}
