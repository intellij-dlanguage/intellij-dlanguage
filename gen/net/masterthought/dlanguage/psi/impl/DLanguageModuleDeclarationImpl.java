// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageModuleDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageModuleDeclarationStub> implements DLanguageModuleDeclaration {

  public DLanguageModuleDeclarationImpl(DLanguageModuleDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageModuleDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitModuleDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAttribute getAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAttribute.class);
  }

  @Override
  @NotNull
  public DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageModuleFullyQualifiedName.class));
  }

  @Override
  @NotNull
  public PsiElement getKwModule() {
    return notNullChild(findChildByType(KW_MODULE));
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return notNullChild(findChildByType(OP_SCOLON));
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
