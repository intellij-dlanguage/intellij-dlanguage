
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageInitializer;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangAutoDeclarationPartStub;
import org.jetbrains.annotations.Nullable;


public interface DlangAutoDeclarationPart extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangAutoDeclarationPartStub> {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageInitializer getInitializer();
}
