package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageNamedImportBindStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNamedImportBind extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageNamedImportBindStub> {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

}
