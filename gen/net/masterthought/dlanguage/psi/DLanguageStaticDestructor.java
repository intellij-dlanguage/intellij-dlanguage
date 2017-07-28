package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import net.masterthought.dlanguage.stubs.DLanguageStaticDestructorStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticDestructor extends PsiElement, DCompositeElement, StubBasedPsiElement<DLanguageStaticDestructorStub> {
    @Nullable
    public PsiElement getOP_TILDA();

    @Nullable
    public PsiElement getKW_STATIC();

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();
}
