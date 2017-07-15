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
import net.masterthought.dlanguage.stubs.DLanguageTemplateParameterStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageTemplateParameterImpl extends DNamedStubbedPsiElementBase<DLanguageTemplateParameterStub> implements DLanguageTemplateParameter {

  public DLanguageTemplateParameterImpl(DLanguageTemplateParameterStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageTemplateParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTemplateAliasParameter getTemplateAliasParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateAliasParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateThisParameter getTemplateThisParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateThisParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateTupleParameter getTemplateTupleParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateTupleParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateTypeParameter getTemplateTypeParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateTypeParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateValueParameter getTemplateValueParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateValueParameter.class);
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

  public boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType) {
      //todo fix
      return false;
  }

  public boolean isSomeVisibility(Visibility visibility) {
      //todo fix
      return false;
  }

}
