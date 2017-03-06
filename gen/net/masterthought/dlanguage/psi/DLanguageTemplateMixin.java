// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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

  DLanguageTemplateDeclaration getTemplateDeclaration();

  DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration();

}
