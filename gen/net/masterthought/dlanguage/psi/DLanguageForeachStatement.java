// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageForeachStatement extends PsiElement {

  @NotNull
  DLanguageForeach getForeach();

  @NotNull
  DLanguageForeachAggregate getForeachAggregate();

  @NotNull
  DLanguageForeachTypeList getForeachTypeList();

  @NotNull
  DLanguageStatement getStatement();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

  @NotNull
  PsiElement getOpScolon();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageForeachStatement, ...)
  //methods are not found in DPsiImplUtil

}
