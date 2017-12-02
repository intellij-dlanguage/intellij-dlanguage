package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLabeledStatement extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageLabeledStatementStub> {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
