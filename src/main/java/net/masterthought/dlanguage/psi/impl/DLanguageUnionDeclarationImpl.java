// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.references.DReference;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageUnionDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageUnionDeclarationStub> implements DLanguageUnionDeclaration {

  public DLanguageUnionDeclarationImpl(DLanguageUnionDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageUnionDeclarationImpl(ASTNode node) {
    super(node);
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
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwUnion() {
    return notNullChild(findChildByType(KW_UNION));
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

  @Nullable
  public PsiElement setName(String newName) {
    return DPsiImplUtil.setName(this, newName);
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

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
