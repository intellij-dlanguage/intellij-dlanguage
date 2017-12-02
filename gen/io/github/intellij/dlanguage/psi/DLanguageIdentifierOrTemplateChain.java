package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageIdentifierOrTemplateChain extends PsiElement {

    @NotNull
    public List<PsiElement> getOP_DOTs();

    @NotNull
    public List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstances();
}
