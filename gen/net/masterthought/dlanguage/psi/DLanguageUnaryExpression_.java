// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageUnaryExpression_ extends PsiElement {

    @Nullable
    DLanguageCastExpression getCastExpression();

    @Nullable
    DLanguageDeleteExpression getDeleteExpression();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguagePostfixExpression getPostfixExpression();

    @Nullable
    DLanguagePowExpression_ getPowExpression_();

    @Nullable
    DLanguageTemplateInstance getTemplateInstance();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageTypeCtor getTypeCtor();

    @Nullable
    DLanguageUnaryExpression_ getUnaryExpression_();

    @Nullable
    PsiElement getOpAnd();

    @Nullable
    PsiElement getOpAsterisk();

    @Nullable
    PsiElement getOpDot();

    @Nullable
    PsiElement getOpMinus();

    @Nullable
    PsiElement getOpMinusMinus();

    @Nullable
    PsiElement getOpNot();

    @Nullable
    PsiElement getOpOr();

    @Nullable
    PsiElement getOpParLeft();

    @Nullable
    PsiElement getOpParRight();

    @Nullable
    PsiElement getOpPlus();

    @Nullable
    PsiElement getOpPlusPlus();

    @Nullable
    PsiElement getOpPow();

    @Nullable
    PsiElement getOpTilda();

}
