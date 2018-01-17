package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIsExpression extends PsiElement {

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageTypeSpecialization getTypeSpecialization();

    @Nullable
    public DLanguageTemplateParameterList getTemplateParameterList();

    @Nullable
    public PsiElement getOP_COMMA();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public PsiElement getKW_IS();

}
