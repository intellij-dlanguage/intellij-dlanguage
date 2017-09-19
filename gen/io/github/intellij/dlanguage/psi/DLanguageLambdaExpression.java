package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageLambdaExpression extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getKW_FUNCTION();

    @Nullable
    PsiElement getKW_DELEGATE();

    @Nullable
    PsiElement getOP_LAMBDA_ARROW();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageParameters getParameters();

    @NotNull
    List<DLanguageFunctionAttribute> getFunctionAttributes();
}
