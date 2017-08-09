package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguagePostblit extends PsiElement {
    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @Nullable
    PsiElement getOP_SCOLON();

    @NotNull
    List<PsiElement> getKW_THISs();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageMemberFunctionAttribute getMemberFunctionAttribute();
}
