package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DLanguageDeclaratorIdentifierStub;
import org.jetbrains.annotations.Nullable;

public interface DLanguageDeclaratorIdentifier extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageDeclaratorIdentifierStub> {

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_COMMA();
}
