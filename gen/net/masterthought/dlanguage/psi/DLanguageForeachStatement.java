package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageForeachStatement extends PsiElement {
    @Nullable
    PsiElement getKW_FOREACH();

    @Nullable
    PsiElement getKW_FOREACH_REVERSE();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @NotNull
    List<DLanguageExpression> getExpressions();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    PsiElement getOP_DDOT();

    @Nullable
    DLanguageForeachType getForeachType();

    @Nullable
    DLanguageForeachTypeList getForeachTypeList();

    @Nullable
    PsiElement getOP_SCOLON();

}
