package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIsExpression extends PsiElement {
    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTypeSpecialization getTypeSpecialization();

    @Nullable
    DLanguageTemplateParameterList getTemplateParameterList();

    @Nullable
    PsiElement getOP_COMMA();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    PsiElement getKW_IS();

}
