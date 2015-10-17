// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageLinkageAttribute extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIdentifierChain getIdentifierChain();

  @NotNull
  PsiElement getKwExtern();

  @Nullable
  PsiElement getOpComma();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpPlusPlus();

}
