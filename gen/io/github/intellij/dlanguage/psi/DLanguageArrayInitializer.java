package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayInitializer extends PsiElement {

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @NotNull
    public List<DLanguageArrayMemberInitialization> getArrayMemberInitializations();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

}
