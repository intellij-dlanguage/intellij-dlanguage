// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStatement extends PsiElement {

  @Nullable
  DLanguageBlockStatement getBlockStatement();

  @Nullable
  DLanguageNonEmptyStatement getNonEmptyStatement();

  @Nullable
  PsiElement getOpScolon();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageStatement, ...)
  //methods are not found in DPsiImplUtil

}
