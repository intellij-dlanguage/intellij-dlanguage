package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageRegister extends PsiElement {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
