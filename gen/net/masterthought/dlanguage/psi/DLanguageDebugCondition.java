package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDebugCondition extends PsiElement {
    @Nullable
    PsiElement getKW_DEBUG();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
