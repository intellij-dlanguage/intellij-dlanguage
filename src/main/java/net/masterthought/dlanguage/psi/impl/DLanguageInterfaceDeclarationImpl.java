// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;

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

import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import java.util.Map;

import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageInterfaceDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageInterfaceDeclarationStub> implements DLanguageInterfaceDeclaration {

  public DLanguageInterfaceDeclarationImpl(DLanguageInterfaceDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageInterfaceDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitInterfaceDeclaration(this);
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
  public DLanguageBaseInterfaceList getBaseInterfaceList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBaseInterfaceList.class);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwInterface() {
    return notNullChild(findChildByType(KW_INTERFACE));
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

  @NotNull
  public PsiElement setName(String newName) {
      getIdentifier().setName(newName);
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

  public List<CanInherit> whatInheritsFrom() {
    return DPsiImplUtil.whatInheritsFrom(this);
  }

  public Map<String, DLanguageIdentifier> getSuperClassNames() {
    return DPsiImplUtil.getSuperClassNames(this);
  }

}
