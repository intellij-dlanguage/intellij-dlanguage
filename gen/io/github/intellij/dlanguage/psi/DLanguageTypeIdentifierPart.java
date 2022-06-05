package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageTypeIdentifierPart extends PsiElement {

    @Nullable
    PsiElement getOP_DOT();

    @NotNull
    DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_DDOT();

    @Nullable
    DLanguageTypeIdentifierPart getTypeIdentifierPart();
}
