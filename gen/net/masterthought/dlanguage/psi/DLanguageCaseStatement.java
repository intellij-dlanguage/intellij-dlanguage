// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCaseStatement extends PsiElement {

  @Nullable
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguageScopeStatementList getScopeStatementList();

  @NotNull
  PsiElement getKwCase();

  @Nullable
  PsiElement getOpColon();

}
