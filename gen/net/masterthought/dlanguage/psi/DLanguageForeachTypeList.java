package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageForeachTypeList extends PsiElement {
    @NotNull
    public List<DLanguageForeachType> getForeachTypes();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
