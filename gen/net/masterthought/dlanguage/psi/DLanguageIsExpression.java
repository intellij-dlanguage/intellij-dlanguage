// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIsExpression extends PsiElement {

  @Nullable
  DLanguageTemplateParameterList getTemplateParameterList();

  @NotNull
  DLanguageType getType();

  @Nullable
  DLanguageTypeSpecialization getTypeSpecialization();

  @Nullable
  PsiElement getComma();

  @Nullable
  PsiElement getEqual();

  @NotNull
  PsiElement getIs();

  @NotNull
  PsiElement getLParen();

  @NotNull
  PsiElement getRParen();

}
