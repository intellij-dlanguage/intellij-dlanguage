package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageParameters extends PsiElement {

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

    @NotNull
    public List<DLanguageParameter> getParameters();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

}
