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

public class DLanguagePrimaryExpressionImpl extends ASTWrapperPsiElement implements DLanguagePrimaryExpression {

  public DLanguagePrimaryExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitPrimaryExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArrayLiteral getArrayLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageAssertExpression getAssertExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssertExpression.class);
  }

  @Override
  @Nullable
  public DLanguageAssocArrayLiteral getAssocArrayLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssocArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageBasicTypeX getBasicTypeX() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicTypeX.class);
  }

  @Override
  @Nullable
  public DLanguageCommaExpression getCommaExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageCommaExpression.class);
  }

  @Override
  @Nullable
  public DLanguageFloatLiteral getFloatLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFloatLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionLiteral getFunctionLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageImportExpression getImportExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageImportExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIsExpression getIsExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIsExpression.class);
  }

  @Override
  @Nullable
  public DLanguageMixinExpression getMixinExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMixinExpression.class);
  }

  @Override
  @Nullable
  public DLanguageNewExpressionWithArgs getNewExpressionWithArgs() {
    return PsiTreeUtil.getChildOfType(this, DLanguageNewExpressionWithArgs.class);
  }

  @Override
  @Nullable
  public DLanguageSpecialKeyword getSpecialKeyword() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSpecialKeyword.class);
  }

  @Override
  @Nullable
  public DLanguageStringLiterals getStringLiterals() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStringLiterals.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateInstance getTemplateInstance() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
  }

  @Override
  @Nullable
  public DLanguageTraitsExpression getTraitsExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTraitsExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTypeidExpression getTypeidExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeidExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTypeof getTypeof() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeof.class);
  }

  @Override
  @Nullable
  public PsiElement getCharacterLiteral() {
    return findChildByType(CHARACTER_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getIntegerLiteral() {
    return findChildByType(INTEGER_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getKwFalse() {
    return findChildByType(KW_FALSE);
  }

  @Override
  @Nullable
  public PsiElement getKwNull() {
    return findChildByType(KW_NULL);
  }

  @Override
  @Nullable
  public PsiElement getKwSuper() {
    return findChildByType(KW_SUPER);
  }

  @Override
  @Nullable
  public PsiElement getKwThis() {
    return findChildByType(KW_THIS);
  }

  @Override
  @Nullable
  public PsiElement getKwTrue() {
    return findChildByType(KW_TRUE);
  }

  @Override
  @Nullable
  public PsiElement getOpDollar() {
    return findChildByType(OP_DOLLAR);
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
