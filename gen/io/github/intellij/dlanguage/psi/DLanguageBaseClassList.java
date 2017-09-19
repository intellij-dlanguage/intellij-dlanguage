package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageBaseClassList extends PsiElement {
    @NotNull
    List<DLanguageBaseClass> getBaseClasss();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
