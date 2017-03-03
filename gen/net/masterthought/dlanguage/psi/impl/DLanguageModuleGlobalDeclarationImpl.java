// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import java.util.Set;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageModuleGlobalDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageModuleDeclarationStub> implements DLanguageModuleGlobalDeclaration {

  public DLanguageModuleGlobalDeclarationImpl(DLanguageModuleDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageModuleGlobalDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitModuleDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAttribute getAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAttribute.class);
  }

  @Override
  @NotNull
  public DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageModuleFullyQualifiedName.class));
  }

  @Override
  @NotNull
  public PsiElement getKwModule() {
    return notNullChild(findChildByType(KW_MODULE));
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

  public DLanguageProtectionAttribute getProtection() {
    return DPsiImplUtil.getProtection(this);
  }

  public List<DLanguageClassDeclaration> getClassDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getClassDeclarations(this, includeFromMixins);
  }

  public List<DLanguageTemplateDeclaration> getTemplateDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTemplateDeclarations(this, includeFromMixins);
  }

  public List<DLanguageStructDeclaration> getStructDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getStructDeclarations(this, includeFromMixins);
  }

  public List<DLanguageFuncDeclaration> getFunctionDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getFunctionDeclarations(this, includeFromMixins);
  }

  public List<DLanguageVarDeclarations> getVarDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getVarDeclarations(this, includeFromMixins);
  }

  public List<DLanguageVarDeclarations> getTopLevelVarDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTopLevelVarDeclarations(this, includeFromMixins);
  }

  public List<DLanguageFuncDeclaration> getTopLevelFunctionDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTopLevelFunctionDeclarations(this, includeFromMixins);
  }

  public List<DLanguageStructDeclaration> getTopLevelStructDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTopLevelStructDeclarations(this, includeFromMixins);
  }

  public List<DLanguageTemplateDeclaration> getTopLevelTemplateDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTopLevelTemplateDeclarations(this, includeFromMixins);
  }

  public List<DLanguageClassDeclaration> getTopLevelClassDeclarations(boolean includeFromMixins) {
    return DPsiImplUtil.getTopLevelClassDeclarations(this, includeFromMixins);
  }

  public Set<DNamedElement> getPubliclyAccessibleSymbols(boolean includeFromMixins) {
    return DPsiImplUtil.getPubliclyAccessibleSymbols(this, includeFromMixins);
  }

  public Set<DNamedElement> getAllSymbols(boolean includeFromMixins) {
    return DPsiImplUtil.getAllSymbols(this, includeFromMixins);
  }

}
