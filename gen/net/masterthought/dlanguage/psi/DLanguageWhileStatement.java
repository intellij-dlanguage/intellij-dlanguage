// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageWhileStatement extends PsiElement {

  @NotNull
  DLanguageDeclarationOrStatement getDeclarationOrStatement();

  @NotNull
  DLanguageExpression getExpression();

  @NotNull
  PsiElement getKwWhile();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
