package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIdentifierOrTemplateInstance extends PsiElement {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateInstance getTemplateInstance();
}
