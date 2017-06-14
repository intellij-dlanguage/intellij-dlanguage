// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

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

  @Nullable
  DLanguageVarFuncDeclaration getVarFuncDeclaration();

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
