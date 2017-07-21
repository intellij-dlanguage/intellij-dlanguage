package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticConstructorStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSharedStaticConstructor extends PsiElement, DCompositeElement, StubBasedPsiElement<DLanguageSharedStaticConstructorStub> {
    @Nullable
    public PsiElement getKW_STATIC();

    @Nullable
    public PsiElement getKW_SHARED();

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();
}
