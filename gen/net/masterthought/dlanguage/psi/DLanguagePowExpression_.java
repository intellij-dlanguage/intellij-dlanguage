// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePowExpression_ extends PsiElement {

  @NotNull
  DLanguagePostfixExpression getPostfixExpression();

  @Nullable
  DLanguagePowExpression_ getPowExpression_();

  @NotNull
  DLanguageUnaryExpression getUnaryExpression();

  @NotNull
  PsiElement getOpPow();

}
