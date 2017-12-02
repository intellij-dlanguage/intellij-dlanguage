package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachType extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageForeachTypeStub> {

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTypeConstructors getTypeConstructors();
}
