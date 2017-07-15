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
import net.masterthought.dlanguage.stubs.DLanguageConditionVariableDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageConditionVariableDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageConditionVariableDeclarationStub> implements DLanguageConditionVariableDeclaration {

  public DLanguageConditionVariableDeclarationImpl(DLanguageConditionVariableDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageConditionVariableDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitConditionVariableDeclaration(this);
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
  @NotNull
  public DLanguageCommaExpression getCommaExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageCommaExpression.class));
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
  public DLanguageTypeCtors getTypeCtors() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeCtors.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAuto() {
    return findChildByType(KW_AUTO);
  }

  @Override
  @NotNull
  public PsiElement getOpEq() {
    return notNullChild(findChildByType(OP_EQ));
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

}
