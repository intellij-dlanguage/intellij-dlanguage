package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageBaseClassList extends PsiElement {

    @NotNull
    public List<DLanguageBaseClass> getBaseClasss();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
