// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageTemplateMixinDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageTemplateMixinDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageTemplateMixinDeclarationStub> implements DLanguageTemplateMixinDeclaration {

  public DLanguageTemplateMixinDeclarationImpl(DLanguageTemplateMixinDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageTemplateMixinDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateMixinDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
  }

  @Override
  @Nullable
  public DLanguageDeclDefs getDeclDefs() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclDefs.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @NotNull
  public DLanguageTemplateParameters getTemplateParameters() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class));
  }

  @Override
  @NotNull
  public PsiElement getKwMixin() {
    return notNullChild(findChildByType(KW_MIXIN));
  }

  @Override
  @NotNull
  public PsiElement getKwTemplate() {
    return notNullChild(findChildByType(KW_TEMPLATE));
  }

  @Override
  @NotNull
  public PsiElement getOpBracesLeft() {
    return notNullChild(findChildByType(OP_BRACES_LEFT));
  }

  @Override
  @NotNull
  public PsiElement getOpBracesRight() {
    return notNullChild(findChildByType(OP_BRACES_RIGHT));
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

}
