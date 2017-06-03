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

public class DLanguagePowExpression_Impl extends ASTWrapperPsiElement implements DLanguagePowExpression_ {

  public DLanguagePowExpression_Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitPowExpression_(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageCastExpression getCastExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageCastExpression.class);
  }

  @Override
  @Nullable
  public DLanguageDeleteExpression getDeleteExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeleteExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguagePostfixExpression> getPostfixExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguagePostfixExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguagePowExpression_> getPowExpression_List() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguagePowExpression_.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateInstance getTemplateInstance() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
  }

  @Override
  @Nullable
  public DLanguageTypeCtor getTypeCtor() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeCtor.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

  @Override
  @Nullable
  public PsiElement getOpParLeft() {
    return findChildByType(OP_PAR_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpParRight() {
    return findChildByType(OP_PAR_RIGHT);
  }

}
