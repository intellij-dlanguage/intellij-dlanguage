package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayInitializer extends PsiElement {

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @NotNull
    List<DLanguageArrayMemberInitialization> getArrayMemberInitializations();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

}
