// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageModuleDeclaration extends PsiElement {

  @Nullable
  DLanguageAttribute getAttribute();

  @Nullable
  DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName();

  @NotNull
  PsiElement getKwModule();

  @Nullable
  PsiElement getOpScolon();

}
