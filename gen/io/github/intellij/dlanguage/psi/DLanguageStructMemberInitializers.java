package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageStructMemberInitializers extends PsiElement {

    @NotNull
    public List<DLanguageStructMemberInitializer> getStructMemberInitializers();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
