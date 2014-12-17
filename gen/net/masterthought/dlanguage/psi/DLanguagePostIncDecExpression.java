// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePostIncDecExpression extends PsiElement {

  @NotNull
  DLanguageUnaryExpression getUnaryExpression();

  @Nullable
  PsiElement getDecrement();

  @Nullable
  PsiElement getIncrement();

}
