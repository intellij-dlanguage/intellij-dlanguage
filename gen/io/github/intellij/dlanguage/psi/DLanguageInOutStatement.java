package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageInOutStatement extends PsiElement {

    @Nullable
    DLanguageInStatement getInStatement();

    @Nullable
    DLanguageOutStatement getOutStatement();
}
