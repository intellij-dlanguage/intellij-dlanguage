package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateAliasParameter extends PsiElement {
    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    DlangIdentifier getIdentifier();

    @NotNull
    List<DLanguageType> getTypes();

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

}
