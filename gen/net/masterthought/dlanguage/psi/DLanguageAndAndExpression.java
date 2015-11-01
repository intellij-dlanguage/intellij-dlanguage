// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAndAndExpression extends PsiElement {

  @Nullable
  DLanguageAndAndExpression getAndAndExpression();

  @NotNull
  List<DLanguageCmpExpression> getCmpExpressionList();

  @Nullable
  DLanguageOrExpression getOrExpression();

  @Nullable
  PsiElement getOpBoolAnd();

}
