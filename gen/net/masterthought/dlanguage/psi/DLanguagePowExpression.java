package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePowExpression extends PsiElement {
    @Nullable
    DLanguagePowExpression getPowExpression();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    PsiElement getOP_POW();

}
