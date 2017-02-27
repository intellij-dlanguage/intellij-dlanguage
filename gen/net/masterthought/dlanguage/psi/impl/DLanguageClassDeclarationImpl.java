// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageClassDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageClassDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageClassDeclarationStub> implements DLanguageClassDeclaration {

  public DLanguageClassDeclarationImpl(DLanguageClassDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageClassDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitClassDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAggregateBody getAggregateBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAggregateBody.class);
  }

  @Override
  @Nullable
  public DLanguageBaseClassList getBaseClassList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBaseClassList.class);
  }

  @Override
  @Nullable
  public DLanguageClassTemplateDeclaration getClassTemplateDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageClassTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public PsiElement getKwClass() {
    return findChildByType(KW_CLASS);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

  @NotNull
  public String getName() {
    return DPsiImplUtil.getName(this);
  }

  @Nullable
  public PsiElement getNameIdentifier() {
    return DPsiImplUtil.getNameIdentifier(this);
  }

  @NotNull
  public PsiReference getReference() {
    return DPsiImplUtil.getReference(this);
  }

  @Nullable
  public PsiElement setName(String newName) {
    return DPsiImplUtil.setName(this, newName);
  }

  @NotNull
  public ItemPresentation getPresentation() {
    return DPsiImplUtil.getPresentation(this);
  }

  public List<DLanguageTemplateParameter> getTemplateArguments(boolean includeFromInheritance, boolean includeFromMixins) {
    return DPsiImplUtil.getTemplateArguments(this, includeFromInheritance, includeFromMixins);
  }

  public List<DLanguageFuncDeclaration> getMethods(boolean includeFromInheritance, boolean includeFromMixins) {
    return DPsiImplUtil.getMethods(this, includeFromInheritance, includeFromMixins);
  }

  public List<DLanguageVarDeclarations> getVariables(boolean includeFromInheritance, boolean includeFromMixins) {
    return DPsiImplUtil.getVariables(this, includeFromInheritance, includeFromMixins);
  }

  public List<DLanguageFuncDeclaration> getPropertyMethods(boolean includeFromInheritance, boolean includeFromMixins) {
    return DPsiImplUtil.getPropertyMethods(this, includeFromInheritance, includeFromMixins);
  }

  public DLanguageProtectionAttribute getProtection() {
    return DPsiImplUtil.getProtection(this);
  }

}
