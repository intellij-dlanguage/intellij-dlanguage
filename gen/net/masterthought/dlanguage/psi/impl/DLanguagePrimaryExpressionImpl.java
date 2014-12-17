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
  public DLanguageFloatLiteral getFloatLiteral() {
    return findChildByClass(DLanguageFloatLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageIntegerLiteral getIntegerLiteral() {
    return findChildByClass(DLanguageIntegerLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageArrayLiteral getArrayLiteral() {
    return findChildByClass(DLanguageArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageAssocArrayLiteral getAssocArrayLiteral() {
    return findChildByClass(DLanguageAssocArrayLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageBuiltinType getBuiltinType() {
    return findChildByClass(DLanguageBuiltinType.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionLiteralExpression getFunctionLiteralExpression() {
    return findChildByClass(DLanguageFunctionLiteralExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance() {
    return findChildByClass(DLanguageIdentifierOrTemplateInstance.class);
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
  public DLanguageLambdaExpression getLambdaExpression() {
    return findChildByClass(DLanguageLambdaExpression.class);
  }

  @Override
  @Nullable
  public DLanguageMixinExpression getMixinExpression() {
    return findChildByClass(DLanguageMixinExpression.class);
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
  public DLanguageTypeofExpression getTypeofExpression() {
    return findChildByClass(DLanguageTypeofExpression.class);
  }

  @Override
  @Nullable
  public DLanguageVector getVector() {
    return findChildByClass(DLanguageVector.class);
  }

  @Override
  @Nullable
  public PsiElement getCharacterLiteral() {
    return findChildByType(CHARACTERLITERAL);
  }

  @Override
  @Nullable
  public PsiElement getDollar() {
    return findChildByType(DOLLAR);
  }

  @Override
  @Nullable
  public PsiElement getDot() {
    return findChildByType(DOT);
  }

  @Override
  @Nullable
  public PsiElement getFalse() {
    return findChildByType(FALSE);
  }

  @Override
  @Nullable
  public PsiElement getLParen() {
    return findChildByType(LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getNull() {
    return findChildByType(NULL);
  }

  @Override
  @Nullable
  public PsiElement getRParen() {
    return findChildByType(RPAREN);
  }

  @Override
  @Nullable
  public PsiElement getSpecialDate() {
    return findChildByType(SPECIALDATE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFile() {
    return findChildByType(SPECIALFILE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFunction() {
    return findChildByType(SPECIALFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialLine() {
    return findChildByType(SPECIALLINE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialModule() {
    return findChildByType(SPECIALMODULE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialPrettyFunction() {
    return findChildByType(SPECIALPRETTYFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialTime() {
    return findChildByType(SPECIALTIME);
  }

  @Override
  @Nullable
  public PsiElement getSpecialVendor() {
    return findChildByType(SPECIALVENDOR);
  }

  @Override
  @Nullable
  public PsiElement getSpecialVersion() {
    return findChildByType(SPECIALVERSION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialimestamp() {
    return findChildByType(SPECIALIMESTAMP);
  }

  @Override
  @Nullable
  public PsiElement getSuper() {
    return findChildByType(SUPER);
  }

  @Override
  @Nullable
  public PsiElement getThis() {
    return findChildByType(THIS);
  }

  @Override
  @Nullable
  public PsiElement getTrue() {
    return findChildByType(TRUE);
  }

}
