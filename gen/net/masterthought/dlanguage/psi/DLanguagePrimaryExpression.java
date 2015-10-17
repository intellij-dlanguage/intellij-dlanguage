// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguagePrimaryExpression extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageStringLiteral> getStringLiteralList();

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
  PsiElement getFloatLiteral();

  @Nullable
  PsiElement getIntegerLiteral();

  @Nullable
  PsiElement getKwFalse();

  @Nullable
  PsiElement getKwNull();

  @Nullable
  PsiElement getKwSuper();

  @Nullable
  PsiElement getKwThis();

  @Nullable
  PsiElement getKwTrue();

  @Nullable
  PsiElement getKwFile();

  @Nullable
  PsiElement getKwFunction();

  @Nullable
  PsiElement getKwLine();

  @Nullable
  PsiElement getKwModule();

  @Nullable
  PsiElement getKwPrettyFunction();

  @Nullable
  PsiElement getOpDollar();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
