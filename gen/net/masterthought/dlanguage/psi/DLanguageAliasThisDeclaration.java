package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasThisDeclaration extends PsiElement {
    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_SCOLON();

}
