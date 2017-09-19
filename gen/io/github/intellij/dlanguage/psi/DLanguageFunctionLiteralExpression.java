package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageFunctionLiteralExpression extends PsiElement {
    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getKW_FUNCTION();

    @Nullable
    PsiElement getKW_DELEGATE();

    @Nullable
    DLanguageParameters getParameters();

    @NotNull
    List<DLanguageFunctionAttribute> getFunctionAttributes();

    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @Nullable
    DlangIdentifier getIdentifier();
}
