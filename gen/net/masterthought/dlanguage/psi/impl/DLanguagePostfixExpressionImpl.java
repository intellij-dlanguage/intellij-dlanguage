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

public class DLanguagePostfixExpressionImpl extends ASTWrapperPsiElement implements DLanguagePostfixExpression {

  public DLanguagePostfixExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitPostfixExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArgumentList getArgumentList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageArgumentList.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageIndexExpression getIndexExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIndexExpression.class);
  }

  @Override
  @Nullable
  public DLanguageNewExpression getNewExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageNewExpression.class);
  }

  @Override
  @Nullable
  public DLanguagePostfixExpression getPostfixExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePostfixExpression.class);
  }

  @Override
  @Nullable
  public DLanguagePrimaryExpression getPrimaryExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePrimaryExpression.class);
  }

  @Override
  @Nullable
  public DLanguageSliceExpression getSliceExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSliceExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateInstance getTemplateInstance() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
  }

  @Override
  @Nullable
  public DLanguageTypeCtors getTypeCtors() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeCtors.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

  @Override
  @Nullable
  public PsiElement getOpMinusMinus() {
    return findChildByType(OP_MINUS_MINUS);
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
  public PsiElement getOpPlusPlus() {
    return findChildByType(OP_PLUS_PLUS);
  }

}
