package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageIdentifierChain extends PsiElement {
    @NotNull
    List<DlangIdentifier> getIdentifiers();

    @NotNull
    List<PsiElement> getOP_DOTs();

}
