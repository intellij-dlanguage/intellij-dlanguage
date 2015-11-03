// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDeclDef extends PsiElement {

  @Nullable
  DLanguageAliasThis getAliasThis();

  @Nullable
  DLanguageAllocator getAllocator();

  @Nullable
  DLanguageAttributeSpecifier getAttributeSpecifier();

  @Nullable
  DLanguageConditionalDeclaration getConditionalDeclaration();

  @Nullable
  DLanguageConstructor getConstructor();

  @Nullable
  DLanguageDeallocator getDeallocator();

  @Nullable
  DLanguageDebugSpecification getDebugSpecification();

  @Nullable
  DLanguageDeclaration getDeclaration();

  @Nullable
  DLanguageDestructor getDestructor();

  @Nullable
  DLanguageInvariant getInvariant();

  @Nullable
  DLanguageMixinDeclaration getMixinDeclaration();

  @Nullable
  DLanguagePostblit getPostblit();

  @Nullable
  DLanguageSharedStaticConstructor getSharedStaticConstructor();

  @Nullable
  DLanguageSharedStaticDestructor getSharedStaticDestructor();

  @Nullable
  DLanguageStaticAssert getStaticAssert();

  @Nullable
  DLanguageStaticConstructor getStaticConstructor();

  @Nullable
  DLanguageStaticDestructor getStaticDestructor();

  @Nullable
  DLanguageTemplateDeclaration getTemplateDeclaration();

  @Nullable
  DLanguageTemplateMixin getTemplateMixin();

  @Nullable
  DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration();

  @Nullable
  DLanguageUnitTesting getUnitTesting();

  @Nullable
  DLanguageVersionSpecification getVersionSpecification();

}
