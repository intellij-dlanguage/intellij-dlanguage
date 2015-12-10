// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageFuncDeclaration;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateDeclaration getAggregateDeclaration();

  @Nullable
  DLanguageAliasDeclaration getAliasDeclaration();

  @Nullable
  DLanguageEnumDeclaration getEnumDeclaration();

  @Nullable
  DLanguageFuncDeclaration getFuncDeclaration();

  @Nullable
  DLanguageImportDeclaration getImportDeclaration();

  @Nullable
  DLanguageTemplateDeclaration getTemplateDeclaration();

  @Nullable
  DLanguageVarDeclarations getVarDeclarations();

}
