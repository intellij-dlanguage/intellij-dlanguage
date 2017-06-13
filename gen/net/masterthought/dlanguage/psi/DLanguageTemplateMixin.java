// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;

public interface DLanguageTemplateMixin extends Mixin {

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

  @NotNull
  String getName();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageTemplateMixin, ...)
  //methods are not found in DPsiImplUtil

}
