package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageSpecifiedFunctionBody extends PsiElement {

    @Nullable
    List<DLanguageFunctionContract> getFunctionContracts();

    @Nullable
    PsiElement getKW_DO();

    @NotNull
    DLanguageBlockStatement getBlockStatement();
}
