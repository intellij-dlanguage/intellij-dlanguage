// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateMixin extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageMixinTemplateName getMixinTemplateName();

  @Nullable
  DLanguageTemplateArguments getTemplateArguments();

  @NotNull
  PsiElement getKwMixin();

  @NotNull
  PsiElement getOpScolon();

}
