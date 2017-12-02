package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateInstance extends PsiElement {

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateArguments getTemplateArguments();
}
