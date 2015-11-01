// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageInterfaceDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageBaseInterfaceList getBaseInterfaceList();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInterfaceTemplateDeclaration getInterfaceTemplateDeclaration();

  @Nullable
  PsiElement getKwInterface();

  @Nullable
  PsiElement getOpScolon();

}
