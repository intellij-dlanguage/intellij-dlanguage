package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageString extends PsiElement {
    @NotNull
    List<PsiElement> getDOUBLE_QUOTED_STRINGs();

}
