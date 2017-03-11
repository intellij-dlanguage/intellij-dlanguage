// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageTypeInferredParameterListImpl extends ASTWrapperPsiElement implements DLanguageTypeInferredParameterList {

  public DLanguageTypeInferredParameterListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTypeInferredParameterList(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @Nullable
  public DLanguageMemberFunctionAttributes getMemberFunctionAttributes() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMemberFunctionAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClass getStorageClass() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStorageClass.class);
  }

  @Override
  @Nullable
  public DLanguageTypeCtor getTypeCtor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeCtor.class);
  }

  @Override
  @Nullable
  public DLanguageTypeInferredParameterList getTypeInferredParameterList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeInferredParameterList.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
