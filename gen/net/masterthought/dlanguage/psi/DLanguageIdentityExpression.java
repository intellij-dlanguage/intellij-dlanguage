// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIdentityExpression extends PsiElement {

  @NotNull
  DLanguageShiftExpression getShiftExpression();

  @Nullable
  PsiElement getKwIs();

  @Nullable
  PsiElement getKwNotIs();

}
