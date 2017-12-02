package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLambdaExpression extends PsiElement {

    @Nullable
    DLanguageIdentifier getIdentifier();

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
