// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageLinkageAttribute extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIdentifierList getIdentifierList();

  @Nullable
  DLanguageLinkageType getLinkageType();

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
