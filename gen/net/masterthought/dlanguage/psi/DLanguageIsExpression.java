// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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

  @Nullable
  PsiElement getKwIs();

  @Nullable
  PsiElement getKwNotIs();

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
