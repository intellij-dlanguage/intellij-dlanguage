package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DlangIdentifierStub;
import org.jetbrains.annotations.Nullable;


public interface DlangIdentifier extends PsiElement, DNamedElement, StubBasedPsiElement<DlangIdentifierStub> {
    @Nullable
    public PsiElement getID();

}
