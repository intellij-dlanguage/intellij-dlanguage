package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageIdentifierList extends PsiElement {
    @NotNull
    List<DLanguageIdentifier> getIdentifiers();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
