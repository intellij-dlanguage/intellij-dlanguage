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
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageStringLiteral> getStringLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStringLiteral.class);
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
  public PsiElement getKwFile() {
    return findChildByType(KW___FILE__);
  }

  @Override
  @Nullable
  public PsiElement getKwFunction() {
    return findChildByType(KW___FUNCTION__);
  }

  @Override
  @Nullable
  public PsiElement getKwLine() {
    return findChildByType(KW___LINE__);
  }

  @Override
  @Nullable
  public PsiElement getKwModule() {
    return findChildByType(KW___MODULE__);
  }

  @Override
  @Nullable
  public PsiElement getKwPrettyFunction() {
    return findChildByType(KW___PRETTY_FUNCTION__);
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
