// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageFuncDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageFuncDeclarationStub> implements DLanguageFuncDeclaration {

  public DLanguageFuncDeclarationImpl(DLanguageFuncDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageFuncDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitFuncDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType2 getBasicType2() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType2.class);
  }

  @Override
  @NotNull
  public DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageFuncDeclaratorSuffix.class));
  }

  @Override
  @Nullable
  public DLanguageFunctionBody getFunctionBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
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

  @NotNull
  public List<DLanguageParameter> getArguments() {
    return DPsiImplUtil.getArguments(this);
  }

  public List<DLanguageTemplateParameter> getTemplateArguments() {
    return DPsiImplUtil.getTemplateArguments(this);
  }

}
