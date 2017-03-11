// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.StubBasedPsiElementBase;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageUnionDeclarationImpl extends StubBasedPsiElementBase<DLanguageUnionDeclarationStub> implements DLanguageUnionDeclaration {

  public DLanguageUnionDeclarationImpl(DLanguageUnionDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageUnionDeclarationImpl(ASTNode node) {
    super(node);
  }

  public DLanguageUnionDeclarationImpl(DLanguageUnionDeclarationStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitUnionDeclaration(this);
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
  public DLanguageAnonUnionDeclaration getAnonUnionDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAnonUnionDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageUnionTemplateDeclaration getUnionTemplateDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnionTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public PsiElement getKwUnion() {
    return findChildByType(KW_UNION);
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

  public boolean isSomeVisibility(String visibility) {
    return DPsiImplUtil.isSomeVisibility(this, visibility);
  }

}
