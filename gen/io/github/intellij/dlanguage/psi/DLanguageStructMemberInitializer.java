package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStructMemberInitializer extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer();
}
