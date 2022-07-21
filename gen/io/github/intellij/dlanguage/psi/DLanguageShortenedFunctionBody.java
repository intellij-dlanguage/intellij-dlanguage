package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageShortenedFunctionBody extends PsiElement {

    @Nullable
    List<DLanguageFunctionContract> getFunctionContracts();

    @NotNull
    PsiElement getOP_LAMBDA_ARROW();

    @NotNull
    DLanguageExpression getExpression();

    @NotNull
    PsiElement getOP_SCOLON();
}
