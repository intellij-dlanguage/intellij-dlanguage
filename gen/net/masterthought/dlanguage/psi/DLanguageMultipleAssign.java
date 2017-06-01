// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageMultipleAssign extends PsiElement {

  @NotNull
  List<DLanguageAssignExpression> getAssignExpressionList();

  @Nullable
  DLanguageMultipleAssign getMultipleAssign();

  @Nullable
  PsiElement getOpComma();

  @Nullable
  PsiElement getOpDdot();

}
