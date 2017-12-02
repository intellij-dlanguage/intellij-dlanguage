package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageIdentifierList extends PsiElement {

    @NotNull
    public List<DLanguageIdentifier> getIdentifiers();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

}
