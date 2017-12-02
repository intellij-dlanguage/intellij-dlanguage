package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageUnittest extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DLanguageUnittestStub> {

    @Nullable
    public DLanguageUnittest getUnittest();

    @Nullable
    public DLanguageBlockStatement getBlockStatement();
}
