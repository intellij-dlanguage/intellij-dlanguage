// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDeclaration2 extends PsiElement {

  @Nullable
  DLanguageAliasDeclaration getAliasDeclaration();

  @Nullable
  DLanguageAliasThisDeclaration getAliasThisDeclaration();

  @Nullable
  DLanguageAttributeDeclaration getAttributeDeclaration();

  @Nullable
  DLanguageClassDeclaration getClassDeclaration();

  @Nullable
  DLanguageConditionalDeclaration getConditionalDeclaration();

  @Nullable
  DLanguageConstructor getConstructor();

  @NotNull
  List<DLanguageDeclaration> getDeclarationList();

  @Nullable
  DLanguageDestructor getDestructor();

  @Nullable
  DLanguageEnumDeclaration getEnumDeclaration();

  @Nullable
  DLanguageFunctionDeclaration getFunctionDeclaration();

  @Nullable
  DLanguageImportDeclaration getImportDeclaration();

  @Nullable
  DLanguageInterfaceDeclaration getInterfaceDeclaration();

  @Nullable
  DLanguageInvariant getInvariant();

  @Nullable
  DLanguageMixinDeclaration getMixinDeclaration();

  @Nullable
  DLanguageMixinTemplateDeclaration getMixinTemplateDeclaration();

  @Nullable
  DLanguagePragmaDeclaration getPragmaDeclaration();

  @Nullable
  DLanguageSharedStaticConstructor getSharedStaticConstructor();

  @Nullable
  DLanguageSharedStaticDestructor getSharedStaticDestructor();

  @Nullable
  DLanguageStaticAssertDeclaration getStaticAssertDeclaration();

  @Nullable
  DLanguageStaticConstructor getStaticConstructor();

  @Nullable
  DLanguageStaticDestructor getStaticDestructor();

  @Nullable
  DLanguageStructDeclaration getStructDeclaration();

  @Nullable
  DLanguageTemplateDeclaration getTemplateDeclaration();

  @Nullable
  DLanguageUnionDeclaration getUnionDeclaration();

  @Nullable
  DLanguageUnittest getUnittest();

  @Nullable
  DLanguageVariableDeclaration getVariableDeclaration();

  @Nullable
  PsiElement getOpBracesLeft();

  @Nullable
  PsiElement getOpBracesRight();

}
