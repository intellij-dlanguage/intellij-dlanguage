package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateMixinExpression extends PsiElement {

    @Nullable
    public PsiElement getKW_MIXIN();

    @Nullable
    public DLanguageMixinTemplateName getMixinTemplateName();

    @Nullable
    public DLanguageTemplateArguments getTemplateArguments();

    @Nullable
    public DlangIdentifier getIdentifier();
}
