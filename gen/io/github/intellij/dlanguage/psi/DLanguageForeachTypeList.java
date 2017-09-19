package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageForeachTypeList extends PsiElement {
    @NotNull
    List<DLanguageForeachType> getForeachTypes();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
