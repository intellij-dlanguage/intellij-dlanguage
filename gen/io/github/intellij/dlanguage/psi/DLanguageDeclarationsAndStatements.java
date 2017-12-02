package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageDeclarationsAndStatements extends PsiElement {

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();
}
