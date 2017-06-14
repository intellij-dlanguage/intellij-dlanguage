// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageAliasDeclarationSingleStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageAliasDeclarationSingleImpl extends DNamedStubbedPsiElementBase<DLanguageAliasDeclarationSingleStub> implements DLanguageAliasDeclarationSingle {

  public DLanguageAliasDeclarationSingleImpl(DLanguageAliasDeclarationSingleStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageAliasDeclarationSingleImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAliasDeclarationSingle(this);
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
  @Nullable
  public DLanguageDeclarator getDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFuncDeclaratorSuffix.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateArguments getTemplateArguments() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArguments.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
  }

  @Override
  @NotNull
  public PsiElement getKwAlias() {
    return notNullChild(findChildByType(KW_ALIAS));
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
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

  public String getFullName() {
    return DPsiImplUtil.getFullName(this);
  }

  @Nullable
  public PsiElement getNameIdentifier() {
    return DPsiImplUtil.getNameIdentifier(this);
  }

  @NotNull
  public PsiReference getReference() {
    return DPsiImplUtil.getReference(this);
  }

  @NotNull
  public PsiElement setName(String newName) {
    return DPsiImplUtil.setName(this, newName);
  }

  @NotNull
  public ItemPresentation getPresentation() {
    return DPsiImplUtil.getPresentation(this);
  }

  public boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType) {
    return DPsiImplUtil.isSomeVisibility(this, visibility, containerType);
  }

  public boolean actuallyIsDeclaration() {
    return DPsiImplUtil.actuallyIsDeclaration(this);
  }

}
