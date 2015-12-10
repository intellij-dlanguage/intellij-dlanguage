// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageEnumMember extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpEq();

}
