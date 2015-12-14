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
  DLanguageBasicType2 getBasicType2();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix();

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
