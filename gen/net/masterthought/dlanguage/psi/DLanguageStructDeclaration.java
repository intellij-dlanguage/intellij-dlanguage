// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStructDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAnonStructDeclaration getAnonStructDeclaration();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageStructTemplateDeclaration getStructTemplateDeclaration();

  @Nullable
  PsiElement getKwStruct();

  @Nullable
  PsiElement getOpScolon();

}
