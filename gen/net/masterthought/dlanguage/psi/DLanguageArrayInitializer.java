package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
