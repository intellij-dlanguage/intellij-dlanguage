// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageParameter extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  List<DLanguageParameterAttribute> getParameterAttributeList();

  @NotNull
  DLanguageType getType();

  @Nullable
  PsiElement getAssign();

  @Nullable
  PsiElement getVararg();

}
