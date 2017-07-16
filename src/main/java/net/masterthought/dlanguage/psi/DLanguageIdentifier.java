package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIdentifier extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageIdentifierStub> {
    @Nullable
    public PsiElement getID();

}
