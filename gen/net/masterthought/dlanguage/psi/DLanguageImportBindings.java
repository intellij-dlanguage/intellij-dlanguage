// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportBindings extends PsiElement {

  @NotNull
  List<DLanguageImportBind> getImportBindList();

  @NotNull
  DLanguageSingleImport getSingleImport();

  @NotNull
  PsiElement getOpColon();

}
