package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageXorExpression extends PsiElement {
    @Nullable
    DLanguageXorExpression getXorExpression();

    @Nullable
    DLanguageAndExpression getAndExpression();

    @Nullable
    PsiElement getOP_XOR();

}
