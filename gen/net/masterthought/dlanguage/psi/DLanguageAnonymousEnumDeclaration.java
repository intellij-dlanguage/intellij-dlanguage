// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAnonymousEnumDeclaration extends PsiElement {

  @Nullable
  DLanguageAnonymousEnumMembers getAnonymousEnumMembers();

  @Nullable
  DLanguageEnumMembers getEnumMembers();

  @Nullable
  DLanguageType getType();

  @NotNull
  PsiElement getKwEnum();

  @Nullable
  PsiElement getOpBracesLeft();

  @Nullable
  PsiElement getOpBracesRight();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpScolon();

}
