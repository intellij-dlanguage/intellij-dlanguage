package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangModuleDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DlangModuleDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DlangModuleDeclarationStub> {
    @Nullable
    PsiElement getKW_MODULE();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    PsiElement getOP_SCOLON();

}
