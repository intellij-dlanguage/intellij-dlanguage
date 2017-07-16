package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageModuleDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageModuleDeclarationStub> {
    @Nullable
    public PsiElement getKW_MODULE();

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    public PsiElement getOP_SCOLON();

}
