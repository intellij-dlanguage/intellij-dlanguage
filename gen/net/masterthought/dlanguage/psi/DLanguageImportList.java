// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportList extends PsiElement {

  @NotNull
  DLanguageImport getImport();

  @Nullable
  DLanguageImportBindList getImportBindList();

  @Nullable
  DLanguageImportList getImportList();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpComma();

}
