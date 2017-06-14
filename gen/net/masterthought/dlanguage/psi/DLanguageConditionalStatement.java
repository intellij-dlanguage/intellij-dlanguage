// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

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

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
