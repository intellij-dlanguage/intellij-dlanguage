// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageConditionalStatement extends PsiElement {

  @Nullable
  DLanguageBlockStatement getBlockStatement();

  @NotNull
  DLanguageCondition getCondition();

  @NotNull
  List<DLanguageDeclarationBlock> getDeclarationBlockList();

  @NotNull
  List<DLanguageStatement> getStatementList();

  @Nullable
  PsiElement getKwElse();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageConditionalStatement, ...)
  //methods are not found in DPsiImplUtil

}
