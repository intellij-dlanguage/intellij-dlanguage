// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguagePrimaryExpressionStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguagePrimaryExpression extends DNamedElement, StubBasedPsiElement<DLanguagePrimaryExpressionStub> {

  @Nullable
  DLanguageArrayLiteral getArrayLiteral();

  @Nullable
  DLanguageAssertExpression getAssertExpression();

  @Nullable
  DLanguageAssocArrayLiteral getAssocArrayLiteral();

  @Nullable
  DLanguageBasicTypeX getBasicTypeX();

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageFunctionLiteral getFunctionLiteral();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageImportExpression getImportExpression();

  @Nullable
  DLanguageIsExpression getIsExpression();

  @Nullable
  DLanguageMixinExpression getMixinExpression();

  @Nullable
  DLanguageNewExpressionWithArgs getNewExpressionWithArgs();

  @Nullable
  DLanguageSpecialKeyword getSpecialKeyword();

  @Nullable
  DLanguageStringLiterals getStringLiterals();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  DLanguageTraitsExpression getTraitsExpression();

  @Nullable
  DLanguageTypeidExpression getTypeidExpression();

  @Nullable
  DLanguageTypeof getTypeof();

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
  PsiElement getOpDollar();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

  @NotNull
  String getName();

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

}
