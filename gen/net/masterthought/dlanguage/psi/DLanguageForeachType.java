package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachType extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageForeachTypeStub> {
    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTypeConstructors getTypeConstructors();
}
