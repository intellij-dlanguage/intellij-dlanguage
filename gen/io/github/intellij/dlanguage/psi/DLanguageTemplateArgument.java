package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateArgument extends PsiElement {

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();
}
