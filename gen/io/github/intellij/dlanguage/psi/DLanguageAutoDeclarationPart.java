package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAutoDeclarationPart extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageAutoDeclarationPartStub> {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageInitializer getInitializer();
}
