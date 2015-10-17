// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIfStatement extends PsiElement {

  @NotNull
  List<DLanguageDeclarationOrStatement> getDeclarationOrStatementList();

  @NotNull
  DLanguageIfCondition getIfCondition();

  @Nullable
  PsiElement getKwElse();

  @NotNull
  PsiElement getKwIf();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
