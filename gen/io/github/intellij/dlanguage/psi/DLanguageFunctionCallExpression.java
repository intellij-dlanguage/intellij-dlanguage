package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionCallExpression extends PsiElement {

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageArguments getArguments();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    DLanguageTemplateArguments getTemplateArguments();
}
