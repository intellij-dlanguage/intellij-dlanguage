package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNamedImportBind extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageNamedImportBindStub> {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_EQ();

}
