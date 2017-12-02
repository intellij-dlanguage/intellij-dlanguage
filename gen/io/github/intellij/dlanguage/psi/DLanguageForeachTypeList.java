package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageForeachTypeList extends PsiElement {

    @NotNull
    public List<DLanguageForeachType> getForeachTypes();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
