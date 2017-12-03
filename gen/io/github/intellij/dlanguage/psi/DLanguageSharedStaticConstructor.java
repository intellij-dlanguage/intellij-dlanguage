package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSharedStaticConstructor extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DlangSharedStaticConstructorStub> {

    @Nullable
    PsiElement getKW_STATIC();

    @Nullable
    PsiElement getKW_SHARED();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageFunctionBody getFunctionBody();
}
