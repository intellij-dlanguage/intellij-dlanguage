package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDebugSpecification extends PsiElement {
    @Nullable
    PsiElement getKW_DEBUG();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    PsiElement getOP_SCOLON();

}
