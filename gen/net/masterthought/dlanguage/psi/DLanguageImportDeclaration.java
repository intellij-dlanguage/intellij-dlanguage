// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportDeclaration extends PsiElement {

  @NotNull
  DLanguageImportList getImportList();

  @NotNull
  PsiElement getKwImport();

  @Nullable
  PsiElement getKwStatic();

  @NotNull
  PsiElement getOpScolon();

}
