package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageAsmTypePrefix extends PsiElement {

    @NotNull
    List<DlangIdentifier> getIdentifiers();
}
