// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDoStatement extends PsiElement {

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageScopeStatement getScopeStatement();

  @NotNull
  PsiElement getKwDo();

  @Nullable
  PsiElement getKwWhile();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpScolon();

}
