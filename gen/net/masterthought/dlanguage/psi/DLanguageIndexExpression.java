// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIndexExpression extends PsiElement {

  @NotNull
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguagePostfixExpression getPostfixExpression();

  @NotNull
  PsiElement getOpBracketLeft();

  @NotNull
  PsiElement getOpBracketRight();

}
