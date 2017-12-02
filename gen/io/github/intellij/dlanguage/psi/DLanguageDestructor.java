package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDestructor extends PsiElement, DCompositeElement,
    StubBasedPsiElement<DLanguageDestructorStub> {

    @Nullable
    public DLanguageFunctionBody getFunctionBody();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getKW_THIS();

    @Nullable
    public PsiElement getOP_TILDA();

    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();
}
