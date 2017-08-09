package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticAssertStatement extends PsiElement {
    @Nullable
    PsiElement getKW_STATIC();

    @Nullable
    DLanguageAssertExpression getAssertExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
