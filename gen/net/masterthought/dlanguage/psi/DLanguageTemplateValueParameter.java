package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateValueParameter extends PsiElement {
    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault();
}
