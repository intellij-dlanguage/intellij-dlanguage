package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import net.masterthought.dlanguage.stubs.DLanguageDestructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageDestructor extends PsiElement, DCompositeElement, StubBasedPsiElement<DLanguageDestructorStub> {
    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @Nullable
    PsiElement getOP_SCOLON();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_TILDA();

    @NotNull
    List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();
}
