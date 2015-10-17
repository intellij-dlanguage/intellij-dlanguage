// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageEponymousTemplateDeclaration extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwEnum();

  @NotNull
  PsiElement getOpEq();

  @NotNull
  PsiElement getOpScolon();

}
