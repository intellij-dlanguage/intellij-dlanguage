// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateMixinDeclaration extends PsiElement {

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageDeclDefs getDeclDefs();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwMixin();

  @NotNull
  PsiElement getKwTemplate();

  @NotNull
  PsiElement getOpBracesLeft();

  @NotNull
  PsiElement getOpBracesRight();

}
