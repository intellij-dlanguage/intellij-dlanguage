// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageVersionCondition extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getIntegerLiteral();

  @Nullable
  PsiElement getKwAssert();

  @Nullable
  PsiElement getKwUnittest();

  @NotNull
  PsiElement getKwVersion();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
