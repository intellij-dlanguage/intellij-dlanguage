// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageEnumDeclaration extends PsiElement {

  @Nullable
  DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration();

  @Nullable
  DLanguageEnumBaseType getEnumBaseType();

  @Nullable
  DLanguageEnumBody getEnumBody();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getKwEnum();

  @Nullable
  PsiElement getOpColon();

}
