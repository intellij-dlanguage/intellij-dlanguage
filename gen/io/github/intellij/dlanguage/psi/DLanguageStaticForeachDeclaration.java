package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageStaticForeachDeclaration extends PsiElement {

    @Nullable
    PsiElement getKW_FOREACH();

    @Nullable
    PsiElement getKW_FOREACH_REVERSE();

    @Nullable
    DLanguageDeclaration getDeclaration();

    @NotNull
    List<DLanguageExpression> getExpressions();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    PsiElement getOP_DDOT();

    @Nullable
    DlangForeachType getForeachType();

    @Nullable
    DLanguageForeachTypeList getForeachTypeList();

    @Nullable
    PsiElement getOP_SCOLON();

    @NotNull
    PsiElement getKW_STATIC();
}
