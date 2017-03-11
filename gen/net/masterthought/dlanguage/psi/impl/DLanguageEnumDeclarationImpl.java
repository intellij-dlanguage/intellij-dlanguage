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
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageEnumDeclarationImpl extends StubBasedPsiElementBase<DLanguageEnumDeclarationStub> implements DLanguageEnumDeclaration {

  public DLanguageEnumDeclarationImpl(DLanguageEnumDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageEnumDeclarationImpl(ASTNode node) {
    super(node);
  }

  public DLanguageEnumDeclarationImpl(DLanguageEnumDeclarationStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitEnumDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAnonymousEnumDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageEnumBaseType getEnumBaseType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEnumBaseType.class);
  }

  @Override
  @Nullable
  public DLanguageEnumBody getEnumBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEnumBody.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public PsiElement getKwEnum() {
    return findChildByType(KW_ENUM);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
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
