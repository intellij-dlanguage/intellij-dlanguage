package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageVersionSpecification extends PsiElement {
    @Nullable
    PsiElement getKW_VERSION();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    PsiElement getOP_SCOLON();

}
