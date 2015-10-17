// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCatch extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageDeclarationOrStatement getDeclarationOrStatement();

  @NotNull
  DLanguageType getType();

  @NotNull
  PsiElement getKwCatch();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
