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

public class DLanguageTypeSuffixImpl extends ASTWrapperPsiElement implements DLanguageTypeSuffix {

  public DLanguageTypeSuffixImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTypeSuffix(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return findChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMemberFunctionAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageParameters getParameters() {
    return findChildByClass(DLanguageParameters.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getDelegate() {
    return findChildByType(DELEGATE);
  }

  @Override
  @Nullable
  public PsiElement getFunction() {
    return findChildByType(FUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getLBracket() {
    return findChildByType(LBRACKET);
  }

  @Override
  @Nullable
  public PsiElement getRBracket() {
    return findChildByType(RBRACKET);
  }

  @Override
  @Nullable
  public PsiElement getSlice() {
    return findChildByType(SLICE);
  }

  @Override
  @Nullable
  public PsiElement getStar() {
    return findChildByType(STAR);
  }

}
