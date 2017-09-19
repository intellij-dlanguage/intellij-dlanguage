package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangForeachTypeStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachType extends PsiElement, DNamedElement, StubBasedPsiElement<DlangForeachTypeStub> {
    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTypeConstructors getTypeConstructors();
}
