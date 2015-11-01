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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitPrimaryExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArrayLiteral getArrayLiteral() {
    return findChildByClass(DLanguageArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageAssertExpression getAssertExpression() {
    return findChildByClass(DLanguageAssertExpression.class);
  }

  @Override
  @Nullable
  public DLanguageAssocArrayLiteral getAssocArrayLiteral() {
    return findChildByClass(DLanguageAssocArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageBasicTypeX getBasicTypeX() {
    return findChildByClass(DLanguageBasicTypeX.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionLiteral getFunctionLiteral() {
    return findChildByClass(DLanguageFunctionLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageImportExpression getImportExpression() {
    return findChildByClass(DLanguageImportExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIsExpression getIsExpression() {
    return findChildByClass(DLanguageIsExpression.class);
  }

  @Override
  @Nullable
  public DLanguageMixinExpression getMixinExpression() {
    return findChildByClass(DLanguageMixinExpression.class);
  }

  @Override
  @Nullable
  public DLanguageNewExpressionWithArgs getNewExpressionWithArgs() {
    return findChildByClass(DLanguageNewExpressionWithArgs.class);
  }

  @Override
  @Nullable
  public DLanguageSpecialKeyword getSpecialKeyword() {
    return findChildByClass(DLanguageSpecialKeyword.class);
  }

  @Override
  @Nullable
  public DLanguageStringLiterals getStringLiterals() {
    return findChildByClass(DLanguageStringLiterals.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateInstance getTemplateInstance() {
    return findChildByClass(DLanguageTemplateInstance.class);
  }

  @Override
  @Nullable
  public DLanguageTraitsExpression getTraitsExpression() {
    return findChildByClass(DLanguageTraitsExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTypeidExpression getTypeidExpression() {
    return findChildByClass(DLanguageTypeidExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTypeof getTypeof() {
    return findChildByClass(DLanguageTypeof.class);
  }

  @Override
  @Nullable
  public PsiElement getCharacterLiteral() {
    return findChildByType(CHARACTER_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getFloatLiteral() {
    return findChildByType(FLOAT_LITERAL);
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
