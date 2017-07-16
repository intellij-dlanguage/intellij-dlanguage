// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageEnumFuncDeclaration extends PsiElement {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @NotNull
  DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwAuto();

  @Nullable
  PsiElement getKwEnum();

  @NotNull
  PsiElement getOpEq();

  @NotNull
  PsiElement getOpScolon();

}
