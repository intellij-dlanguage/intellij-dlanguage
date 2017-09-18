package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateMixinExpression extends PsiElement {
    @Nullable
    PsiElement getKW_MIXIN();

    @Nullable
    DLanguageMixinTemplateName getMixinTemplateName();

    @Nullable
    DLanguageTemplateArguments getTemplateArguments();

    @Nullable
    DlangIdentifier getIdentifier();
}
