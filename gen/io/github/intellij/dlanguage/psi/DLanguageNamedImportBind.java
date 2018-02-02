package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.stubs.DlangNamedImportBindStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNamedImportBind extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangNamedImportBindStub> {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

}
