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

public class DLanguageTemplateAliasParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateAliasParameter {

  public DLanguageTemplateAliasParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateAliasParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAddExpression_> getAddExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAddExpression_.class);
  }

  @Override
  @NotNull
  public List<DLanguageAndExxpression_> getAndExxpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAndExxpression_.class);
  }

  @Override
  @NotNull
  public List<DLanguageConditionalExpression_> getConditionalExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageConditionalExpression_.class);
  }

  @Override
  @NotNull
  public List<DLanguageEqualExpression> getEqualExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageEqualExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageIdentityExpression> getIdentityExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentityExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageInExpression> getInExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageInExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageMulExpression_> getMulExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMulExpression_.class);
  }

  @Override
  @NotNull
  public List<DLanguageOrOrExpression> getOrOrExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageOrOrExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageRelExpression> getRelExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageRelExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageShiftExpression_> getShiftExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageShiftExpression_.class);
  }

  @Override
  @NotNull
  public List<DLanguageType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
  }

  @Override
  @NotNull
  public List<DLanguageUnaryExpression> getUnaryExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageUnaryExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageXorExpression_> getXorExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageXorExpression_.class);
  }

  @Override
  @NotNull
  public PsiElement getKwAlias() {
    return notNullChild(findChildByType(KW_ALIAS));
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

}
