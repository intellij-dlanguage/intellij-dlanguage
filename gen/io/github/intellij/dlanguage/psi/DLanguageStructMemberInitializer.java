package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStructMemberInitializer extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer();
}
