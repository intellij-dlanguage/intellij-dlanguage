
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticDestructorStub;
import org.jetbrains.annotations.Nullable;


public interface DlangSharedStaticDestructor extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DlangSharedStaticDestructorStub> {

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
