
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageInitializer;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangDeclaratorStub;
import org.jetbrains.annotations.Nullable;


public interface DlangDeclarator extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangDeclaratorStub> {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageInitializer getInitializer();
}
