package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageIdentifierOrTemplateChain extends PsiElement {
    @NotNull
    List<PsiElement> getOP_DOTs();

    @NotNull
    List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstances();
}
