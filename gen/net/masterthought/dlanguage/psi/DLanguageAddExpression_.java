// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAddExpression_ extends PsiElement {

  @Nullable
  DLanguageAddExpression_ getAddExpression_();

  @Nullable
  DLanguageMulExpression_ getMulExpression_();

  @NotNull
  DLanguageUnaryExpression getUnaryExpression();

  @Nullable
  PsiElement getOpMinus();

  @Nullable
  PsiElement getOpPlus();

  @Nullable
  PsiElement getOpTilda();

}
