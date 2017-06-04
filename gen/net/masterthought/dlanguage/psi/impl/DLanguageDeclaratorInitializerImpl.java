// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageDeclaratorInitializerStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import net.masterthought.dlanguage.psi.interfaces.Type;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageDeclaratorInitializerImpl extends DNamedStubbedPsiElementBase<DLanguageDeclaratorInitializerStub> implements DLanguageDeclaratorInitializer {

  public DLanguageDeclaratorInitializerImpl(DLanguageDeclaratorInitializerStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageDeclaratorInitializerImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitDeclaratorInitializer(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclarator getAltDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAltDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @Nullable
  public DLanguageVarDeclarator getVarDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVarDeclarator.class);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
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

  @Nullable
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

  public boolean isSomeVisibility(Visibility visibility) {
    return DPsiImplUtil.isSomeVisibility(this, visibility);
  }

  public boolean actuallyIsDeclaration() {
    return DPsiImplUtil.actuallyIsDeclaration(this);
  }

  public Type getVariableDeclarationType() {
    return DPsiImplUtil.getVariableDeclarationType(this);
  }

}
