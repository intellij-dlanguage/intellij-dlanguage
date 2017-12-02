package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachType extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageForeachTypeStub> {

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageTypeConstructors getTypeConstructors();
}
