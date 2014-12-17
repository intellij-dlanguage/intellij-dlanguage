// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTypeofExpression extends PsiElement {

  @Nullable
  DLanguageExpression getExpression();

  @NotNull
  PsiElement getLParen();

  @NotNull
  PsiElement getRParen();

  @Nullable
  PsiElement getReturn();

  @NotNull
  PsiElement getTypeof();

}
