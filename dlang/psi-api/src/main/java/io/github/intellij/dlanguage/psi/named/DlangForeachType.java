
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DlangForeachType extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangForeachTypeStub> {

    @Nullable
    PsiElement getKW_REF();

    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getIdentifier();

    @NotNull
    List<DLanguageTypeConstructor> getTypeConstructors();
}
