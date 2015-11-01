// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAutoFuncDeclaration extends PsiElement {

  @NotNull
  DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix();

  @NotNull
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageStorageClasses getStorageClasses();

}
