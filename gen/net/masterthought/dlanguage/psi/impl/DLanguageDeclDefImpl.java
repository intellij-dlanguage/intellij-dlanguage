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

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitDeclDef(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAliasThis getAliasThis() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAliasThis.class);
  }

  @Override
  @Nullable
  public DLanguageAllocator getAllocator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAllocator.class);
  }

  @Override
  @Nullable
  public DLanguageAttributeSpecifier getAttributeSpecifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAttributeSpecifier.class);
  }

  @Override
  @Nullable
  public DLanguageConditionalDeclaration getConditionalDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConditionalDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageConstructor getConstructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageDeallocator getDeallocator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeallocator.class);
  }

  @Override
  @Nullable
  public DLanguageDebugSpecification getDebugSpecification() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDebugSpecification.class);
  }

  @Override
  @Nullable
  public DLanguageDeclaration getDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageDestructor getDestructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageInvariant getInvariant() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInvariant.class);
  }

  @Override
  @Nullable
  public DLanguageMixinDeclaration getMixinDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMixinDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguagePostblit getPostblit() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePostblit.class);
  }

  @Override
  @Nullable
  public DLanguageSharedStaticConstructor getSharedStaticConstructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSharedStaticConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageSharedStaticDestructor getSharedStaticDestructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSharedStaticDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageStaticAssert getStaticAssert() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStaticAssert.class);
  }

  @Override
  @Nullable
  public DLanguageStaticConstructor getStaticConstructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStaticConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageStaticDestructor getStaticDestructor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStaticDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateDeclaration getTemplateDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixin getTemplateMixin() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateMixin.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateMixinDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnitTesting getUnitTesting() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnitTesting.class);
  }

  @Override
  @Nullable
  public DLanguageVersionSpecification getVersionSpecification() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVersionSpecification.class);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
