// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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
  DLanguageStorageClasses getStorageClasses();

  @NotNull
  PsiElement getKwAlias();

  @NotNull
  PsiElement getOpScolon();

}
