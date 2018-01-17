package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangParameter;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageParameters extends PsiElement {

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @NotNull
    List<DlangParameter> getParameters();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

}
