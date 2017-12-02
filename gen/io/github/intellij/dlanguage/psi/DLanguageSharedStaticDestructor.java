package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSharedStaticDestructor extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DLanguageSharedStaticDestructorStub> {

    @Nullable
    public PsiElement getOP_TILDA();

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
