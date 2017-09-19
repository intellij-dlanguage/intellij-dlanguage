package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageNewAnonClassExpression extends PsiElement {
    @Nullable
    PsiElement getKW_NEW();

    @Nullable
    PsiElement getKW_CLASS();

    @NotNull
    List<DLanguageArguments> getArgumentss();

    @Nullable
    DLanguageBaseClassList getBaseClassList();

    @Nullable
    DLanguageStructBody getStructBody();
}
