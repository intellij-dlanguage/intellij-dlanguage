package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageParameters extends PsiElement {
    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @NotNull
    List<DLanguageParameter> getParameters();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

}
