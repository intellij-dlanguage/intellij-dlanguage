// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAddExpression extends PsiElement {

  @Nullable
  DLanguageAddExpression getAddExpression();

  @NotNull
  DLanguageMulExpression getMulExpression();

  @Nullable
  PsiElement getOpMinus();

  @Nullable
  PsiElement getOpPlus();

  @Nullable
  PsiElement getOpTilda();

}
