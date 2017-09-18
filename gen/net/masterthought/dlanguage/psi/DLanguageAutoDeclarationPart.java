package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DlangAutoDeclarationPartStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAutoDeclarationPart extends PsiElement, DNamedElement, StubBasedPsiElement<DlangAutoDeclarationPartStub> {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageInitializer getInitializer();
}
