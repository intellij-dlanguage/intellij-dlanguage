package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTraitsExpression extends PsiElement {
    @Nullable
    public PsiElement getKW___TRAITS();

    @Nullable
    public DLanguageTemplateArgumentList getTemplateArgumentList();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
