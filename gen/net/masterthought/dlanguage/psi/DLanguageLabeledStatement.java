package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageLabeledStatementStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLabeledStatement extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageLabeledStatementStub> {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
