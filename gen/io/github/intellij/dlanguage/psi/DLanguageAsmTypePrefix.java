package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageAsmTypePrefix extends PsiElement {

    @NotNull
    public List<DLanguageIdentifier> getIdentifiers();
}
