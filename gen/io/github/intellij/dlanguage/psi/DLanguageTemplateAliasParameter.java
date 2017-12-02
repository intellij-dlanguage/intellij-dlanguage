package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateAliasParameter extends PsiElement {

    @Nullable
    public PsiElement getKW_ALIAS();

    @Nullable
    public DlangIdentifier getIdentifier();

    @NotNull
    public List<DLanguageType> getTypes();

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getOP_EQ();

}
