package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticDestructorStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSharedStaticDestructor extends PsiElement, DCompositeElement, StubBasedPsiElement<DLanguageSharedStaticDestructorStub> {
    @Nullable
    PsiElement getOP_TILDA();

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
