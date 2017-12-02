package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageString extends PsiElement {

    @NotNull
    public List<PsiElement> getDOUBLE_QUOTED_STRINGs();

}
