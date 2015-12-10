// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasThis extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  PsiElement getKwAlias();

  @NotNull
  PsiElement getKwThis();

  @NotNull
  PsiElement getOpScolon();

}
