package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateAliasParameter extends PsiElement {

    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @NotNull
    List<DLanguageType> getTypes();

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

}
