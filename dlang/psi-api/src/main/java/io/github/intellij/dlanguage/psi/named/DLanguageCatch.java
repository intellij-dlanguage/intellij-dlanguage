
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Statement;
import io.github.intellij.dlanguage.stubs.DLanguageCatchStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCatch extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageCatchStub> {

    @Nullable
    PsiElement getKW_CATCH();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    Statement getStatement();
}
