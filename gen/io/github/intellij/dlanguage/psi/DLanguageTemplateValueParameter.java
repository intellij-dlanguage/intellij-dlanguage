package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateValueParameter extends PsiElement {

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault();
}
