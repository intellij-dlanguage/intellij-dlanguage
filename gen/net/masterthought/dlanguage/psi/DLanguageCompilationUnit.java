// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCompilationUnit extends PsiElement {

  @NotNull
  DLanguageImportStatement getImportStatement();

  @Nullable
  DLanguagePackageStatement getPackageStatement();

  @NotNull
  DLanguageTypeDeclaration getTypeDeclaration();

}
