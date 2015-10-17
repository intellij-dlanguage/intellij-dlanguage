// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTernaryExpression extends PsiElement {

  @Nullable
  DLanguageExpression getExpression();

  @NotNull
  DLanguageOrOrExpression getOrOrExpression();

  @Nullable
  DLanguageTernaryExpression getTernaryExpression();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpQuest();

}
