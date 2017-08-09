package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageImportBind extends PsiElement {
    @Nullable
    DLanguageIdentifier getIdentifier();
}
