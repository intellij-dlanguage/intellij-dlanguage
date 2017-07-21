package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIdentifierOrTemplateInstance extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateInstance getTemplateInstance();
}
