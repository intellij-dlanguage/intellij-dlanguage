// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageParameters extends PsiElement {

  @NotNull
  List<DLanguageParameter> getParameterList();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @Nullable
  PsiElement getOpTripledot();

}
