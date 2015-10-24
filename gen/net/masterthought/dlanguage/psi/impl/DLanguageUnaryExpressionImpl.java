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

public class DLanguageUnaryExpressionImpl extends ASTWrapperPsiElement implements DLanguageUnaryExpression {

  public DLanguageUnaryExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitUnaryExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArgumentList getArgumentList() {
    return findChildByClass(DLanguageArgumentList.class);
  }

  @Override
  @Nullable
  public DLanguageAssertExpression getAssertExpression() {
    return findChildByClass(DLanguageAssertExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageAssignExpression> getAssignExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageCastExpression getCastExpression() {
    return findChildByClass(DLanguageCastExpression.class);
  }

  @Override
  @Nullable
  public DLanguageDeleteExpression getDeleteExpression() {
    return findChildByClass(DLanguageDeleteExpression.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionCallExpression getFunctionCallExpression() {
    return findChildByClass(DLanguageFunctionCallExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstanceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifierOrTemplateInstance.class);
  }

  @Override
  @Nullable
  public DLanguageNewExpression getNewExpression() {
    return findChildByClass(DLanguageNewExpression.class);
  }

  @Override
  @Nullable
  public DLanguagePrimaryExpression getPrimaryExpression() {
    return findChildByClass(DLanguagePrimaryExpression.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @NotNull
  public List<DLanguageUnaryExpression> getUnaryExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageUnaryExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpAnd() {
    return findChildByType(OP_AND);
  }

  @Override
  @Nullable
  public PsiElement getOpAsterisk() {
    return findChildByType(OP_ASTERISK);
  }

  @Override
  @Nullable
  public PsiElement getOpBracketLeft() {
    return findChildByType(OP_BRACKET_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpBracketRight() {
    return findChildByType(OP_BRACKET_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpDdot() {
    return findChildByType(OP_DDOT);
  }

  @Override
  @Nullable
  public PsiElement getOpMinus() {
    return findChildByType(OP_MINUS);
  }

  @Override
  @Nullable
  public PsiElement getOpNot() {
    return findChildByType(OP_NOT);
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

  @Override
  @Nullable
  public PsiElement getOpPlus() {
    return findChildByType(OP_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getOpTilda() {
    return findChildByType(OP_TILDA);
  }

}
