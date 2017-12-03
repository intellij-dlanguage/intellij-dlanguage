package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageUnaryExpression extends PsiElement {

    @Nullable
    DLanguagePrimaryExpression getPrimaryExpression();

    @Nullable
    DLanguageFunctionCallExpression getFunctionCallExpression();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    DLanguageNewExpression getNewExpression();

    @Nullable
    DLanguageDeleteExpression getDeleteExpression();

    @Nullable
    DLanguageCastExpression getCastExpression();

    @Nullable
    DLanguageAssertExpression getAssertExpression();

    @Nullable
    DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    DLanguageSliceExpression getSliceExpression();

    @Nullable
    DLanguageIndexExpression getIndexExpression();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getOP_DOT();

    @Nullable
    PsiElement getOP_AND();

    @Nullable
    PsiElement getOP_ASTERISK();

    @Nullable
    PsiElement getOP_MINUS();

    @Nullable
    PsiElement getOP_MINUS_MINUS();

    @Nullable
    PsiElement getOP_NOT();

    @Nullable
    PsiElement getOP_PLUS();

    @Nullable
    PsiElement getOP_PLUS_PLUS();

    @Nullable
    PsiElement getOP_TILDA();

}
