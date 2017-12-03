package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageIdentifierList extends PsiElement {

    @NotNull
    List<DlangIdentifier> getIdentifiers();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
