// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageGotoStatement extends PsiElement {

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getKwCase();

  @Nullable
  PsiElement getKwDefault();

  @NotNull
  PsiElement getKwGoto();

  @NotNull
  PsiElement getOpScolon();

}
