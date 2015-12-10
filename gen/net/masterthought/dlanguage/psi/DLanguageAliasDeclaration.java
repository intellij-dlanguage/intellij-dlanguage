// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasDeclaration extends PsiElement {

  @Nullable
  DLanguageAliasDeclarationX getAliasDeclarationX();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageFuncDeclarator getFuncDeclarator();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  DLanguageTemplateArguments getTemplateArguments();

  @Nullable
  DLanguageType getType();

  @NotNull
  PsiElement getKwAlias();

  @Nullable
  PsiElement getOpEq();

  @NotNull
  PsiElement getOpScolon();

}
