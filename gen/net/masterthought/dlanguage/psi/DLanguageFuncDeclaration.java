// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageFuncDeclaration extends PsiElement {

  @Nullable
  DLanguageAutoFuncDeclaration getAutoFuncDeclaration();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageFuncDeclarator getFuncDeclarator();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpScolon();

}
