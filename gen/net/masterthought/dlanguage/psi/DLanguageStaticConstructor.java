// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;

import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStaticConstructor extends PsiElement, PsiNamedElement {

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  PsiElement getKwStatic();

  @NotNull
  PsiElement getKwThis();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpScolon();

}
