// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.references.DReference;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;

import static com.intellij.psi.util.PsiTreeUtil.getChildOfType;
import static com.intellij.psi.util.PsiTreeUtil.getChildrenOfType;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import com.intellij.psi.stubs.IStubElementType;

import javax.swing.*;

public class DLanguageFuncDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageFunctionDeclarationStub> implements DLanguageFuncDeclaration {

  public DLanguageFuncDeclarationImpl(DLanguageFunctionDeclarationStub stub, IStubElementType type) {
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
  @Nullable
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
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
  public DLanguageMemberFunctionAttributes getMemberFunctionAttributes() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMemberFunctionAttributes.class);
  }

  @Override
  @NotNull
  public DLanguageParameters getParameters() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageParameters.class));
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
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

  @NotNull
  public List<DLanguageParameter> getParameterList() {
      return Arrays.asList(getChildrenOfType(getParameters(), DLanguageParameter.class));
  }

  public boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType) {
      //todo fix
      return false;
  }

  public boolean isSomeVisibility(Visibility visibility) {
      //todo fix
      return false;
  }

  public List<DLanguageTemplateParameter> getTemplateArguments() {
    return DPsiImplUtil.getTemplateArguments(this);
  }

  @NotNull
  public List<DLanguageProtectionAttribute> getProtection() {
      return Collections.singletonList(getChildOfType(this, DLanguageProtectionAttribute.class));
  }

  public boolean isSystem() {
      return false;//todo
  }

  public boolean isNoGC() {
      return false;//todo
  }

  public boolean isTrusted() {
      return false;//todo
  }

  public boolean hasCustomProperty() {
      return false;//todo
  }

  public boolean isSafe() {
      return false;//todo
  }

  public DLanguageUserDefinedAttribute getCustomProperty() {
      return null;//todo
  }

  public boolean isPropertyFunction() {
      return false;//todo
  }

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
