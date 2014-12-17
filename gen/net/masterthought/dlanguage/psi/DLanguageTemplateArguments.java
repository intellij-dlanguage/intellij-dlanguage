// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateArguments extends PsiElement {

  @Nullable
  DLanguageTemplateArgumentList getTemplateArgumentList();

  @Nullable
  DLanguageTemplateSingleArgument getTemplateSingleArgument();

  @Nullable
  PsiElement getLParen();

  @NotNull
  PsiElement getNot();

  @Nullable
  PsiElement getRParen();

}
