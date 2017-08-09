package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePragmaDeclaration extends PsiElement {
    @Nullable
    DLanguagePragmaExpression getPragmaExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
