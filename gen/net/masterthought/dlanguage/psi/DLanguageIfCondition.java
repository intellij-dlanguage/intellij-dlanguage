// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIfCondition extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageExpression getExpression();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwAuto();

  @Nullable
  PsiElement getOpEq();

}
