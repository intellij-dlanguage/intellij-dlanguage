// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasDeclarationX extends PsiElement {

  @Nullable
  DLanguageAliasDeclarationX getAliasDeclarationX();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInitializer getInitializer();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpComma();

  @NotNull
  PsiElement getOpEq();

}
