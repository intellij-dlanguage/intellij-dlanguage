// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;

public interface DLanguageTemplateMixin extends Mixin {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageMixinTemplateName getMixinTemplateName();

  @Nullable
  DLanguageTemplateArguments getTemplateArguments();

  @NotNull
  PsiElement getKwMixin();

  @Nullable
  PsiElement getOpScolon();

  @NotNull
  String getName();

}
