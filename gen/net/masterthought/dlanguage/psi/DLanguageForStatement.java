// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageForStatement extends PsiElement {

  @Nullable
  DLanguageIncrement getIncrement();

  @Nullable
  DLanguageInitialize getInitialize();

  @Nullable
  DLanguageScopeStatement getScopeStatement();

  @Nullable
  DLanguageTest getTest();

  @NotNull
  PsiElement getKwFor();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpScolon();

}
