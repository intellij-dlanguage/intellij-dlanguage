// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePrimaryExpression extends PsiElement {

  @Nullable
  DLanguageFloatLiteral getFloatLiteral();

  @Nullable
  DLanguageIntegerLiteral getIntegerLiteral();

  @Nullable
  DLanguageArrayLiteral getArrayLiteral();

  @Nullable
  DLanguageAssocArrayLiteral getAssocArrayLiteral();

  @Nullable
  DLanguageBuiltinType getBuiltinType();

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageFunctionLiteralExpression getFunctionLiteralExpression();

  @Nullable
  DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();

  @Nullable
  DLanguageImportExpression getImportExpression();

  @Nullable
  DLanguageIsExpression getIsExpression();

  @Nullable
  DLanguageLambdaExpression getLambdaExpression();

  @Nullable
  DLanguageMixinExpression getMixinExpression();

  @Nullable
  DLanguageTraitsExpression getTraitsExpression();

  @Nullable
  DLanguageTypeidExpression getTypeidExpression();

  @Nullable
  DLanguageTypeofExpression getTypeofExpression();

  @Nullable
  DLanguageVector getVector();

  @Nullable
  PsiElement getCharacterLiteral();

  @Nullable
  PsiElement getDollar();

  @Nullable
  PsiElement getDot();

  @Nullable
  PsiElement getFalse();

  @Nullable
  PsiElement getLParen();

  @Nullable
  PsiElement getNull();

  @Nullable
  PsiElement getRParen();

  @Nullable
  PsiElement getSpecialDate();

  @Nullable
  PsiElement getSpecialFile();

  @Nullable
  PsiElement getSpecialFunction();

  @Nullable
  PsiElement getSpecialLine();

  @Nullable
  PsiElement getSpecialModule();

  @Nullable
  PsiElement getSpecialPrettyFunction();

  @Nullable
  PsiElement getSpecialTime();

  @Nullable
  PsiElement getSpecialVendor();

  @Nullable
  PsiElement getSpecialVersion();

  @Nullable
  PsiElement getSpecialimestamp();

  @Nullable
  PsiElement getSuper();

  @Nullable
  PsiElement getThis();

  @Nullable
  PsiElement getTrue();

}
