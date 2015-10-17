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

public class DLanguageDeclaration2Impl extends ASTWrapperPsiElement implements DLanguageDeclaration2 {

  public DLanguageDeclaration2Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDeclaration2(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclaration getAliasDeclaration() {
    return findChildByClass(DLanguageAliasDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageAliasThisDeclaration getAliasThisDeclaration() {
    return findChildByClass(DLanguageAliasThisDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageAttributeDeclaration getAttributeDeclaration() {
    return findChildByClass(DLanguageAttributeDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageClassDeclaration getClassDeclaration() {
    return findChildByClass(DLanguageClassDeclaration.class);
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
  @NotNull
  public List<DLanguageDeclaration> getDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageDestructor getDestructor() {
    return findChildByClass(DLanguageDestructor.class);
  }

  @Override
  @Nullable
  public DLanguageEnumDeclaration getEnumDeclaration() {
    return findChildByClass(DLanguageEnumDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionDeclaration getFunctionDeclaration() {
    return findChildByClass(DLanguageFunctionDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageImportDeclaration getImportDeclaration() {
    return findChildByClass(DLanguageImportDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageInterfaceDeclaration getInterfaceDeclaration() {
    return findChildByClass(DLanguageInterfaceDeclaration.class);
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
  public DLanguageMixinTemplateDeclaration getMixinTemplateDeclaration() {
    return findChildByClass(DLanguageMixinTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguagePragmaDeclaration getPragmaDeclaration() {
    return findChildByClass(DLanguagePragmaDeclaration.class);
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
  public DLanguageStaticAssertDeclaration getStaticAssertDeclaration() {
    return findChildByClass(DLanguageStaticAssertDeclaration.class);
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
  public DLanguageStructDeclaration getStructDeclaration() {
    return findChildByClass(DLanguageStructDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateDeclaration getTemplateDeclaration() {
    return findChildByClass(DLanguageTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnionDeclaration getUnionDeclaration() {
    return findChildByClass(DLanguageUnionDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnittest getUnittest() {
    return findChildByClass(DLanguageUnittest.class);
  }

  @Override
  @Nullable
  public DLanguageVariableDeclaration getVariableDeclaration() {
    return findChildByClass(DLanguageVariableDeclaration.class);
  }

  @Override
  @Nullable
  public PsiElement getOpBracesLeft() {
    return findChildByType(OP_BRACES_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpBracesRight() {
    return findChildByType(OP_BRACES_RIGHT);
  }

}
