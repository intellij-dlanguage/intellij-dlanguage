package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageImportBind extends PsiElement {

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageNamedImportBind getNamedImportBind();
}
