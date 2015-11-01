// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageDeclDefImpl extends ASTWrapperPsiElement implements DLanguageDeclDef {

  public DLanguageDeclDefImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDeclDef(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAliasThis getAliasThis() {
    return findChildByClass(DLanguageAliasThis.class);
  }

  @Override
  @Nullable
  public DLanguageAllocator getAllocator() {
    return findChildByClass(DLanguageAllocator.class);
  }

  @Override
  @Nullable
  public DLanguageAttributeSpecifier getAttributeSpecifier() {
    return findChildByClass(DLanguageAttributeSpecifier.class);
  }

  @Override
  @Nullable
  public DLanguageConditionalDeclaration getConditionalDeclaration() {
    return findChildByClass(DLanguageConditionalDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageConstructor getConstructor() {
    return findChildByClass(DLanguageConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageDeallocator getDeallocator() {
    return findChildByClass(DLanguageDeallocator.class);
  }

  @Override
  @Nullable
  public DLanguageDebugSpecification getDebugSpecification() {
    return findChildByClass(DLanguageDebugSpecification.class);
  }

  @Override
  @Nullable
  public DLanguageDeclaration getDeclaration() {
    return findChildByClass(DLanguageDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageDestructor getDestructor() {
    return findChildByClass(DLanguageDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageInvariant getInvariant() {
    return findChildByClass(DLanguageInvariant.class);
  }

  @Override
  @Nullable
  public DLanguageMixinDeclaration getMixinDeclaration() {
    return findChildByClass(DLanguageMixinDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguagePostblit getPostblit() {
    return findChildByClass(DLanguagePostblit.class);
  }

  @Override
  @Nullable
  public DLanguageSharedStaticConstructor getSharedStaticConstructor() {
    return findChildByClass(DLanguageSharedStaticConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageSharedStaticDestructor getSharedStaticDestructor() {
    return findChildByClass(DLanguageSharedStaticDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageStaticAssert getStaticAssert() {
    return findChildByClass(DLanguageStaticAssert.class);
  }

  @Override
  @Nullable
  public DLanguageStaticConstructor getStaticConstructor() {
    return findChildByClass(DLanguageStaticConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageStaticDestructor getStaticDestructor() {
    return findChildByClass(DLanguageStaticDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateDeclaration getTemplateDeclaration() {
    return findChildByClass(DLanguageTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixin getTemplateMixin() {
    return findChildByClass(DLanguageTemplateMixin.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration() {
    return findChildByClass(DLanguageTemplateMixinDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnitTest getUnitTest() {
    return findChildByClass(DLanguageUnitTest.class);
  }

  @Override
  @Nullable
  public DLanguageVersionSpecification getVersionSpecification() {
    return findChildByClass(DLanguageVersionSpecification.class);
  }

}
