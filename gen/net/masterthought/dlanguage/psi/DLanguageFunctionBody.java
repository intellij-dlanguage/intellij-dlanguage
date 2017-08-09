package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionBody extends PsiElement {
    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    DLanguageInStatement getInStatement();

    @Nullable
    DLanguageOutStatement getOutStatement();

    @Nullable
    DLanguageBodyStatement getBodyStatement();
}
