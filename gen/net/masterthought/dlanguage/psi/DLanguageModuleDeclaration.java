package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DlangModuleDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageModuleDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DlangModuleDeclarationStub> {
    @Nullable
    PsiElement getKW_MODULE();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    PsiElement getOP_SCOLON();

}
