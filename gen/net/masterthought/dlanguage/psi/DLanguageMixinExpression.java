// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;

public interface DLanguageMixinExpression extends Mixin {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  PsiElement getKwMixin();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @NotNull
  String getName();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageMixinExpression, ...)
  //methods are not found in DPsiImplUtil

}
