
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageDeclarationOrStatement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangLabeledStatementStub;
import org.jetbrains.annotations.Nullable;


public interface DlangLabeledStatement extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangLabeledStatementStub> {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
