package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTypeSuffix extends PsiElement {
    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @Nullable
    PsiElement getKW_FUNCTION();

    @Nullable
    PsiElement getKW_DELEGATE();

    @Nullable
    PsiElement getOP_ASTERISK();

    @NotNull
    List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @Nullable
    DLanguageParameters getParameters();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

}
