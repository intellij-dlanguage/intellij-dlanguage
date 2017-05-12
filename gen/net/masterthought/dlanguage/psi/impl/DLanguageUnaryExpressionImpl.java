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

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitUnaryExpression(this);
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
  @Nullable
  public DLanguagePowExpression getPowExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePowExpression.class);
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
  public DLanguageUnaryExpression getUnaryExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
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
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

  @Override
  @Nullable
  public PsiElement getOpMinus() {
    return findChildByType(OP_MINUS);
  }

  @Override
  @Nullable
  public PsiElement getOpMinusMinus() {
    return findChildByType(OP_MINUS_MINUS);
  }

  @Override
  @Nullable
  public PsiElement getOpNot() {
    return findChildByType(OP_NOT);
  }

  @Override
  @Nullable
  public PsiElement getOpOr() {
    return findChildByType(OP_OR);
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
  public PsiElement getOpPlusPlus() {
    return findChildByType(OP_PLUS_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getOpPow() {
    return findChildByType(OP_POW);
  }

  @Override
  @Nullable
  public PsiElement getOpTilda() {
    return findChildByType(OP_TILDA);
  }

}
