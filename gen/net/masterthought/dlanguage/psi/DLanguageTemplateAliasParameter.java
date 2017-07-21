package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateAliasParameter extends PsiElement {
    @Nullable
    public PsiElement getKW_ALIAS();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @NotNull
    public List<DLanguageType> getTypes();

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getOP_EQ();

}
