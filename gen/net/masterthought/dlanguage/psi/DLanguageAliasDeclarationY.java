// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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
