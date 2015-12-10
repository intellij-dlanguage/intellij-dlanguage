// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasDeclarationY extends PsiElement {

  @Nullable
  DLanguageFunctionLiteral getFunctionLiteral();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageType getType();

  @NotNull
  PsiElement getOpEq();

}
