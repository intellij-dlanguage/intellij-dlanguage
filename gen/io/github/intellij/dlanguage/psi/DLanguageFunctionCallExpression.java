package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionCallExpression extends PsiElement {

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageArguments getArguments();

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    public DLanguageTemplateArguments getTemplateArguments();
}
