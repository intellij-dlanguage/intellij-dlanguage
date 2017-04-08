// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTraitsExpression extends PsiElement {

  @Nullable
  DLanguageTraitsArguments getTraitsArguments();

  @Nullable
  DLanguageTraitsKeyword getTraitsKeyword();

  @NotNull
  PsiElement getKwTraits();

  @Nullable
  PsiElement getOpComma();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
