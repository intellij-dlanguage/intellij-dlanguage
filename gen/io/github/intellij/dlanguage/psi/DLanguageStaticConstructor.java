package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.stubs.DlangStaticConstructorStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticConstructor extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DlangStaticConstructorStub> {

    @Nullable
    PsiElement getKW_STATIC();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageFunctionBody getFunctionBody();
}
