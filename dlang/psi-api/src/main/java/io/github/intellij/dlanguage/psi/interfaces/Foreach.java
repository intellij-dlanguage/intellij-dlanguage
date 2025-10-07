package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.DLanguageForeachTypeList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Foreach extends PsiElement {

    @Nullable
    PsiElement getKW_FOREACH();

    @Nullable
    PsiElement getKW_FOREACH_REVERSE();

    @NotNull
    List<Expression> getExpressions();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    PsiElement getOP_DDOT();

    @Nullable
    DLanguageForeachTypeList getForeachTypeList();

    @Nullable
    PsiElement getOP_SCOLON();
}
