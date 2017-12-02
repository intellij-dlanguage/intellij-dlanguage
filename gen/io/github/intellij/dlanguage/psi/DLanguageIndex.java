package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIndex extends PsiElement {

    @Nullable
    public DLanguageAssignExpression getAssignExpression();
}
