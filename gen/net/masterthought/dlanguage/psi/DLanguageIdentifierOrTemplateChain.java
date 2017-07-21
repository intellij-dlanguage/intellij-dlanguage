package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageIdentifierOrTemplateChain extends PsiElement {
    @NotNull
    public List<PsiElement> getOP_DOTs();

    @NotNull
    public List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstances();
}
