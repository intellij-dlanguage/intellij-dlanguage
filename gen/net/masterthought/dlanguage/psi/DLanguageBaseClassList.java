package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageBaseClassList extends PsiElement {
    @NotNull
    public List<DLanguageBaseClass> getBaseClasss();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
