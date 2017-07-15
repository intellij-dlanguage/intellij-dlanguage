// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.references.DReference;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageParameterStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageParameterImpl extends DNamedStubbedPsiElementBase<DLanguageParameterStub> implements DLanguageParameter {

  public DLanguageParameterImpl(DLanguageParameterStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarator getDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInOut getInOut() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInOut.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAlias() {
    return findChildByType(KW_ALIAS);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpTripledot() {
    return findChildByType(OP_TRIPLEDOT);
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
      ASTNode keyNode = getNode();
      return keyNode != null ? keyNode.getPsi() : null;
  }

  @NotNull
  public PsiReference getReference() {
      return new DReference(this, TextRange.from(0, DPsiImplUtil.getName(this).length()));
  }

  @NotNull
  public PsiElement setName(String newName) {
      if (DPsiImplUtil.getIdentifier(this) == null) {
          throw new IllegalStateException("cannot rename");
      }
      DPsiImplUtil.getIdentifier(this).setName(newName);
      return this;
  }

  @NotNull
  public ItemPresentation getPresentation() {
      return new ItemPresentation() {
          @NotNull
          @Override
          public String getPresentableText() {
              return getName();
          }

          /**
           * This is needed to decipher between files when resolving multiple references.
           */
          @Nullable
          @Override
          public String getLocationString() {
              final PsiFile psiFile = getContainingFile();
              return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
          }

          @Nullable
          @Override
          public Icon getIcon(boolean unused) {
              return DLanguageIcons.FILE;
          }
      };
  }

  public boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType) {
      //todo fix
      return false;
  }

  public boolean isSomeVisibility(Visibility visibility) {
      //todo fix
      return false;
  }

}
