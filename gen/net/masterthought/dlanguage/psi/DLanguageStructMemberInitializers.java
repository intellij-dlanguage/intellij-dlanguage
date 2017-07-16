package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageStructMemberInitializers extends PsiElement {
    @NotNull
    public List<DLanguageStructMemberInitializer> getStructMemberInitializers();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
