// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;

public interface DLanguageMixinDeclaration extends Mixin {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @NotNull
  PsiElement getKwMixin();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @NotNull
  PsiElement getOpScolon();

  @Nullable
  DLanguageTemplateDeclaration getTemplateDeclaration();

  @Nullable
  DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration();

}
