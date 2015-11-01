// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUnionDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAnonUnionDeclaration getAnonUnionDeclaration();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageUnionTemplateDeclaration getUnionTemplateDeclaration();

  @Nullable
  PsiElement getKwUnion();

  @Nullable
  PsiElement getOpScolon();

}
