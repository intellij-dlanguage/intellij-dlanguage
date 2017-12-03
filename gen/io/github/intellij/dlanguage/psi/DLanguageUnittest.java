package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageUnittest extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DlangUnittestStub> {

    @Nullable
    DLanguageUnittest getUnittest();

    @Nullable
    DLanguageBlockStatement getBlockStatement();
}
