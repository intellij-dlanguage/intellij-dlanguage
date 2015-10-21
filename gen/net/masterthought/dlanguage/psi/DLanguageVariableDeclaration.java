// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageVariableDeclaration extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageAutoDeclaration getAutoDeclaration();

  @NotNull
  List<DLanguageDeclarator> getDeclaratorList();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  List<DLanguageStorageClass> getStorageClassList();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpScolon();

}
