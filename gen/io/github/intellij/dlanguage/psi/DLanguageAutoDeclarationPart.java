package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangAutoDeclarationPartStub;
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
