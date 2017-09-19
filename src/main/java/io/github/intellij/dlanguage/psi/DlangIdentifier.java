package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangIdentifierStub;
import org.jetbrains.annotations.Nullable;


public interface DlangIdentifier extends PsiElement, DNamedElement, StubBasedPsiElement<DlangIdentifierStub> {
    @Nullable
    public PsiElement getID();

}
