// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCaseRangeStatement extends PsiElement {

  @NotNull
  DLanguageFirstExp getFirstExp();

  @NotNull
  DLanguageLastExp getLastExp();

  @NotNull
  DLanguageScopeStatementList getScopeStatementList();

  @NotNull
  PsiElement getOpDdot();

}
