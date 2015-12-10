// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIsExpression extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTemplateParameterList getTemplateParameterList();

  @NotNull
  DLanguageType getType();

  @Nullable
  DLanguageTypeSpecialization getTypeSpecialization();

  @NotNull
  PsiElement getKwIs();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpComma();

  @Nullable
  PsiElement getOpEqEq();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
