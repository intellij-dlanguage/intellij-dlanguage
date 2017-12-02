package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNonVoidInitializer extends PsiElement {

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageArrayInitializer getArrayInitializer();

    @Nullable
    public DLanguageStructInitializer getStructInitializer();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();
}
