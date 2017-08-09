package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIdentifierOrTemplateInstance extends PsiElement {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateInstance getTemplateInstance();
}
