package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import net.masterthought.dlanguage.stubs.interfaces.DlangUnittestStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageUnittest extends PsiElement, DCompositeElement, StubBasedPsiElement<DlangUnittestStub> {
    @Nullable
    DLanguageUnittest getUnittest();

    @Nullable
    DLanguageBlockStatement getBlockStatement();
}
